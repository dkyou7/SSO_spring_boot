package com.ktnet.auth_server.sso;

import com.ktnet.auth_server.account.Account;
import com.ktnet.auth_server.account.AccountService;
import com.ktnet.auth_server.admin.manage_federation.Federation;
import com.ktnet.auth_server.admin.manage_federation.FederationService;
import com.ktnet.auth_server.logincheck.LoginCheckService;
import com.ktnet.auth_server.user.User;
import com.ktnet.auth_server.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Api(tags = {"1. SSO"})
@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class SsoController {

    private final UserService userService;

    private final FederationService federationService;

    private final LoginCheckService loginCheckService;

    private final AccountService accountService;

    @Resource
    private SessionInfo sessionInfo;

    @ApiOperation(value = "GET 접속 테스트", notes = "api가 잘 동작하는지 테스트 해본다.")
    @GetMapping("/test")
    public String apiTest_get(String testMsg){
        return "api get test success";
    }

    @ApiOperation(value = "POST 접속 테스트", notes = "post 방식의 api가 잘 동작하는지 테스트 해본다.")
    @PostMapping("/test")
    public String apiTest(@RequestBody String testMsg){
        return "api post test success";
    }

    @PostMapping("/isSSO")
    public String findUser(@RequestBody String email){
        log.info(email);
        User byUid = userService.findByUid(email);
        return byUid.getName();
    }

    // 로그인 되어있는지 확인하는 로직
    @PostMapping("/isLogin")
    public String findUser2(@RequestBody String email){
        log.info(email);
        User byUid = userService.findByUid(email);
        if("Y".equals(byUid.getIsLogin())){
            return "Y";
        }else{
            return "N";
        }
    }
    @ApiOperation(value = "로그인",notes = "federation DB 업데이트 이후 세션에 KID 저장")
    @PostMapping("/login")
    public String ssoLogin(@RequestBody String email){
        log.info(email);
        Federation federation = federationService.findByUid(email);
        loginCheckService.login(federation.getKid());

        // 세션에 저장
        sessionInfo.setSessionId("KID");
        sessionInfo.setToken(federation.getKid());
        return "Y";
//        if(!byUid.isLogin()){
//            userService.updateVidLogIn(byUid);
//            return "Y";
//        }
//        return "N";
    }
    @PostMapping("/logout")
    public String ssoLogout(@RequestBody String email){
        log.info(email);
        federationService.logout(email);
        return "Y";
//        if(byUid != null){
//            userService.updateVidLogout(byUid);
//            return "Y";
//        }
//        return "N";
    }

    @PostMapping("/userInfo")
    public User getUserInfo(@RequestBody String email){
        log.info(email);
        User byUid = userService.findByUid(email);
        return byUid;
    }

    private final ArticleRepository articleRepository;

    @PostMapping("/article")
    public ResponseEntity<Article> write(@RequestBody Article article){
        Article save = articleRepository.save(article);
        log.info("write: {}", article.toString());
        return ResponseEntity.ok(article);
    }

    @PostMapping("/user/federation")
    public ResponseEntity<String> federation(@RequestBody Account account){
        Federation res = federationService.findOrCreateNew(account);
        log.info("create federation : {}", res.toString());
        return ResponseEntity.ok(res.toString());
    }

    /**
     * 회원 가입 요청이 들어오면, Federation, LoginCheck table을 구성한다.
     * @param account
     * @return
     */
    @PostMapping("/signUp")
    public ResponseEntity<String> signUpSSO(@RequestBody Account account){
        accountService.signUp(account);     // 1. 계정 테이블에 저장
        Federation res = federationService.findOrCreateNew(account);    // 2. federation 테이블에 저장.
        loginCheckService.save(res);    // 3. federation 정보와, 로그인YN 결정 해주는 테이블 저장
        log.info("create federation : {}", res.toString());
        return ResponseEntity.ok(res.toString());
    }

    @PostMapping("/tokenCheck")
    @ApiOperation(value = "토큰 조회", notes = "해당 클라이언트의 토큰이 유효한지 검사")
    public ResponseEntity tokenChk(@RequestBody String sessionId, HttpServletRequest request){
        return ResponseEntity.ok(sessionInfo.toString());
    }
}
