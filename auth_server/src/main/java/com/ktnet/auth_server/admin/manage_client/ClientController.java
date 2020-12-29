package com.ktnet.auth_server.admin.manage_client;

import com.ktnet.auth_server.admin.manage_user.SignUpForm;
import com.ktnet.auth_server.client.Client;
import com.ktnet.auth_server.client.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/mc")
public class ClientController {

    private final ClientService clientService;
    private final ClientRepository clientRepository;

    /**
     * 클라이언트 관리 홈
     */
    @GetMapping("/client-manage-home")
    public String disp_client_manage(Model model){
        return "admin/manage_client/client-manage-home";
    }


    /**
     * 클라이언트 전체 조회
     */
    @GetMapping("/clients")
    public String find_all_clients(Model model){
        model.addAttribute("clients",clientRepository.findAll());
        return "admin/manage_client/clients";
    }

    /**
     * 클라이언트 등록 페이지 이동
     */
    @GetMapping("/sign-up")
    public String signUpForm(Model model){
        model.addAttribute("signUpForm",new Client_SignUpForm());
        return "admin/manage_client/sign-up";   // 스프링 부트 자동 설정에 의해 resource
    }

    /**
     * 클라이언트 등록
     */
    @PostMapping("/sign-up")
    public String sign_up_submit(@Valid Client_SignUpForm signUpForm, Errors errors, RedirectAttributes attributes, Model model){
        if(errors.hasErrors()){
            model.addAttribute("signUpForm",signUpForm);
            return "admin/manage_client/sign-up";
        }
        Client client = Client_SignUpForm.toEntity(signUpForm);
        clientService.save(client);
        attributes.addFlashAttribute("message","클라이언트 추가 완료");
        return "redirect:/admin/mc/clients";
    }
}
