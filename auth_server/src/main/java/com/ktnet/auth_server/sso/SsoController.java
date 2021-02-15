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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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

    @Autowired
    ServletContext servletContext;

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
        servletContext.setAttribute("KID",federation.getKid());
        log.info("KID 세션 생성 완료 : " + (String)servletContext.getAttribute("KID"));
        return "Y";
//        if(!byUid.isLogin()){
//            userService.updateVidLogIn(byUid);
//            return "Y";
//        }
//        return "N";
    }
    @PostMapping("/logout")
    public String ssoLogout(){
        String kid = (String) servletContext.getAttribute("KID");
        loginCheckService.logout(kid);
        servletContext.setAttribute("KID",null);
        return "Y";
//        if(byUid != null){
//            userService.updateVidLogout(byUid);
//            return "Y";
//        }
//        return "N";
    }

    @PostMapping("/userInfo")
    public Account getUserInfo(){
        String kid = (String) servletContext.getAttribute("KID");
        log.info("SsoController::getUserInfo() KID -> " + kid);
        Federation byKid = federationService.findByKid(kid);
        log.info("SsoController::getUserInfo() UID -> " + byKid.getUid());
        Account byUsername = accountService.findByUsername(byKid.getUid());
        log.info("SsoController::getUserInfo() byUsername -> " + byUsername);
        return byUsername;
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
    @ApiOperation(value = "토큰 조회", notes = "KID의 세션이 존재하는지 검사")
    public String tokenChk(){
        try {
            String kid = (String) servletContext.getAttribute("KID");
            log.info("kid -> " + kid);
            if(loginCheckService.isLogin(kid)){
                return "Y";
            }else{
                return "N";
            }
        }catch (Exception e){
            return "N";
        }
    }
    @PostMapping("/mapping")
    @ApiOperation(value = "KID 매핑", notes = "서로 다른 두개의 아이디 매핑")
    public String mappingKid(@RequestBody String uid){
        String kid = (String) servletContext.getAttribute("KID");
        log.info("SsoController::getUserInfo() KID -> " + kid);
        federationService.mappingKid(kid,uid);
        Federation byUid = federationService.findByUid(uid);
        log.info("SsoController::mappingKid() byUid -> " + byUid);
        return "Y";
    }

    @PostMapping("/ssoLoginByKid")
    @ApiOperation(value = "KID 로그인", notes = "KID 세션를 이용한 로그인")
    public String ssoLoginByKid(@RequestBody String gid){
        String kid = (String) servletContext.getAttribute("KID");
        log.info("SsoController::getUserInfo() KID -> " + kid);
        if(kid == null){
            return "N";
        }
        Federation byKidAndGid = federationService.findByKidAndGid(kid, gid);
        return byKidAndGid.getUid();
    }
}
