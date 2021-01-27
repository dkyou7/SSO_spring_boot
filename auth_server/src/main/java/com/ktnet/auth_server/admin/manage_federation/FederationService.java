package com.ktnet.auth_server.admin.manage_federation;

import com.ktnet.auth_server.account.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class FederationService {
    
    private final FederationRepository federationRepository;

    public Federation findOrCreateNew(Account account){
        Federation federation = federationRepository.findByUidAndGid(account.getUsername(), account.getGid());
        if (federation == null){
            federation = federationRepository.save(Federation.builder().kid("testKid").gid(account.getGid()).uid(account.getUsername()).build());
        }
        return federation;
    }

    public List<Federation> findAll() {
       return federationRepository.findAll();
    }
}
