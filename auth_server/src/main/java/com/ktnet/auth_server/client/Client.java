package com.ktnet.auth_server.client;

import com.ktnet.auth_server.user.User;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter @Setter @ToString
@Builder
public class Client implements Serializable {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String resourceId;

    private String redirectUrl;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
