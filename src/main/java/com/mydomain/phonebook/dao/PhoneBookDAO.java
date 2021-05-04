package com.mydomain.phonebook.dao;

import com.mydomain.phonebook.entities.User;

import java.util.Collection;

public interface PhoneBookDAO {
    Collection<User> allUsers();
    void add(User user);
    void delete(Long userId);
    void edit(User newUser, User userToEdit);
    User getById(Long id);
}
