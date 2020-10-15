package com.ktnet.testClient2.resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

@Controller
public class ResourceController {
    private static final String jwtTokenCookieName = "JWT-TOKEN";

    @GetMapping("/")
    public String home() {
        return "redirect:/protected-resource-2";
    }

    @GetMapping("/protected-resource-2")
    public String protectedResource() {
        System.out.println("hello");
        return "protected-resource-2";
    }

    @RequestMapping("/logout")
    public String logout(HttpServletResponse httpServletResponse) {
        CookieUtil.clear(httpServletResponse, jwtTokenCookieName);
        return "redirect:/";
    }
}
