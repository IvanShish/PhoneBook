package com.mydomain.phonebook.exceptions;

public class PhoneNotMatchException extends RuntimeException {
    public PhoneNotMatchException(String phone) {
        super("Phone number must start with \"8\" and contain 10 digits after it. Wrong number: " + phone);
        System.out.println("Phone number must start with \"8\" and contain 10 digits after it. Wrong number: " + phone);
    }
}
