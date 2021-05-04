package com.mydomain.phonebook.exceptions;

public class IllegalUserIDException extends RuntimeException {
    public IllegalUserIDException(Long userId) {
        super("You cannot add a user with a negative id = " + userId);
        System.out.println("You cannot add a user with a negative id = " + userId);
    }
}
