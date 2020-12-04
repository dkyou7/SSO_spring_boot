package com.ktnet.auth_server.admin;

import com.ktnet.auth_server.user.User;
import com.ktnet.auth_server.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

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
