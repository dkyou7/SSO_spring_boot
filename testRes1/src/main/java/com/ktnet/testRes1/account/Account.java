package com.ktnet.testRes1.account;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Account {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long id;

    private String username;
    private String password;
    private String nickname;
    private boolean remember;

    private String gid;

    private String vid; // auth_server에 로그인 여부 확인해줄 아이디

    public void updateVid(String s) {
        this.vid = s;
    }
}
