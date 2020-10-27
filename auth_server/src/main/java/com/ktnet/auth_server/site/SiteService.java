package com.ktnet.auth_server.site;

import com.ktnet.auth_server.UTH_Member.MemberRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class SiteService {

    private final SiteRepository siteRepository;

    public SiteService(SiteRepository siteRepository) {
        this.siteRepository = siteRepository;
    }

    @Transactional
    public Site findByGID(String kcmnt) {
        return siteRepository.findBygID(kcmnt);
    }
}
