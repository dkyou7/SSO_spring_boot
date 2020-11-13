package com.ktnet.auth_server.auth;

import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequiredArgsConstructor
public class LoginControllerApi {

    private final SessionInfoService sessionInfoService;
    private static final String signingKey = "signingKey";
    private static final String sessionKey = "testSessionKey";

    @GetMapping("/getInfo/{code}")
    public String getInfo(HttpServletRequest request, @PathVariable("code") String code){
        SessionInfo byCode = sessionInfoService.findByCode(code);
        if(byCode!=null){
            HttpSession session = request.getSession();
            session.setAttribute(code,byCode.getInfo());
            return byCode.getInfo();
        }
        return "hello";
    }

    @GetMapping("/sessionCheck")
    public String sessionCheck(HttpServletRequest request){
        HttpSession session = request.getSession();
        System.out.println("session.getId() = " + session.getId());
        String attribute = (String)session.getAttribute(sessionKey);
        if(attribute==null){
            return null;
        }else{
            String s = testDecodeJWT(attribute);
            JSONObject jObject = new JSONObject(s);
            String username = jObject.getString("sub");
            String token = JwtUtil.generateToken(signingKey, username);
            session.setAttribute(signingKey,token);
            return token;
        }
    }
    public String testDecodeJWT(String code){
        String jwtToken = code;
        System.out.println("------------ Decode JWT ------------");
        String[] split_string = jwtToken.split("\\.");
        String base64EncodedHeader = split_string[0];
        String base64EncodedBody = split_string[1];
        String base64EncodedSignature = split_string[2];

        System.out.println("~~~~~~~~~ JWT Header ~~~~~~~");
        Base64 base64Url = new Base64(true);
        String header = new String(base64Url.decode(base64EncodedHeader));
        System.out.println("JWT Header : " + header);


        System.out.println("~~~~~~~~~ JWT Body ~~~~~~~");
        String body = new String(base64Url.decode(base64EncodedBody));
        System.out.println("JWT Body : "+body);

        return body;
    }
}
