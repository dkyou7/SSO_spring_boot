package com.ktnet.auth_server.auth;

import com.ktnet.auth_server.UTH_Member.Member;
import com.ktnet.auth_server.UTH_Member.MemberService;
import com.ktnet.auth_server.site.FederationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/uth")
public class LoginController {
    private static final String jwtTokenCookieName = "JWT-TOKEN";
    private static final String signingKey = "signingKey";
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final MemberService memberService;
    private final FederationService federationService;

    public LoginController(MemberService memberService, FederationService federationService) {
        this.memberService = memberService;
        this.federationService = federationService;
    }

    @GetMapping("/test")
    public Member uthtest(){
        Member memberByPrtnum = memberService.findByUserId("MILLEPIA");

        return memberByPrtnum;
    }
}
