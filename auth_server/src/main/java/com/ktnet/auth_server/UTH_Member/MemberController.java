package com.ktnet.auth_server.UTH_Member;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberController {
    private static final String jwtTokenCookieName = "JWT-TOKEN";
    private static final String signingKey = "signingKey";
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/uthtest")
    public Member uthtest(){
        Member memberByPrtnum = memberService.findByUserId("BS01000");
        return memberByPrtnum;
    }
}
