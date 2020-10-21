package com.ktnet.auth_server.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter @Setter @ToString
@NoArgsConstructor
public class User implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "uid")
    private Long id;

    private String email;
    private String password;

    private String redirectUrl;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
