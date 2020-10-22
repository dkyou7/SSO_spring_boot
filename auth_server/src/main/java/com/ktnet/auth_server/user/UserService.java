package com.ktnet.auth_server.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Transactional
    public void createDummy() {
        User user1 = new User("test1@naver.com","123");
        User user2 = new User("test2@naver.com","123");
        User user3 = new User("test3@naver.com","123");
        User user4 = new User("hellokoding@naver.com","hellokoding");
        User user5 = new User("hellosso@naver.com","hellosso");
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
        userRepository.save(user4);
        userRepository.save(user5);
    }

    @Transactional
    public void changeUserRoleAdmin(Long id){
        User byEmail = userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        byEmail.setRole(Role.ADMIN);
    }
}
