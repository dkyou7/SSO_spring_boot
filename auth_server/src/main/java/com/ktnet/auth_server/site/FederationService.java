package com.ktnet.auth_server.site;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class FederationService {

    private final FederationRepository federationRepository;

    public FederationService(FederationRepository federationRepository) {
        this.federationRepository = federationRepository;
    }

    @Transactional
    public Federation findByUserID(String userId){
        return federationRepository.findbyUserId(userId);
    }

}
