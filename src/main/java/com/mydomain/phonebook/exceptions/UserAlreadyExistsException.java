package com.mydomain.phonebook.exceptions;

import com.mydomain.phonebook.entities.User;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="User already exists")
public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(User user) {
        super(user + " already exists");
        System.out.println(user + " already exists");
    }

    public UserAlreadyExistsException(Long userId) {
        super("User with id = " + userId + " already exists");
        System.out.println("User with id = " + userId + " already exists");
    }
}
