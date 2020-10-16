package com.ktnet.testClient1.resource;

import org.apache.tomcat.util.codec.binary.Base64;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class ResourceController {
    private static final String jwtTokenCookieName = "JWT-TOKEN";
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/")
    public String home(HttpServletRequest request,Model model) {
        logger.info("[testRes1 서버] 1. 처음 접속하는 화면입니다. ================= 로그인 하기를 클릭하시면 됩니다.");
        if(CookieUtil.getValue(request,jwtTokenCookieName)== null){
            return "index";
        }
        String username = CookieUtil.getValue(request, jwtTokenCookieName);
        String s = testDecodeJWT(username);
        JSONObject jObject = new JSONObject(s);
        username = jObject.getString("sub");
        model.addAttribute("username", username);
//        return "success_page";
        return "index";
    }

    @GetMapping("/success/{username}")
    public String success(@PathVariable("username")String username, Model model) {
        logger.info("[testRes1 서버] success =================" + username);
        model.addAttribute("username",username);
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpServletResponse httpServletResponse) {
        CookieUtil.clear(httpServletResponse, jwtTokenCookieName);
        return "redirect:/";
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
