package com.ktnet.auth_server.oauth2;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

/**
 * oauth approval customize
 */
@Controller
@RequiredArgsConstructor
@SessionAttributes(types = AuthorizationRequest.class)
public class OAuth2Controller {
    private final ClientDetailsService clientDetailsService;

    @RequestMapping("/oauth/confirm_access")
    public String getAccessConfirmation(@ModelAttribute AuthorizationRequest clientAuth, Model model) throws Exception {
        ClientDetails client = clientDetailsService.loadClientByClientId(clientAuth.getClientId());

        model.addAttribute("auth_request", clientAuth.getRequestParameters().get("scope"));
        model.addAttribute("client", client.getClientId());
        return "admin/access_confirmation";
    }
}
