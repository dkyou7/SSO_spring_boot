package com.example.demo.account;

import com.example.demo.sso.SsoService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final SsoService ssoService;
    private final SignUpFormValidator signUpFormValidator;

    @InitBinder("signUpForm")
    public void initBinder(WebDataBinder webDataBinder){
        webDataBinder.addValidators(signUpFormValidator);
    }

    @GetMapping("/sign-up")
    public String signUpForm(Model model){
        model.addAttribute("signUpForm",new SignUpForm());
        return "sign-up";
    }

    @PostMapping("/sign-up")
    public String signUpSubmit(@Valid SignUpForm signUpForm, Errors errors, RedirectAttributes attributes){
        if(errors.hasErrors()){
            return "sign-up";
        }
        attributes.addFlashAttribute("message","회원가입 성공!");
        accountService.signUp(signUpForm);
        return "redirect:/";
    }

    @GetMapping("/log-in")
    public String logInDisp(Model model){
        model.addAttribute(new LoginForm());
        return "log-in";
    }

    @GetMapping("/log-out")
    public String logout(){
        return "redirect:/";
    }

    @GetMapping("/")
    public String home(@CurrentUser Account account, Model model){
        if(account != null){
            // 인증된 유저 객체가 존재한다면, sso login 처리하기
            ssoService.ssoLogin(account);
            model.addAttribute(account);
        }else{
            //TODO : SSO 로직 실행 글로벌 포탈에게 싸인 보내기
            Account byVid = accountService.findByVid("admin@naver.com");
            boolean isSSO = ssoService.isSSO2("admin@naver.com");
            if(loginLogic(isSSO,byVid)){
                accountService.login(byVid);
                return "redirect:/";
            }
        }

        return "index";
    }

    /**
     * SSO VID가 로그인 되어있고, 해당 VID로 등록된 유저가 존재하는 경우
     * @param isSSO
     * @param account
     * @return true
     */
    private boolean loginLogic(boolean isSSO, Account account) {
        return isSSO && account != null;
    }
}