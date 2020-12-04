package com.ktnet.testClient2.user;


import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;


@RequiredArgsConstructor
@Controller
@RequestMapping(value = "/v1")
public class UserController {

    private final UserRepository userRepository;

    // http://localhost:8080/v1/users?access_token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2MDcwMDg4ODIsInVzZXJfbmFtZSI6ImRreW91N0BuYXZlci5jb20iLCJhdXRob3JpdGllcyI6WyJST0xFX1VTRVIiXSwianRpIjoiNzU0MDU0ZGYtMDk3Yi00MWM5LWE3ZmEtZjUxMjVjYTE0Mzc1IiwiY2xpZW50X2lkIjoidGVzdENsaWVudElkIiwic2NvcGUiOlsicmVhZCJdfQ.xVfF
    @GetMapping(value = "/users")
    public List<User> findAllUser() {
        return userRepository.findAll();
    }

    @GetMapping("/find/{username}")
    public User findUserByUsername(@PathVariable("username") String username){
        return userRepository.findByUid(username);
    }

    @GetMapping("/test")
    public String success(){
        return "succes";
    }

    //    @PreAuthorize("#oauth2.hasScope('read')")
    @GetMapping("/api/member")
    public String member(@AuthenticationPrincipal OAuth2Authentication authentication, Model model) {
        String username = authentication.getUserAuthentication().getPrincipal().toString();
//        Set<String> scopes = authentication.getOAuth2Request().getScope();
//        User byUid = userRepository.findByUid(username);
        System.out.println("username = " + username);
        model.addAttribute("username",username);
        return "index";
    }
}