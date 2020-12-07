package com.ktnet.auth_server.admin;

import com.ktnet.auth_server.user.UserService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;

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
