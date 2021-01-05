package com.ktnet.testRes1.main;

import com.ktnet.testRes1.account.Account;
import com.ktnet.testRes1.account.AccountService;
import com.ktnet.testRes1.account.CurrentUser;
import com.ktnet.testRes1.account.SignUpFormValidator;
import com.ktnet.testRes1.sso.SSOService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final AccountService accountService;
    private final SSOService ssoService;

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
