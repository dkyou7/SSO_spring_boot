package com.ktnet.testRes1.user;

import com.ktnet.testRes1.sso.SSOService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Set;


@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/v1")
public class UserController {

    private final UserRepository userRepository;
    private final SSOService ssoService;

    private final WebClient webClient = WebClient.builder()
            .baseUrl("http://localhost:8081/api/v1")
            .build();

    @GetMapping("/user/{msrl}")
    public User findUserByMsrl(@PathVariable("msrl")Long msrl){
        return userRepository.findByMsrl(msrl);
    }

    // http://localhost:8080/v1/users?access_token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2MDcwMDg4ODIsInVzZXJfbmFtZSI6ImRreW91N0BuYXZlci5jb20iLCJhdXRob3JpdGllcyI6WyJST0xFX1VTRVIiXSwianRpIjoiNzU0MDU0ZGYtMDk3Yi00MWM5LWE3ZmEtZjUxMjVjYTE0Mzc1IiwiY2xpZW50X2lkIjoidGVzdENsaWVudElkIiwic2NvcGUiOlsicmVhZCJdfQ.xVfF
    @GetMapping(value = "/users")
    @ResponseBody
    public List<User> findAllUser() {
        return userRepository.findAll();
    }

    //    @PreAuthorize("#oauth2.hasScope('read')")
    @GetMapping("/success")
    @ResponseBody
    public String member(@AuthenticationPrincipal OAuth2Authentication authentication, String access_token, Model model) {
        log.info("access token : " + access_token);
        String username = authentication.getUserAuthentication().getPrincipal().toString();
        String password = authentication.getCredentials().toString();
//        Set<String> scopes = authentication.getOAuth2Request().getScope();
//        User byUid = userRepository.findByUid(username);
        model.addAttribute("username",username);

//        ssoService.federationRequest();
        ssoService.getUserInfo(access_token);
        return "index";
    }
}
