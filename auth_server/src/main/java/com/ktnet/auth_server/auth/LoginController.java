package com.ktnet.auth_server.auth;

import com.ktnet.auth_server.UTH_Member.Member;
import com.ktnet.auth_server.UTH_Member.MemberService;
import com.ktnet.auth_server.site.Site;
import com.ktnet.auth_server.site.SiteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/uth")
public class LoginController {
    private static final String jwtTokenCookieName = "JWT-TOKEN";
    private static final String signingKey = "signingKey";
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final MemberService memberService;
    private final SiteService siteService;

    public LoginController(MemberService memberService, SiteService siteService) {
        this.memberService = memberService;
        this.siteService = siteService;
    }

    @GetMapping("/test")
    public Member uthtest(){
        Member memberByPrtnum = memberService.findByUserId("MILLEPIA");
        Site site = siteService.findByGID("KCMNT");

        System.out.println("site = " + site);

        return memberByPrtnum;
    }
}
