package com.ktnet.testClient1.resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletResponse;

@Controller
public class Client1Controller {
    private static final String jwtTokenCookieName = "JWT-TOKEN";
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/")
    public String home() {
        logger.info("[Client1 서버] home() ================= 2. 이제 여기서 인증서버에서 낚아채갑니다. jwt filter 에 등록되어있기 때문에");
        return "redirect:/protected-resource-1";
    }

    @GetMapping("/protected-resource-1")
    public String protectedResource() {
        logger.info("[Client1 서버] protectedResource() =================");
        return "protected-resource-1";
    }

    @GetMapping("/protected-resource-1/test/{username}")
    public RedirectView protectedResourceTest(@PathVariable("username") String username) {
        logger.info("[Client1 서버] /protected-resource-1/testClient1 =================" + username);
        return new RedirectView("http://localhost:8181/success/"+username);
    }
    @GetMapping("/go_client_1")
    public RedirectView go_client_1() {
        logger.info("[Client1 서버] go_client_1 =================");
        return new RedirectView("http://localhost:8181/success");
    }

    @GetMapping("/logout")
    public String logout(HttpServletResponse httpServletResponse) {
        logger.info("[Client1 서버] logout =================");
        CookieUtil.clear(httpServletResponse, jwtTokenCookieName);
        return "redirect:/";
    }

    @GetMapping("/test")
    public String sendRes1(){
        return "redirect:/http://localhost:8180/";
    }
}
