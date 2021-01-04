package com.example.demo.account;

import com.example.demo.MockMvcTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@MockMvcTest
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired private AccountRepository accountRepository;

    @DisplayName("회원가입 처리 - 입력값 정상")
    @Test
    public void signUpSubmit_with_correct_input() throws Exception {
        mockMvc.perform(post("/sign-up")
                .param("nickname","유동관")
                .param("username","test@naver.com")
                .param("password","123")
                .param("vid","admin@naver.com")
                .with(csrf()))  // CSRF 토큰 넣어주자. 안전한 요청으로 만들어주자.
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));

        Account byEmail = accountRepository.findByVid("admin@naver.com");
        assertNotNull(byEmail);
        assertNotEquals(byEmail.getPassword(),"123");
    }
}