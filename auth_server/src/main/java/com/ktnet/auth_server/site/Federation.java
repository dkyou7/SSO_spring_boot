package com.ktnet.auth_server.site;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "KA_FEDERATION")
public class Federation {

    private String VID;
    private String GID;
    @Id
    private String USERID;
    private String USERFLAG;
    private String ADDDATE;
    private String MODDATE;
    private String DELEGATE_YN;
    private String MIGRATION_YN;

    public String getVID() {
        return VID;
    }

    public void setVID(String VID) {
        this.VID = VID;
    }

    public String getGID() {
        return GID;
    }

    public void setGID(String GID) {
        this.GID = GID;
    }

    public String getUSERID() {
        return USERID;
    }

    public void setUSERID(String USERID) {
        this.USERID = USERID;
    }

    public String getUSERFLAG() {
        return USERFLAG;
    }

    public void setUSERFLAG(String USERFLAG) {
        this.USERFLAG = USERFLAG;
    }

    public String getADDDATE() {
        return ADDDATE;
    }

    public void setADDDATE(String ADDDATE) {
        this.ADDDATE = ADDDATE;
    }

    public String getMODDATE() {
        return MODDATE;
    }

    public void setMODDATE(String MODDATE) {
        this.MODDATE = MODDATE;
    }

    public String getDELEGATE_YN() {
        return DELEGATE_YN;
    }

    public void setDELEGATE_YN(String DELEGATE_YN) {
        this.DELEGATE_YN = DELEGATE_YN;
    }

    public String getMIGRATION_YN() {
        return MIGRATION_YN;
    }

    public void setMIGRATION_YN(String MIGRATION_YN) {
        this.MIGRATION_YN = MIGRATION_YN;
    }
}
