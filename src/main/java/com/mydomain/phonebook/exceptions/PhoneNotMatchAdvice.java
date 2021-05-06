package com.mydomain.phonebook.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class PhoneNotMatchAdvice {
    @ResponseBody
    @ExceptionHandler(PhoneNotMatchException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String phoneNotMatchHandler(PhoneNotMatchException ex) {
        return ex.getMessage();
    }
}
