package com.ktnet.auth_server.admin.manage_federation;

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
    @Column(name = "federation_id")
    private Long id;

    private String kid;

    private String gid;

    private String uid;

    public void updateKid(String kid) {
        this.kid = kid;
    }
}
