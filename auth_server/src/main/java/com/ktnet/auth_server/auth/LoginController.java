package com.ktnet.auth_server.auth;

import com.ktnet.auth_server.user.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Controller
public class LoginController {
    private static final String jwtTokenCookieName = "JWT-TOKEN";
    private static final String signingKey = "signingKey";
    private static final Map<String, String> credentials = new HashMap<>();

    public LoginController() {
        credentials.put("hellokoding", "hellokoding");
        credentials.put("hellosso", "hellosso");
    }

    @GetMapping("/")
    public String home(){
        System.out.println("hello");
        return "redirect:/loginTest";
    }

    @GetMapping("/login")
    public String login(@RequestParam("redirect") String redirectUrl,Model model){
        System.out.println("hello" + redirectUrl);
        User user = new User();
        user.setRedirectUrl(redirectUrl);
        model.addAttribute("user",user);
        return "loginTest";
    }

    @PostMapping("/login")
    public String login(HttpServletResponse httpServletResponse, String username, String password,@ModelAttribute("user") User user, Model model){
        if (username == null || !credentials.containsKey(username) || !credentials.get(username).equals(password)){
            model.addAttribute("error", "Invalid username or password!");
            return "loginTest";
        }
        System.out.println("request.getRequestURL() = " + user.getRedirectUrl());

        String token = JwtUtil.generateToken(signingKey, username);
        CookieUtil.create(httpServletResponse, jwtTokenCookieName, token, false, -1, "localhost");

        return "redirect:" + user.getRedirectUrl();
    }
}
