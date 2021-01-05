package com.example.demo.account;

import com.example.demo.MockMvcTest;
import com.example.demo.sso.Article;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

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

    @Test
    @DisplayName("서비스에서 회원가입 처리 이후 auth_server에 페더레이션 처리 요청 보내기")
    public void test2(){
        WebClient webClient = WebClient.create("http://localhost:8081/api/v1");
        Account account = Account.builder()
                .username("dkyou7@naver.com")
                .password("123")
                .nickname("유동관")
                .gid("KCMNTTEST")
                .build();
        Mono<Account> mono = Mono.just(account); // Mono.just로 이미 존재하는 article 객체를 감쌈

        String result = webClient.post()
                .uri("/user/federation")
                .contentType(MediaType.APPLICATION_JSON)
                .body(mono, Account.class)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        System.out.println("result: "+ result);
    }
}