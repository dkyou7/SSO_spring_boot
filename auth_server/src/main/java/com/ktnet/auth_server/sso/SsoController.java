package com.ktnet.auth_server.sso;

import com.ktnet.auth_server.account.Account;
import com.ktnet.auth_server.admin.manage_federation.Federation;
import com.ktnet.auth_server.admin.manage_federation.FederationService;
import com.ktnet.auth_server.logincheck.LoginCheckService;
import com.ktnet.auth_server.user.User;
import com.ktnet.auth_server.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class SsoController {

    private final UserService userService;

    private final LoginCheckService loginCheckService;

    @GetMapping("/test")
    public String apiTest_get(String testMsg){
        return "api get test success";
    }

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
    @PostMapping("/login")
    public String ssoLogin(@RequestBody String email){
        log.info(email);
        User byUid = userService.findByUid(email);
        log.info("Auth Server에서의 로그인 유무 : " + byUid.getIsLogin());
        userService.updateVidLogIn(byUid);
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
        User byUid = userService.findByUid(email);
        userService.updateVidLogout(byUid);
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

    private final FederationService federationService;

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
        Federation res = federationService.findOrCreateNew(account);
        loginCheckService.save(res);    // federation 정보와, 로그인YN 결정 해주는 테이블 저장
        log.info("create federation : {}", res.toString());
        return ResponseEntity.ok(res.toString());
    }
}
