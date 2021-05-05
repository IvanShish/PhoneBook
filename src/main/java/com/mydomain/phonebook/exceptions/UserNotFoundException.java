package com.mydomain.phonebook.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="No such user")
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long userId) {
        super("Couldn't find user with id = " + userId);
        System.out.println("Couldn't find user with id = " + userId);
    }

    public UserNotFoundException(String s) {
        super("Couldn't find users with name/phone = " + s);
        System.out.println("Couldn't find users with name/phone = " + s);
    }
}
