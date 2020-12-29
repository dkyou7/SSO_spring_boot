package com.ktnet.testRes1.login;

import lombok.Data;

@Data
public class LoginForm {

    private String username;
    private String password;
    private String usertype;    // 미승인, 승인, UTH통합인지 아닌지
    private String idSave;      // 로그인 시, 아이디 저장할지 말지 누르는 버튼
    private String authtype;    //
    private boolean dormant;    // 휴면계정인가?
    private String regid;   // 이건 뭘까
}
