package com.ktnet.auth_server.UTH_Member;

import lombok.Getter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
public class Member {
//    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
//    @SequenceGenerator(sequenceName = "member_seq",allocationSize = 1,name = "seq")
//    @Column(name = "member_id")
//    private Long id;

    @Id
    private String USERID;
    private String PRTNUM;
    private String PSWD;
    private String JUMINNO;
    private String NM;
    private String DPRT;
    private String ACD;
    private String TEL;
    private String EXTNTNUM;
    private String FAXNUM;
    private String MBLNUM;
    private String EMAIL;
    private String SMSRCVYN;
    private String EMAILRCVYN;
    private String EMAILOPNYN;
    private String ADMINYN;
    private String JNSTS;
    private String JNPRPSLDT;
    private String JNPRCSDT;
    private String CMPNTYP;
    private String CTWID;
    private String ASPLINEID;
    private String WEBSRVYN;
    private String FAXSRVCUSEYN;
    private String ALMEMAILYN;
    private String APLCTSIGN;
    private String USERDVSN;
    private String LOGINURL;
    private String ADMINID;
    private Date RGSTTM;
    private Date MDFTM;
    private String HOMEPAGE;
    private String STOPWRNDT;
    private String ASPCMPNID;
    private String ASPAPLCTSIGN;
    private String TESTYN;
    private String ALMEMAIL1;
    private String ALMEMAIL2;
    private String ALMEMAIL3;
    private String PPSID;
    private String ALERTYN;
    private String G4BID;
    private String ENGNM;
    private String DFLTLANGUAGE;
    private String DIVISIONCODE;
    private String GPOSTMAIL;
    private String GPOSTRCVYN;
    private String PRCSTYPE;
    private String PRCSSQN;
    private String OPTAGREE1;
    private String IDENTIFYMTHD;
    private String ADMIP;
    private String ADMLOGINALARMYN;
    private String SNDRCVCDOPENYN;
    private String DORMANTYN;
    private Date AGREE1;
    private Date AGREE2;
    private Date AGREE3;
    private String OLDJNRQSTEMLSNTDT;
    private Date RFDCUSTOMSAGREE;
    private Date RFDCUSTOMSAGREE2;
//    private String ALMEAIL4;
//    private String ALMEAIL5;
    private String PERSONAL_MAIL_YN;

    public String getNM() {
        return NM;
    }
}
