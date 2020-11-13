package com.ktnet.auth_server.auth;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class SessionInfo {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "session_info_id")
    private Long id;

    private String code;

    private String info;
}
