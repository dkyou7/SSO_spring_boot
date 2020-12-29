package com.ktnet.testRes1.sso;

import lombok.Data;

@Data
public class SSORspData {

    private int code;
    private String message;
    private SSOPrincipal principal;

    public SSORspData(){
        this.code = 0;
        this.message = "";
    }

    public void setCodeMessage(int code, String message){
        this.code = code;
        this.message = message;
    }
}
