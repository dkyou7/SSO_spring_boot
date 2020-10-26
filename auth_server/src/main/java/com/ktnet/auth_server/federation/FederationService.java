package com.ktnet.auth_server.federation;

import com.ktnet.auth_server.account.Account;
import com.ktnet.auth_server.account.AccountRepository;
import com.ktnet.auth_server.site.Site;
import com.ktnet.auth_server.site.SiteRepository;
import com.ktnet.auth_server.user.User;
import com.ktnet.auth_server.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class FederationService {

    private final UserRepository userRepository;
    private final SiteRepository siteRepository;
    private final AccountRepository accountRepository;


    @Transactional
    // 추후 회원가입으로 발전시키기
    public void createDummy(Long accountId, Long siteId){
        // 엔티티 조회
        Account account = accountRepository.findById(accountId).orElseThrow(EntityNotFoundException::new);     // 영속 상태에서 제어 가능하다.
        Site site = siteRepository.findById(siteId).orElseThrow(EntityNotFoundException::new);     // 영속 상태에서 제어 가능하다.

        // 페더레이션 정보 생성
        Federation federation = Federation.createFederationInfo(site);

        // 계정 업데이트
        Account savedAccount = Account.updateAccount(account,federation);

        // 계정 저장
        accountRepository.save(savedAccount);
    }
}
