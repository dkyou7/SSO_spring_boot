package com.ktnet.auth_server.gid;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class GidService {

    private final GidRepository gidRepository;

    public void save(Gid gid){
        gidRepository.save(gid);
    }
}
