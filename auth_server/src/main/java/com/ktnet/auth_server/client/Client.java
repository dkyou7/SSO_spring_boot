package com.ktnet.auth_server.client;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "oauth_client_details")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Client {

    @Id
    private String client_id;                   // testClientId1
    private String resource_ids;                 // null
    private String client_secret;               // {bcrypt}$2a$10$bT7H1f7kjkFpdB6M2.T8SuxFt1qJ1mZ6Nc2qUD2NJkRjqrAYdcVx6
    private String scope;                       // read,write
    private String authorized_grant_types;      // authorization_code,refresh_token
    private String web_server_redirect_uri;     // http://localhost:8080/oauth2/callback
    private String authorities;                 // ROLE_USER
    private String access_token_validity;       // 36000
    private String refresh_token_validity;      // 50000
    private String additional_information;      // null
    private String autoapprove;                 // null

    public void updateSecret(String encodedSecret) {
        this.client_secret = encodedSecret;
    }
}
