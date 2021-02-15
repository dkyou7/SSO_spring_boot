package com.ktnet.auth_server.admin.manage_federation;

import com.ktnet.auth_server.account.Account;
import com.ktnet.auth_server.logincheck.LoginCheck;
import com.ktnet.auth_server.logincheck.LoginCheckService;
import com.ktnet.auth_server.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class FederationService {
    
    private final FederationRepository federationRepository;
    private final LoginCheckService loginCheckService;

    public Federation findOrCreateNew(Account account){
        Federation federation = federationRepository.findByUidAndGid(account.getUsername(), account.getGid());
        if (federation == null){
            federation = federationRepository.save(Federation.builder().kid(UUID.randomUUID().toString()).gid(account.getGid()).uid(account.getUsername()).build());
            // kid 가 같은 경우를 테스트해보자.
//            federation = federationRepository.save(Federation.builder().kid("123").gid(account.getGid()).uid(account.getUsername()).build());
        }
        return federation;
    }

    public List<Federation> findAll() {
       return federationRepository.findAll();
    }

    public Federation findByUid(String uid) {
        return federationRepository.findByUid(uid);
    }

    public Federation updateKidByUid(String uid,String kid) {
        Federation byUid = federationRepository.findByUid(uid);
        byUid.updateKid(kid);
        return byUid;
    }

    public Federation createKid(String uid) {
        Federation byUid = federationRepository.findByUid(uid);
        byUid.updateKid(UUID.randomUUID().toString());
        return byUid;
    }

    public void logout(String email) {
        Federation byUid = federationRepository.findByUid(email);
        loginCheckService.logout(byUid.getKid());
    }

    public Federation findByKid(String kid) {
        return federationRepository.findByKid(kid);
    }
}
