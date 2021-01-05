package com.ktnet.testRes1.account;

import lombok.Data;

@Data
public class SignUpForm {
    private String username;
    private String password;
    private String nickname;
    private boolean remember;
}
