package com.ktnet.auth_server.sso;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter @ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_id")
    private Long id;

    private String contents;
}
