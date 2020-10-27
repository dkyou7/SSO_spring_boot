package com.ktnet.auth_server.site;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
public class Site {

    @Id
    private String SITE_NM;
    private String SITE_URL;
    private String MEM_CONN_URL;
    private String PWD_CONN_URL;
    private String DELEGATE_YN;
    private String USE_YN;
    private String GID;
    private String SEQ;
    private String PARENT_SITE_URL;
}
