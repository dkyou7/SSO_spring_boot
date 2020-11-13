package com.ktnet.auth_server.auth;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class SessionInfoService {

    private final SessionInfoRepository sessionInfoRepository;

    @Transactional
    public String save(String info){
        SessionInfo sessionInfo = SessionInfo.builder().code("test").info(info).build();
        sessionInfoRepository.save(sessionInfo);

        return sessionInfo.getCode();
    }

    @Transactional
    public SessionInfo findByCode(String code){
        return sessionInfoRepository.findByCode(code);
    }
}
