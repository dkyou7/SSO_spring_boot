package com.ktnet.auth_server.user;

import com.ktnet.auth_server.admin.manage_user.SignUpForm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Collections;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findByUid(String uid) {
        return userRepository.findByUid(uid);
    }

    public void save(User user){
        userRepository.save(user);
    }

    public User processNewAccount(SignUpForm signUpForm) {
        User newUser = buildAccount(signUpForm);  // 계정 만들기
        // newAccount.generateEmailCheckToken();   // 토큰 생성하기
        // sendSignUpConfirmEmail(newAccount); // 가입 확인 이메일 보내기
        return newUser;
    }

    private User buildAccount(@Valid SignUpForm signUpForm) {
        User user = User.builder().name(signUpForm.getNickname())
                .uid(signUpForm.getEmail())
                .password(passwordEncoder.encode(signUpForm.getPassword()))
                .build();
        User newUser = userRepository.save(user);
        return newUser;
    }

    public User processNewAccountAdmin(SignUpForm signUpForm) {
        User newUser = buildAccountAdmin(signUpForm);  // 계정 만들기
        return newUser;
    }

    private User buildAccountAdmin(SignUpForm signUpForm) {
        User user = User.builder().name(signUpForm.getNickname())
                .uid(signUpForm.getEmail())
                .password(passwordEncoder.encode(signUpForm.getPassword()))
                .roles(Collections.singletonList("ROLE_ADMIN"))
                .build();
        User newUser = userRepository.save(user);
        return newUser;
    }
}
