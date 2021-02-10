package com.example.demo.account;

import com.example.demo.MockMvcTest;
import com.example.demo.sso.Article;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@MockMvcTest
class AccountControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    WebTestClient webTestClient;

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

    @Test
    @DisplayName("첫 접근 시, 인덱스 페이지를 호출해야합니다.")
    public void test3() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }
    @Test
    @DisplayName("회원가입 하면, federation, loginCheck 테이블에 데이터가 삽입됩니다.")
    public void test4() throws Exception{

        String cur_time = String.valueOf(LocalDateTime.now());

        mockMvc.perform(post("/sign-up")
                .param("nickname","유동관")
                .param("username",cur_time + "@naver.com")
                .param("password","123")
                .with(csrf()))  // CSRF 토큰 넣어주자. 안전한 요청으로 만들어주자.
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));

        webTestClient.post().uri("http://localhost:8081/api/v1/login")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(cur_time + "@naver.com")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class).isEqualTo("Y");
    }

    @Test
    @DisplayName("로그인 진행 시, 세션 KID 값을 호출 했을 때 반환이 됩니다.")
    public void test5() throws Exception{
        String cur_time = String.valueOf(LocalDateTime.now());

        mockMvc.perform(post("/sign-up")
                .param("nickname","유동관")
                .param("username",cur_time + "@naver.com")
                .param("password","123")
                .with(csrf()))  // CSRF 토큰 넣어주자. 안전한 요청으로 만들어주자.
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));
        webTestClient.post().uri("http://localhost:8081/api/v1/login")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(cur_time + "@naver.com")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class).isEqualTo("Y");

        webTestClient.post().uri("http://localhost:8081/api/v1/tokenCheck")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class).isEqualTo("KID");
    }
}