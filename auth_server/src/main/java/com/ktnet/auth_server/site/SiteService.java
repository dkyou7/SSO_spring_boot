package com.ktnet.auth_server.site;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class SiteService {

    private final SiteRepository siteRepository;

    @Transactional
    private void save(Site site){
        siteRepository.save(site);
    }

    @Transactional
    public void createDummy() {
        Site site1 = Site.builder().siteNickname("KMCNT")
                .siteName("KTNETPLUS").siteUrl("www.ktnet.com")
                .clientId("testRes1").redirectUrl("http://localhost:8181/success").build();
        Site site2 = Site.builder().siteNickname("BOAN").siteName("KTNET 보안1번지").siteUrl("www.boan1st.com")
                .clientId("testRes2").redirectUrl("http://localhost:8183/success").build();
        siteRepository.save(site1);
        siteRepository.save(site2);
    }
}
