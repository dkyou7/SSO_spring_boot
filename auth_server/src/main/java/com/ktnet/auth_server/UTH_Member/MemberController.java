package com.ktnet.auth_server.UTH_Member;

import com.ktnet.auth_server.auth.CookieUtil;
import com.ktnet.auth_server.auth.JwtUtil;
import com.ktnet.auth_server.site.Site;
import com.ktnet.auth_server.site.SiteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/uth")
public class MemberController {
    private static final String jwtTokenCookieName = "JWT-TOKEN";
    private static final String signingKey = "signingKey";
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final MemberService memberService;
    private final SiteService siteService;

    public MemberController(MemberService memberService, SiteService siteService) {
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
    @GetMapping("/login")
    public String login(@RequestParam("redirect") String redirectUrl, Model model){
        logger.info("[인증서버] login() ===> 로그인 창으로 이동합니다");
        MemberDto user = new MemberDto();
        user.setRedirectUrl(redirectUrl);
        model.addAttribute("user",user);
        return "index";
    }
    @PostMapping("/login")
    public String login(HttpServletResponse httpServletResponse, String username, String password, @ModelAttribute("user") MemberDto user, Model model){
        logger.info("[인증서버] post login() ============" + username);
        Member userByEmail = memberService.findByUserId(username);
        logger.info("[인증서버] userByEmail toSting ============" + userByEmail);
        if(userByEmail == null || !userByEmail.getPRTNUM().equals(password)){
            model.addAttribute("error", "Invalid username or password!");
            return "index";
        }
        System.out.println("request.getRequestURL() = " + user.getRedirectUrl());

        // 다른 정보를 얻고싶다면 username 에 다른걸 넣을 수도 있다.
        username = userByEmail.getNM();

        String token = JwtUtil.generateToken(signingKey, username);
//        CookieUtil.create(httpServletResponse, jwtTokenCookieName, token, false, -1, "localhost");
        CookieUtil.create(httpServletResponse, jwtTokenCookieName, token, false, -1, "192.168.79.112");
        return "redirect:" + user.getRedirectUrl()+"/test/"+username;
    }
}
