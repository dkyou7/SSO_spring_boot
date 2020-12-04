package com.ktnet.auth_server.UTH_Member;

import com.ktnet.auth_server.auth.CookieUtil;
import com.ktnet.auth_server.auth.JwtUtil;
import com.ktnet.auth_server.site.Federation;
import com.ktnet.auth_server.site.FederationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class MemberController {
    private static final String jwtTokenCookieName = "JWT-TOKEN";
    private static final String signingKey = "signingKey";
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final MemberService memberService;
    private final FederationService federationService;

    public MemberController(MemberService memberService, FederationService federationService) {
        this.memberService = memberService;
        this.federationService = federationService;
    }

    @GetMapping("/test")
    public Member uthtest(){
        Member memberByPrtnum = memberService.findByUserId("MILLEPIA");

        return memberByPrtnum;
    }

    class LoginDto{
        private String username;
        private String password;
        private String redirectUrl;
        private String clientId;
        public LoginDto() { }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getRedirectUrl() {
            return redirectUrl;
        }

        public void setRedirectUrl(String redirectUrl) {
            this.redirectUrl = redirectUrl;
        }

        public String getClientId() {
            return clientId;
        }

        public void setClientId(String clientId) {
            this.clientId = clientId;
        }
    }

    @GetMapping("/login")
    public String login(@RequestParam("redirect_uri") String redirectUrl,
                        @RequestParam("client_id") String clientId,
                        @RequestParam("response_type")String resType,
                        Model model){
        if(resType == null || !resType.equals("code")){
            logger.info("[인증서버] login() ===> 코드 방식이 아닙니다. 접근 불가!");
            return "redirect:"+redirectUrl+"/fail";
        }
        logger.info("[인증서버] login() ===> 로그인 창으로 이동합니다");
        LoginDto loginDto = new LoginDto();
        loginDto.setClientId(clientId);
        loginDto.setUsername("");
        loginDto.setPassword("");
        loginDto.setRedirectUrl(redirectUrl);
        model.addAttribute("user",loginDto);
        return "index";
    }
    @PostMapping("/login")
    public String login(HttpSession session,
                        HttpServletResponse httpServletResponse,
                        @RequestParam("username") String email,
                        @RequestParam("password") String password,
                        @RequestParam("redirectUrl") String redirectUrl,
                        @RequestParam("clientId") String clientId,
                        Model model){
        logger.info("[인증서버] post login() ============");
        Member userByEmail = memberService.findByUserId(email);
        logger.info("[인증서버] userByEmail toSting ============" + userByEmail);
        if(userByEmail == null || !userByEmail.getPRTNUM().equals(password)){
            model.addAttribute("error", "Invalid username or password!");
            return "index";
        }
        Federation federation = federationService.findByUserID(userByEmail.getUSERID());
        /**
         * todo: k-sign 으로 토큰 요청 보내야 한다.
         */
        String vid = federation.getVID();
        String vid_token = JwtUtil.generateToken(signingKey,vid);

        // 다른 정보를 얻고싶다면 username 에 다른걸 넣을 수도 있다.
        String username = userByEmail.getNM();

        String token = JwtUtil.generateToken(signingKey, username);
//        CookieUtil.create(httpServletResponse, jwtTokenCookieName, token, false, -1, "localhost");
        /**
         * todo: redirect url 을 분해해서 도메인으로 만들어 주면 되지 않을까?
         */
        CookieUtil.create(httpServletResponse, jwtTokenCookieName, token, false, -1, "192.168.79.112");
        return "redirect:" + redirectUrl+"/test/"+token;
    }
}
