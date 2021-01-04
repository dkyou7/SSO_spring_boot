package com.ktnet.auth_server.gid;

import com.ktnet.auth_server.user.User;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@EqualsAndHashCode(of = "id")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Gid {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gid_id")
    private Long id;

    @OneToMany(mappedBy = "gid")
    private List<User> userList;
}
