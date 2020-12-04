package com.ktnet.auth_server.user;

import com.ktnet.auth_server.account.Account;
import com.ktnet.auth_server.account.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final AccountRepository accountRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findByUid(String uid) {
        return userRepository.findByUid(uid);
    }

//    @Transactional
//    public void createDummy() {
//        User user1 = User.builder().age(21).name("user1").role(Role.USER).userStatus(UserStatus.ACTIVE).build();
//        userRepository.save(user1);
//        User user2 = User.builder().age(42).name("user2").role(Role.USER).userStatus(UserStatus.ACTIVE).build();
//        userRepository.save(user2);
//    }
//
//    @Transactional
//    public void changeUserRoleAdmin(Long id){
//        User byEmail = userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
//        byEmail.setRole(Role.ADMIN);
//    }

}
