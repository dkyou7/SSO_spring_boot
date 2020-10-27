package com.ktnet.auth_server.site;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "KA_FEDERATION_SITE")
public class Site {

    @Id
    private String SITE_NM;
    private String SITE_URL;
    private String MEM_CONN_URL;
    private String PWD_CONN_URL;
    private String DELEGATE_YN;
    private String USE_YN;
    private String gID;
    private String SEQ;
    private String PARENT_SITE_URL;

    public String getSITE_NM() {
        return SITE_NM;
    }

    public void setSITE_NM(String SITE_NM) {
        this.SITE_NM = SITE_NM;
    }

    public String getSITE_URL() {
        return SITE_URL;
    }

    public void setSITE_URL(String SITE_URL) {
        this.SITE_URL = SITE_URL;
    }

    public String getMEM_CONN_URL() {
        return MEM_CONN_URL;
    }

    public void setMEM_CONN_URL(String MEM_CONN_URL) {
        this.MEM_CONN_URL = MEM_CONN_URL;
    }

    public String getPWD_CONN_URL() {
        return PWD_CONN_URL;
    }

    public void setPWD_CONN_URL(String PWD_CONN_URL) {
        this.PWD_CONN_URL = PWD_CONN_URL;
    }

    public String getDELEGATE_YN() {
        return DELEGATE_YN;
    }

    public void setDELEGATE_YN(String DELEGATE_YN) {
        this.DELEGATE_YN = DELEGATE_YN;
    }

    public String getUSE_YN() {
        return USE_YN;
    }

    public void setUSE_YN(String USE_YN) {
        this.USE_YN = USE_YN;
    }

    public String getgID() {
        return gID;
    }

    public void setGID(String GID) {
        this.gID = GID;
    }

    public String getSEQ() {
        return SEQ;
    }

    public void setSEQ(String SEQ) {
        this.SEQ = SEQ;
    }

    public String getPARENT_SITE_URL() {
        return PARENT_SITE_URL;
    }

    public void setPARENT_SITE_URL(String PARENT_SITE_URL) {
        this.PARENT_SITE_URL = PARENT_SITE_URL;
    }

    @Override
    public String toString() {
        return "Site{" +
                "SITE_NM='" + SITE_NM + '\'' +
                ", SITE_URL='" + SITE_URL + '\'' +
                ", MEM_CONN_URL='" + MEM_CONN_URL + '\'' +
                ", PWD_CONN_URL='" + PWD_CONN_URL + '\'' +
                ", DELEGATE_YN='" + DELEGATE_YN + '\'' +
                ", USE_YN='" + USE_YN + '\'' +
                ", GID='" + gID + '\'' +
                ", SEQ='" + SEQ + '\'' +
                ", PARENT_SITE_URL='" + PARENT_SITE_URL + '\'' +
                '}';
    }
}
