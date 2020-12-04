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

}
