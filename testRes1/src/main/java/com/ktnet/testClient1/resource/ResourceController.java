package com.ktnet.testClient1.resource;

import org.apache.tomcat.util.codec.binary.Base64;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;

@Controller
public class ResourceController {
    private static final String jwtTokenCookieName = "JWT-TOKEN";
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final String sessionKey = "testSessionKey";

    @GetMapping("/login")
    public String go_login(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        String test = (String) session.getAttribute("test");
        logger.info("[Res1 서버] 낚아 채가기 전에 할 행위가 있다. 토큰이 있는지 검색해본다.");
        if(test != null){
            String username = CookieUtil.getValue(request, jwtTokenCookieName);
            String s = testDecodeJWT(username);
            JSONObject jObject = new JSONObject(s);
            username = jObject.getString("sub");
            model.addAttribute("username", username);
            return "redirect:/";
        }
        logger.info("[Res1 서버] home() ================= 1. 이제 여기서 인증서버에서 낚아채갑니다. jwt filter 에 등록되어있기 때문에");
        return "redirect:http://localhost:8080/login?redirect_uri=http://localhost:8181&client_id=testClient1&response_type=code";
//        return "redirect:/go_auth";
    }
    @GetMapping("/test/{token}")
    public String protectedResourceTest(HttpServletRequest request, HttpServletResponse httpServletResponse,@PathVariable("token") String code) throws Exception{
        logger.info("[res1 서버] /go_auth/res1 =================" + code);

        HttpSession session = request.getSession();
        String test =((String) session.getAttribute(sessionKey));
        logger.info("------------------------_"+test);
        session.setAttribute(sessionKey,code);
//        RestTemplate restTemplate = new RestTemplate();
//        String jwt = restTemplate.getForObject("http://localhost:8080/sessionCheck", String.class,code);
//        if (jwt!=null){
//            session.setAttribute(sessionKey,jwt);
//        }
//        HttpSession session = request.getSession();
//        session.setAttribute(jwtTokenCookieName,jwt);
//        CookieUtil.create(httpServletResponse, jwtTokenCookieName, jwt, false, -1, "127.0.0.1");
//        CookieUtil.create(httpServletResponse, jwtTokenCookieName, jwt, false, -1, "localhost");
        return "redirect:/";
    }

    @GetMapping("/")
    public String home(HttpServletRequest request,Model model) {
        HttpSession session = request.getSession();
        System.out.println("session.getId() = " + session.getId());
        String test =(String) session.getAttribute(sessionKey);

        logger.info("====" + test);
        logger.info("[testRes1 서버] 1. 처음 접속하는 화면입니다. ================= 로그인 하기를 클릭하시면 됩니다.");
        if(test == null){
            /**
             * todo
             * 쿠키에 아무 정보도 없다면.. 다른 도메인에서 접속 정보가 존재할 수도 있으니까 인증서버 세션이 존재하는지 파악해 보자.
             * 근데 이건 다시 세션을 파야되는데 어떻게 해야하는건가? 잘 모르겠다.
             */
            return "index";
        }
        String s = testDecodeJWT(test);
        JSONObject jObject = new JSONObject(s);
        String username = jObject.getString("sub");
        model.addAttribute("username", username);
        return "index";
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
