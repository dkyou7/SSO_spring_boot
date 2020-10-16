package com.ktnet.auth_server.UTH_Member;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member,String> {
//    Member findByPrtnum(String prtnum);
}
