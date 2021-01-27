package com.ktnet.auth_server.logincheck;

import com.ktnet.auth_server.federation.Federation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class LoginCheckService {
    private final LoginCheckRepository loginCheckRepository;

    public void save(Federation res) {
        LoginCheck loginCheck = LoginCheck.builder().kid(res.getKid()).isLogin(false).build();
        loginCheckRepository.save(loginCheck);
    }

    public boolean isLogin(Federation res){
        LoginCheck byKid = loginCheckRepository.findByKid(res.getKid());
        return byKid.isLogin();
    }
}
