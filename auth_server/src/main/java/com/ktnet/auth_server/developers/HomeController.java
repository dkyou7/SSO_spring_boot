package com.ktnet.auth_server.developers;

import com.ktnet.auth_server.account.Account;
import com.ktnet.auth_server.account.AccountService;
import com.ktnet.auth_server.user.Role;
import com.ktnet.auth_server.user.User;
import com.ktnet.auth_server.user.UserService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/developers")
@RequiredArgsConstructor
public class HomeController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final UserService userService;
    private final AccountService accountService;

    @Getter
    @Setter
    @NoArgsConstructor
    class DevUserLoginDto{
        private String email;
        private String password;
    }

    @GetMapping("/loginDev")
    public String dispLoginDev(HttpSession session,
                               Model model){
        if(session.getAttribute("user") != null){
            User user = (User)session.getAttribute("user");
            model.addAttribute("user",user);
            return "developers/index";
        }
        logger.info("[인증서버] loginDev() ===> 개발자 로그인 창으로 이동합니다");
        DevUserLoginDto dto = new DevUserLoginDto();
        model.addAttribute("user",dto);
        return "developers/login";
    }
//    @PostMapping("/loginDev")
//    public String loginDev(Model model,
//                           HttpSession session,
//                           @ModelAttribute("user") DevUserLoginDto dto){
//        logger.info("[인증서버] loginDev() ===> 개발자 로그인 start =============");
//        Account findUser = accountService.findUserByEmail(dto.getEmail());
//        if(findUser == null || !findUser.getPassword().equals(dto.getPassword())){
//            logger.info("[인증서버] loginDev() ===> 유저가 존재하지 않거나, 비밀번호가 틀립니다.");
//            model.addAttribute("error", "Invalid username or password!");
//            return "developers/login";
//        }
//        if(findUser.getUser().getRole().equals(Role.USER)){
//            logger.info("[인증서버] loginDev() ===> 어드민 권한이 없습니다.");
//            return "developers/login";
//        }
//        if(!findUser.getUser().getUserStatus().equals(UserStatus.ACTIVE)){
//            logger.info("[인증서버] loginDev() ===> 활성상태가 아닙니다.");
//            return "developers/login";
//        }
//        model.addAttribute("user",findUser);
//        session.setAttribute("user",findUser);
//
//        logger.info("[인증서버] loginDev() ===> 개발자 로그인 end =============");
//        return "developers/index";
//    }

    @GetMapping("/dispIndex")
    public String dispIndex(HttpServletRequest request,
                            Model model){
        User user = (User)request.getSession().getAttribute("user");
        model.addAttribute("user",user);
        // http://localhost:8080/developers/index
        return "index";
    }
}
