package com.ktnet.auth_server.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
        User user1 = new User("test1","123");
        User user2 = new User("test2","123");
        User user3 = new User("test3","123");
        User user4 = new User("hellokoding","hellokoding");
        User user5 = new User("hellosso","hellosso");
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
        userRepository.save(user4);
        userRepository.save(user5);
    }
}
