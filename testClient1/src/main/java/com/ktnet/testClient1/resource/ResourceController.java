package com.ktnet.testClient1.resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;

@Controller
public class ResourceController {
    private static final String jwtTokenCookieName = "JWT-TOKEN";

    @GetMapping("/")
    public String home() {
        return "redirect:/protected-resource-1";
    }

    @GetMapping("/protected-resource-1")
    public String protectedResource() {
        System.out.println("hello res 1");
        return "protected-resource-1";
    }

    @GetMapping("/logout")
    public String logout(HttpServletResponse httpServletResponse) {
        CookieUtil.clear(httpServletResponse, jwtTokenCookieName);
        return "redirect:/";
    }
}
