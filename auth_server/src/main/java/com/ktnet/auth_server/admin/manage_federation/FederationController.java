package com.ktnet.auth_server.admin.manage_federation;

import com.ktnet.auth_server.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/mf")
@RequiredArgsConstructor
public class FederationController {

    private final FederationService federationService;

    /**
     * 각 클라이언트 회원 페더레이션 전체 조회
     */
    @GetMapping("/federations")
    public String find_all_users(Model model){
        model.addAttribute("federations",federationService.findAll());
        return "admin/manage_user/federation-mapping";
    }

    /**
     * Federation을 위한 Details
     */
    @GetMapping("/federation/{uid}")
    public String user_detail_by_userid(@PathVariable("uid") String uid, Model model){
        Federation byUid = federationService.findByUid(uid);
        model.addAttribute("user",byUid);
        return "admin/manage_federation/profile";
    }

    @PostMapping("/setKid")
    public String setKid(@ModelAttribute Federation federation, RedirectAttributes attributes){


        return "admin/manage_federation/profile";
    }
}
