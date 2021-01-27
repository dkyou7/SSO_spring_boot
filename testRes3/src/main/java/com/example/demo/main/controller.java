package com.example.demo.main;

import com.example.demo.account.LoginForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class controller {

    @GetMapping("/federation-mapping")
    public String dispFedMapping(Model model){
        model.addAttribute("username","test");
        return "federation-mapping";
    }
}
