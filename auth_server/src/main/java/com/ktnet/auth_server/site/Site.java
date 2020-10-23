package com.ktnet.auth_server.site;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@Builder
public class Site {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "site_id")
    private Long id;

    private String clientId;
    private String redirectUrl;

    @Column(name = "gid")
    private String siteNickname;
    private String siteName;
    private String siteUrl;
//    private String memConnUrl;
//    private String pwdConnUrl;
//    private String deleteGateYN;
//    private String useYN;
}
