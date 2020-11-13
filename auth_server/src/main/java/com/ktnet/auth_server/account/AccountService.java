package com.ktnet.auth_server.account;

import com.ktnet.auth_server.user.User;
import com.ktnet.auth_server.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;

    private final UserRepository userRepository;

    @Transactional
    public void save(Account account){
        accountRepository.save(account);
    }

    @Transactional
    public void createDummy() {
        Account account1 = Account.builder().email("test1@naver.com").password("123").build();
        accountRepository.save(account1);

        Account account2 = Account.builder().email("test2@naver.com").password("123").build();
        accountRepository.save(account2);

        Account account3 = Account.builder().email("test3@naver.com").password("123").build();
        accountRepository.save(account3);
    }

    @Transactional
    public Account findUserByEmail(String email) {
        return accountRepository.findByEmail(email);
    }

    @Transactional
    public void connect(Long userId, Long accountId) {
        Account account = accountRepository.findById(accountId).orElseThrow(EntityNotFoundException::new);
        User user = userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
        account.setUser(user);
    }

    @Transactional
    public int getSize(){
        return accountRepository.findAll().size();
    }
}
