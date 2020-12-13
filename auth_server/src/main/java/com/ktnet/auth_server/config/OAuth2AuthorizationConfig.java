package com.ktnet.auth_server.config;


import com.ktnet.auth_server.user.CustomUserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
@EnableAuthorizationServer
public class OAuth2AuthorizationConfig extends AuthorizationServerConfigurerAdapter {

    private final DataSource dataSource;
    private final PasswordEncoder passwordEncoder;
    private final CustomUserDetailService userDetailService;

    /**
     * 클라이언트 정보를 저장하는 메서드입니다.
     * .withClient("testClientId")
     * .secret("testSecret")
     * .redirectUris("http://localhost:8081/oauth2/callback")
     * .authorizedGrantTypes("authorization_code")
     * .scopes("read", "write")
     * .accessTokenValiditySeconds(30000);
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.jdbc(dataSource).passwordEncoder(passwordEncoder);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()")
                .allowFormAuthenticationForClients();
    }

    /**
     * 토큰 정보를 DB를 통해 관리한다.
     * @return
     */
//    @Override
//    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
//        endpoints.tokenStore(new JdbcTokenStore(dataSource));
//    }

    /**
     * 인증, 토큰 엔드포인트, 토큰 서비스를 정의할 수 있다.
     * tokenStore, userDetailsService 등 객체를 빈으로 가져와 설정에 등록해주고 있는데, 이 빈들은 뒤에 나올
     * SecurityConfig 에서 등록해주게 될 것이다.
     * 토큰 발급 방식을 JWT 토큰 방식으로 변경한다. 이렇게 하면 토큰 저장하는 DB Table은 필요가 없다.
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        super.configure(endpoints);
        endpoints.accessTokenConverter(jwtAccessTokenConverter()).userDetailsService(userDetailService);
    }

    /**
     * jwt converter를 등록
     */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey("KAS");
        return converter;
    }
}
