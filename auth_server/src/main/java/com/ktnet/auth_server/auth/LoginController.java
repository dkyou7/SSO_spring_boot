package com.ktnet.auth_server.auth;

import com.ktnet.auth_server.account.Account;
import com.ktnet.auth_server.account.AccountService;
import com.ktnet.auth_server.federation.Federation;
import com.ktnet.auth_server.federation.FederationService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class LoginController {
    private static final String jwtTokenCookieName = "JWT-TOKEN";
    private static final String signingKey = "signingKey";
    private static final String sessionKey = "testSessionKey";
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final AccountService accountService;

    @GetMapping("/")
    public String home(HttpServletRequest request){
        HttpSession session = request.getSession();
        String value = (String) session.getAttribute(sessionKey);
        if(value == null){
            logger.info("[인증서버] home() ===> 로그인 창으로 이동합니다");
            return "redirect:/loginTest";
        }else{
            //todo: 토큰 발급해서 준다.
        }
        return "redirect:/loginTest";

    }

    @Getter @Setter
    @NoArgsConstructor
    class DevUserLoginDto{
        private String email;
        private String password;
    }

    @GetMapping("/login")
    public String login(@RequestParam("redirect_uri") String redirectUrl,
                        @RequestParam("client_id") String clientId,
                        @RequestParam("response_type")String resType,
                        HttpServletRequest request,
                        Model model){
        String value = (String) request.getSession().getAttribute(sessionKey);
        System.out.println("request.getSession().getId() = " + request.getSession().getId());
        if (value != null){
            return "redirect:" + redirectUrl+"/test/"+value;
        }

        if(resType == null || !resType.equals("code")){
            logger.info("[인증서버] login() ===> 코드 방식이 아닙니다. 접근 불가!");
            return "redirect:"+redirectUrl+"/fail";
        }
        logger.info("[인증서버] login() ===> 로그인 창으로 이동합니다");
        if(accountService.getSize()<1){
            accountService.save(new Account("123","test1@naver.com","123"));
        }
        Account account = new Account(redirectUrl,clientId);
        model.addAttribute("account",account);
//        return "loginTest";
        return "index";
    }

    @PostMapping("/login")
    public String login(HttpServletRequest request,
                        HttpServletResponse httpServletResponse,
                        @RequestParam("email") String email,
                        @RequestParam("password") String password,
                        @RequestParam("redirectUrl") String redirectUrl,
                        @RequestParam("clientId") String clientId,
                        Model model){
        logger.info("[인증서버] post login() ================================");
        Account findAccount = accountService.findUserByEmail(email);

        if(findAccount == null || !findAccount.getPassword().equals(password)){
            model.addAttribute("error", "Invalid username or password!");
            return "index";
        }
        String token = JwtUtil.generateToken(signingKey, findAccount.getEmail());
        HttpSession session = request.getSession();
        session.setAttribute(sessionKey,token);
        System.out.println("session.getId() = " + session.getId());
//        String code = sessionInfoService.save(token);

        // 쿠키와 세션을 동시에 만들어준다.
        return "redirect:" + redirectUrl+"/test/"+token;
    }

}
