package com.ktnet.testRes1.oauth2;

import com.ktnet.testRes1.sso.SSORspData;
import com.ktnet.testRes1.sso.SSOService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class OAuth2Service {

    /**
     * 1. isSSO가 세션에서 true인지 여부 체크, SSOPRINCIPAL이 request 내부에 존재하는지 여부 체크
     * @param request
     * @return boolean
     */
    public boolean isLoginSSO(HttpServletRequest request) {
        String isSSO = (String) request.getSession().getAttribute("isSSO");
        if("true".equals(isSSO)){
            SSOService ssoService = new SSOService();

            // SSOPRINCIPAL 이 request 내부에 존재하는가?
            SSORspData ssoRspData = ssoService.ssoGetLoginData(request);

            if(ssoRspData != null){
                int code = ssoRspData.getCode();
                if(code == 0){
                    return true;    // 있다.
                }
            }
        }
        return false;   // 없다.
    }
}
