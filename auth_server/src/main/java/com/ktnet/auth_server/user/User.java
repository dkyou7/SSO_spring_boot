package com.ktnet.auth_server.user;

import com.ktnet.auth_server.account.Account;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter @ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String name;
    private int age;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private UserStatus userStatus; // 계정 상태[ACTIVE(활성),DORMANT(휴면),INACTIVE(비활성)]

    @OneToMany(mappedBy = "user")
    private final List<Account> accounts = new ArrayList<>();
}
