package com.ktnet.auth_server.admin;

import com.ktnet.auth_server.account.AccountService;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;
    private final AccountService accountService;

    @Getter
    @Setter
    @NoArgsConstructor
    class LoginForm{
        private String email;
        private String password;
    }

    @GetMapping("/admin")
    public String dispLoginDev(Model model){
        model.addAttribute(new LoginForm());
        return "admin/index";
    }
    @GetMapping("/login")
    public String login(){
        return "admin/login";
    }

    /**
     * 사용자 관리 홈
     */
    @GetMapping("/user-manage-home")
    public String disp_users_manage(Model model){
        return "admin/user-manage-home";
    }
    /**
     * 사용자 전체 조회
     */
    @GetMapping("/users")
    public String find_all_users(Model model){
        model.addAttribute("users",userService.findAll());
        return "admin/users";
    }

    @GetMapping("/user/{uid}")
    public String user_detail_by_userid(@PathVariable("uid") String uid, Model model){
        User byUid = userService.findByUid(uid);
        model.addAttribute("user",byUid);
        return "admin/profile";
    }
}
