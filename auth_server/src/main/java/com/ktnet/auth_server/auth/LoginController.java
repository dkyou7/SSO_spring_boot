package com.ktnet.auth_server.auth;

import com.ktnet.auth_server.client.ClientService;
import com.ktnet.auth_server.user.User;
import com.ktnet.auth_server.user.UserService;
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
public class LoginController {
    private static final String jwtTokenCookieName = "JWT-TOKEN";
    private static final String signingKey = "signingKey";
    private static final Map<String, String> credentials = new HashMap<>();
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final UserService userService;

    private final ClientService clientService;

    public LoginController(UserService userService, ClientService clientService) {
        this.userService = userService;
        this.clientService = clientService;
        credentials.put("hellokoding", "hellokoding");
        credentials.put("hellosso", "hellosso");
    }

    @GetMapping("/")
    public String home(){
        logger.info("[인증서버] home() ===> 로그인 창으로 이동합니다");
        return "redirect:/loginTest";
    }

    @GetMapping("/login")
    public String login(@RequestParam("redirect_uri") String redirectUrl,
                        @RequestParam("client_id") String clientId,
                        @RequestParam("response_type")String resType,
                        Model model){
        if(resType == null || !resType.equals("code")){
            logger.info("[인증서버] login() ===> 코드 방식이 아닙니다. 접근 불가!");
            return "redirect:"+redirectUrl+"/fail";
        }
        logger.info("[인증서버] login() ===> 로그인 창으로 이동합니다");
        User user = new User();
        user.setRedirectUrl(redirectUrl);
        user.setClientId(clientId);
        model.addAttribute("user",user);
//        return "loginTest";
        return "index";
    }

    @PostMapping("/login")
    public String login(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @ModelAttribute("user") User user, Model model){
        logger.info("[인증서버] post login() ============" + user);
        User findUser = userService.findUserByEmail(user.getEmail());
        if(findUser == null || !findUser.getPassword().equals(user.getPassword())){
//        if (username == null || !credentials.containsKey(username) || !credentials.get(username).equals(password)){
            model.addAttribute("error", "Invalid username or password!");
            return "index";
        }
        findUser.mergeUser(user);   // 유저정보를 업데이트 한다.
        String token = JwtUtil.generateToken(signingKey, user.getEmail());

        // 쿠키와 세션을 동시에 만들어준다.
        HttpSession httpSession = httpServletRequest.getSession();
        httpSession.setAttribute(user.getEmail(),token);
        CookieUtil.create(httpServletResponse, jwtTokenCookieName, token, false, -1, "localhost");
        return "redirect:" + user.getRedirectUrl()+"/test/"+token;
    }
}
