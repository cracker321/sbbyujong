package com.example.ssbyujong;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

//'@Controller'를 붙이면, 이제 아래 '클래스 MainController'는 스프링부트의 컨트롤러가 됨
@Controller
public class MainController {

    @RequestMapping("/") //'root URL' 만들어줌.
                            //사용자가 'www.localhost8080'만 입력했을 때, 사용자에게 어느 화면을 보여줄지에 대한 로직
    public String root(){

        return "ridirect:/question/list"; //사용자가 'www.localhost8080'만 입력했을 때, 사용자에게 저 링크로 리다이렉트 해줌.
    }

    @RequestMapping("/sbb") //'@RequestMapping'은 '요청된 URL과의 매핑'을 담당함
    @ResponseBody //'URL 요청'에 대한 응답으로 문자열을 리턴하라는 의미.
    public String index(){
        return "안녕하세요, sbb에 오신 것을 환영합니다";
    }

}
