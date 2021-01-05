package com.ktnet.auth_server.gid;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class GidService {

    private final GidRepository gidRepository;

    public Gid findOrCreateNew(Gid gid){
        Gid res = gidRepository.findByTitle(gid.getTitle());
        if(res == null){
            res = gidRepository.save(gid);
        }
        return res;
    }
}
