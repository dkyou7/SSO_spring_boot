package com.ktnet.auth_server.admin.manage_federation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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


}
