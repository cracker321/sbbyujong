package com.example.ssbyujong.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


//'내가 만든 메소드 DataNotFoundException'이 '인터페이스 RuntimeException'을 상속하도록 만들었음.
//이제, 'DataNotFoundException'이 발생하면, '@ResponseStatus'에 의해, '404 에러(HttpStatus.Not_FOUND)'가 나타날 것임
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "entity not found")
public class DataNotFoundException extends RuntimeException{

    private static final long serialVersionUID = 1L;
    public DataNotFoundException(String message){
        super(message);
    }
}
