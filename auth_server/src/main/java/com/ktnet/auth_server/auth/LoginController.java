package com.ktnet.auth_server.auth;

import com.ktnet.auth_server.client.ClientService;
import com.ktnet.auth_server.user.Role;
import com.ktnet.auth_server.user.User;
import com.ktnet.auth_server.user.UserService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

//    @GetMapping("/devLogin")
//    public String dispLoginDev(Model model){
//        logger.info("[인증서버] loginDev() ===> 개발자 로그인 창으로 이동합니다");
//        DevUserLoginDto dto = new DevUserLoginDto();
//        model.addAttribute("user",dto);
//        return "developers/login";
//    }
//
//    @PostMapping("/devLogin")
//    public String loginDev(Model model,
//                           HttpSession session,
//                           @ModelAttribute("user") DevUserLoginDto dto){
//        logger.info("[인증서버] loginDev() ===> 개발자 로그인 start =============");
//        User findUser = userService.findUserByEmail(dto.getEmail());
//        if(findUser == null || !findUser.getPassword().equals(dto.getPassword())){
//            logger.info("[인증서버] loginDev() ===> 유저가 존재하지 않거나, 비밀번호가 틀립니다.");
//            model.addAttribute("error", "Invalid username or password!");
//            return "developers/login";
//        }
//        if(findUser.getRole().equals(Role.USER)){
//            logger.info("[인증서버] loginDev() ===> 어드민 권한이 없습니다.");
//            return "developers/login";
//        }
//        model.addAttribute("user",findUser);
//        session.setAttribute("user",findUser);
//        return "developers/index";
//    }

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
    public String login(HttpSession session,
                        HttpServletResponse httpServletResponse,
                        @ModelAttribute("user") User user, Model model){
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
        session.setAttribute(user.getEmail(),token);
        CookieUtil.create(httpServletResponse, jwtTokenCookieName, token, false, -1, "localhost");
        return "redirect:" + user.getRedirectUrl()+"/test/"+token;
    }
}
