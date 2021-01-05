package com.ktnet.auth_server.federation;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter @ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Federation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "kid")
    private Long id;

    private String gid;

    private String uid;
}
