package com.ktnet.auth_server.admin.manage_user;

import com.ktnet.auth_server.gid.Gid;
import com.ktnet.auth_server.user.User;
import com.ktnet.auth_server.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Set;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/mu")
public class UserController {

    private final UserService userService;
    private final SignUpFormValidator signUpFormValidator;

    // 변수명 말고 타입이랑 매핑됨.
    @InitBinder("signUpForm")
    public void initBinder(WebDataBinder webDataBinder){
        webDataBinder.addValidators(signUpFormValidator);
    }

    /**
     * 사용자 관리 홈
     */
    @GetMapping("/user-manage-home")
    public String disp_users_manage(Model model){
        return "admin/manage_user/user-manage-home";
    }
    /**
     * 사용자 전체 조회
     */
    @GetMapping("/users")
    public String find_all_users(Model model){
        model.addAttribute("users",userService.findAll());
        return "admin/manage_user/users";
    }

    @GetMapping("/user/{uid}")
    public String user_detail_by_userid(@PathVariable("uid") String uid, Model model){
        User byUid = userService.findByUid(uid);
        model.addAttribute("user",byUid);
        return "admin/manage_user/profile";
    }

    /**
     * 비밀번호 초기화
     */
    @GetMapping("/resetPW/{uid}")
    public String user_passwd_reset_by_userid(@PathVariable("uid") String uid, RedirectAttributes attributes, Model model){
        String new_password = userService.resetPW(uid);
        User byUid = userService.findByUid(uid);
        model.addAttribute("user",byUid);
        attributes.addFlashAttribute("newPW","새로운 비밀번호 : " + new_password);
        return "redirect:/admin/mu/user/"+uid;
    }

    @GetMapping("/sign-up")
    public String signUpForm(Model model){
//        model.addAttribute("signUpForm",new SignUpForm());
        model.addAttribute(new SignUpForm());
        return "admin/manage_user/sign-up";   // 스프링 부트 자동 설정에 의해 resource
    }

    @PostMapping("/sign-up")
    public String signUpSubmit(@Valid SignUpForm signUpForm, Errors errors){
        if(errors.hasErrors()){
            return "account/sign-up";
        }
        userService.processNewAccount(signUpForm);
        return "redirect:/admin/mu/users";
    }
    @GetMapping("/user/gids")
    public Set<Gid> updateGidSet(String email){
        Set<Gid> gidSet = userService.getGids(email);
        return gidSet;
    }
}
