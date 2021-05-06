package com.mydomain.phonebook.exceptions;

public class NameNotMatchException extends RuntimeException {
    public NameNotMatchException(String name) {
        super("The name must consist of letters (the symbol \"-\" is possible). Invalid name: " + name);
        System.out.println("The name must consist of letters (the symbol \"-\" is possible). Invalid name: " + name);
    }
}
