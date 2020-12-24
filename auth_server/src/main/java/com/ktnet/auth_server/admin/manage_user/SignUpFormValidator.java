package com.ktnet.auth_server.admin.manage_user;

import com.ktnet.auth_server.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class SignUpFormValidator implements Validator {

    private final UserRepository accountRepository;

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.isAssignableFrom(SignUpForm.class);   // 어떤 타입의 인스턴스를 검증을 할 것인가?
    }

    // 뭘 검사할 것인가?
    @Override
    public void validate(Object o, Errors errors) {
        SignUpForm signUpForm = (SignUpForm)o;
        if(accountRepository.existsByUid(signUpForm.getEmail())){
            errors.rejectValue("email","invalid email",new Object[]{signUpForm.getEmail()},"이미 사용중인 이메일입니다.");
        }
        if(accountRepository.existsByName(signUpForm.getNickname())){
            errors.rejectValue("nickname","invalid nickname",new Object[]{signUpForm.getNickname()},"이미 사용중인 닉네임입니다.");
        }
    }
}
