package com.mydomain.phonebook.service;

import com.mydomain.phonebook.entities.User;

import java.util.Collection;

public interface PhoneBookService {
    Collection<User> allUsers();
    void add(User user);
    void delete(Long userId);
    void edit(User newUser, User userToEdit);
    User getById(Long id);
}
