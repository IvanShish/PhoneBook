package com.mydomain.phonebook.dao;

import com.mydomain.phonebook.entities.PhoneBookMember;
import com.mydomain.phonebook.entities.User;

import java.util.Collection;
import java.util.List;

public interface PhoneBookDAO {
    Collection<PhoneBookMember> allPhoneBookMembers();
    void add(User user);
    void delete(Long id);
    void edit(User newUser, PhoneBookMember userToEdit);
    PhoneBookMember getById(Long id);
    User getUserByIdFromPhoneBook(Long id, Long userId);
    void addInPhoneBook(User user, Long id);
    void delInPhoneBook(Long id, Long userId);
    void editInPhoneBook(User user, Long id, Long userId);
    List<PhoneBookMember> getUsersByName(String name);
    List<PhoneBookMember> getUsersByPhone(String phone);
    List<User> getUsersInPhoneBookByName(Long id, String name);
    List<User> getUsersInPhoneBookByPhone(Long id, String phone);
}
