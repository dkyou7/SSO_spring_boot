package com.ktnet.auth_server.auth;

import com.ktnet.auth_server.user.User;
import com.ktnet.auth_server.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
        credentials.put("hellokoding", "hellokoding");
        credentials.put("hellosso", "hellosso");
    }

    @GetMapping("/")
    public String home(){
        logger.info("[인증서버] home() ===> 로그인 창으로 이동합니다");
        return "redirect:/loginTest";
    }

    @GetMapping("/login")
    public String login(@RequestParam("redirect") String redirectUrl,Model model){
        logger.info("[인증서버] login() ===> 로그인 창으로 이동합니다");
        User user = new User();
        user.setRedirectUrl(redirectUrl);
        model.addAttribute("user",user);
//        return "loginTest";
        return "index";
    }

    @PostMapping("/login")
    public String login(HttpServletResponse httpServletResponse, String username, String password,@ModelAttribute("user") User user, Model model){
        logger.info("[인증서버] post login() ============" + username);
        User userByEmail = userService.findUserByEmail(username);
        logger.info("[인증서버] userByEmail toSting ============" + userByEmail);
        if(userByEmail == null || !userByEmail.getPassword().equals(password)){
//        if (username == null || !credentials.containsKey(username) || !credentials.get(username).equals(password)){
            model.addAttribute("error", "Invalid username or password!");
            return "loginTest";
        }
        System.out.println("request.getRequestURL() = " + user.getRedirectUrl());

        String token = JwtUtil.generateToken(signingKey, username);
        CookieUtil.create(httpServletResponse, jwtTokenCookieName, token, false, -1, "localhost");
        return "redirect:" + user.getRedirectUrl()+"/test/"+username;
    }
}
