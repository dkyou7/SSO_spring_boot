package com.ktnet.testRes1.user;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class UserController {

    private final UserRepository userRepository;

    @GetMapping("/users")
    public List<User> findAllUser(){
        return userRepository.findAll();
    }

    @GetMapping("/user/{msrl}")
    public User findUserByMsrl(@PathVariable("msrl")Long msrl){
        return userRepository.findByMsrl(msrl);
    }
}
