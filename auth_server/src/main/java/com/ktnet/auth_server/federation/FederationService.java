package com.ktnet.auth_server.federation;

import com.ktnet.auth_server.account.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

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
}
