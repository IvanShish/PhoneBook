package com.mydomain.phonebook.service;

import com.mydomain.phonebook.dao.PhoneBookDAO;
import com.mydomain.phonebook.entities.User;
import com.mydomain.phonebook.exceptions.IllegalUserIDException;
import com.mydomain.phonebook.exceptions.UserAlreadyExistsException;
import com.mydomain.phonebook.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class PhoneBookServiceImpl implements PhoneBookService {
    private PhoneBookDAO phoneBookDAO;

    @Autowired
    public void setPhoneBookDAO(PhoneBookDAO phoneBookDAO) {
        this.phoneBookDAO = phoneBookDAO;
    }

    @Override
    public Collection<User> allUsers() {
        return phoneBookDAO.allUsers();
    }

    @Override
    public void add(User user) throws UserAlreadyExistsException, IllegalUserIDException {
        phoneBookDAO.add(user);
    }

    @Override
    public void delete(Long userId) {
        phoneBookDAO.delete(userId);
    }

    @Override
    public void edit(User newUser, User userToEdit) {
        phoneBookDAO.edit(newUser, userToEdit);
    }

    @Override
    public User getById(Long id) throws UserNotFoundException {
        return phoneBookDAO.getById(id);
    }
}
