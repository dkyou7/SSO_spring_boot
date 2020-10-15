package com.ktnet.testClient2.resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletResponse;

@Controller
public class Client2Controller {
    private static final String jwtTokenCookieName = "JWT-TOKEN";
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/")
    public String home() {
        logger.info("[Client2 서버] home() ================= 2. 이제 여기서 인증서버에서 낚아채갑니다. jwt filter 에 등록되어있기 때문에");
        return "redirect:/protected-resource-2";
    }

    @GetMapping("/protected-resource-2/test/{username}")
    public RedirectView protectedResourceTest(@PathVariable("username") String username) {
        logger.info("[Client2 서버] /protected-resource-2/testClient2 =================" + username);
        return new RedirectView("http://localhost:8183/success/"+username);
    }

    @GetMapping("/logout")
    public String logout(HttpServletResponse httpServletResponse) {
        logger.info("[Client2 서버] logout =================");
        CookieUtil.clear(httpServletResponse, jwtTokenCookieName);
        return "redirect:/";
    }
}
