package com.ktnet.auth_server.admin.manage_client;

import com.ktnet.auth_server.client.Client;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;


/**
 insert into oauth_client_details(client_id, resource_ids,client_secret,scope,authorized_grant_types,web_server_redirect_uri,authorities,access_token_validity,refresh_token_validity,additional_information,autoapprove)
 values('testClientId1',null,'{bcrypt}$2a$10$bT7H1f7kjkFpdB6M2.T8SuxFt1qJ1mZ6Nc2qUD2NJkRjqrAYdcVx6','read,write','authorization_code,refresh_token','http://localhost:8080/oauth2/callback','ROLE_USER',36000,50000,null,null);
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Client_SignUpForm {


    @NotBlank
    private String client_id;
    private String resource_ids;
    @NotBlank
    private String client_secret;
    private String scope;
    private String authorized_grant_types;
    @NotBlank
    private String web_server_redirect_uri;
    private String authorities;
    private String access_token_validity;
    private String refresh_token_validity;
    private String additional_information;
    private String autoapprove;

    public static Client toEntity(Client_SignUpForm dto) {
        return Client.builder().client_id(dto.getClient_id())
                .resource_ids(dto.getResource_ids())
                .client_secret(dto.getClient_secret())
                .scope("read,write")
                .authorized_grant_types("authorization_code,refresh_token")
                .web_server_redirect_uri(dto.getWeb_server_redirect_uri())
                .authorities("ROLE_USER")
                .access_token_validity("36000")
                .refresh_token_validity("50000")
                .additional_information(dto.getAdditional_information())
                .autoapprove(dto.getAutoapprove())
                .build();
    }
}
