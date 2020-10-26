package com.ktnet.auth_server.federation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ktnet.auth_server.account.Account;
import com.ktnet.auth_server.site.Site;
import com.ktnet.auth_server.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Builder
@NoArgsConstructor
@AllArgsConstructor
public class Federation {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "site_id")
    private Site site;

    private String gId;
    private String kId;
    private String uId;

    private LocalDateTime createDate;
    private LocalDateTime modifyDate;

    public static Federation createFederationInfo(Site site) {
        Federation federation = Federation.builder()
                .site(site)
                .gId(site.getSiteNickname())
                .createDate(LocalDateTime.now())
                .modifyDate(LocalDateTime.now())
                .build();
        return federation;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public void setKid(String email) {
        this.kId = this.gId+"_"+email;
    }

    public void setUid(String email) {
        this.uId = email;
    }
}
