package com.ktnet.auth_server.admin;

import com.ktnet.auth_server.client.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class ClientController {

    private final ClientRepository clientRepository;

    /**
     * 클라이언트 관리 홈
     */
    @GetMapping("/client-manage-home")
    public String disp_client_manage(Model model){
        return "admin/client/client-manage-home";
    }


    /**
     * 클라이언트 전체 조회
     */
    @GetMapping("/clients")
    public String find_all_clients(Model model){
        model.addAttribute("clients",clientRepository.findAll());
        return "admin/client/clients";
    }
}
