package com.ktnet.testRes1.login;

import com.ktnet.testRes1.user.User;
import com.ktnet.testRes1.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class LoginService {

    private final static String COOKIE_USER_ID = "RES1_COOKIE_USER_ID";
    private final UserRepository userRepository;

    /**
     * 쿠키에 아이디 삽입
     * @param request
     * @param response
     * @param loginForm
     */
    public void insert_id_to_cookie(HttpServletRequest request, HttpServletResponse response, LoginForm loginForm) {
        Cookie[] cookies = request.getCookies();
        String idSave = loginForm.getIdSave();
        if("Y".equals(idSave)){
            Cookie cookie = new Cookie(COOKIE_USER_ID,loginForm.getUsername());
            cookie.setMaxAge(60*60*24*30);
            response.addCookie(cookie);
        }else{
            if(cookies!=null && cookies.length>1){
                Cookie cookie = new Cookie(COOKIE_USER_ID,loginForm.getUsername());
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
        }
    }

    /**
     * N : 사용자 정보 없음(?), 아이디/패스워드 로그인 사용자
     * NA : UTH 미승인 사용자
     * Y : 인증서 로그인 사용자
     * @param loginForm
     * @return
     */
    public String isCertMember(LoginForm loginForm) {
        return "N";
    }

    public User login(LoginForm loginForm) {
        User user = userRepository.findByUid(loginForm.getUsername()).orElseThrow(EntityNotFoundException::new);
        if(user.getPassword().matches(loginForm.getPassword())){
            return user;
        }
        return null;
    }
}
