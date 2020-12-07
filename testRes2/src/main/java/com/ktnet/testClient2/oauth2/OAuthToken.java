package com.ktnet.testClient2.oauth2;

import lombok.Data;

@Data
public class OAuthToken {
    private String access_token;
    private String token_type;
    private String refresh_token;
    private long expires_in;
    private String scope;
}