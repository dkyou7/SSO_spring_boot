package com.ktnet.auth_server.UTH_Member;

import com.ktnet.auth_server.site.Site;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional
    public Member findByUserId(String id){
        return memberRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

}
