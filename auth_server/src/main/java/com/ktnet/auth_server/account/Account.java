package com.ktnet.auth_server.account;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ktnet.auth_server.federation.Federation;
import com.ktnet.auth_server.site.Site;
import com.ktnet.auth_server.user.User;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Builder @ToString
@NoArgsConstructor
@AllArgsConstructor
public class Account implements Serializable {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long id;

    private String email;
    private String password;
    private String clientId;
    private String redirectUrl;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "account",cascade = CascadeType.ALL)
    private final List<Federation> federationList = new ArrayList<>();

    public Account(String redirectUrl, String clientId){
        this.redirectUrl=redirectUrl;
        this.clientId=clientId;
    }

    public void mergeUser(Account info){
        this.clientId = info.getClientId();
        this.redirectUrl = info.getRedirectUrl();
    }

    public void setUser(User user){
        this.user = user;
        user.getAccounts().add(this);
    }

    public void addFederation(Federation federation){
        federationList.add(federation);
        federation.setAccount(this);
    }

    public static Account updateAccount(Account account, Federation federation){
        federation.setUid(account.getEmail());
        federation.setKid(account.getEmail());
        account.addFederation(federation);
        return account;
    }
}
