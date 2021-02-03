package com.ktnet.auth_server.admin.manage_federation;

import com.ktnet.auth_server.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

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
        model.addAttribute("federation",KidUpdateForm.toDto(byUid));
        return "admin/manage_federation/profile";
    }

    @PostMapping("/federation/{uid}")
    public String setKid(@PathVariable("uid") String uid, KidUpdateForm federation,
                         RedirectAttributes attributes, Model model){
        Federation byUid = federationService.updateKidByUid(uid,federation.getKid());   // uid 기반으로 kid 수정
        model.addAttribute("federation",byUid);
        attributes.addFlashAttribute("message","kid 수정 완료");

        return "redirect:/admin/mf/federation/"+uid;
    }

    @PostMapping("/federation/{uid}/disabled")
    public String deleteMapping(@PathVariable("uid") String uid, KidUpdateForm federation,
                                RedirectAttributes attributes, Model model){
        Federation byUid = federationService.createKid(uid);// 걍 새로운 kid를 만들어주면 갱신된다.
        model.addAttribute("federation",byUid);
        attributes.addFlashAttribute("message","매핑 해제 완료");

        return "redirect:/admin/mf/federation/"+uid;
    }
}
