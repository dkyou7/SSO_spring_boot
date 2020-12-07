package com.ktnet.testClient1.user;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Profile("local")
@Component
@RequiredArgsConstructor
public class InitUser {

    private final InitUserService initUserService;
    @PostConstruct
    public void init(){
        initUserService.init();   // 스프링 라이프 사이클로 인해 분리해주어야 한다.
        // @postConstruct 와 @Transaction
    }
    @Component
    static class InitUserService{
        @PersistenceContext
        private EntityManager em;

        @Transactional
        public void init(){
            for(int i = 0 ; i< 100 ; i++){
                em.persist(new User("user"+i+"@email.com",i,"user"+i));
            }
        }
    }
}
