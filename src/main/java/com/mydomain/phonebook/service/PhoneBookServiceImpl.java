package com.mydomain.phonebook.service;

import com.mydomain.phonebook.dao.PhoneBookDAO;
import com.mydomain.phonebook.entities.PhoneBookMember;
import com.mydomain.phonebook.entities.User;
import com.mydomain.phonebook.exceptions.IllegalUserIDException;
import com.mydomain.phonebook.exceptions.UserAlreadyExistsException;
import com.mydomain.phonebook.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class PhoneBookServiceImpl implements PhoneBookService {
    private PhoneBookDAO phoneBookDAO;

    @Autowired
    public void setPhoneBookDAO(PhoneBookDAO phoneBookDAO) {
        this.phoneBookDAO = phoneBookDAO;
    }

    @Override
    public Collection<PhoneBookMember> allPhoneBookMembers() {
        return phoneBookDAO.allPhoneBookMembers();
    }

    @Override
    public void add(User user) throws UserAlreadyExistsException, IllegalUserIDException {
        phoneBookDAO.add(user);
    }

    @Override
    public void delete(Long id) {
        phoneBookDAO.delete(id);
    }

    @Override
    public void edit(User newUser, PhoneBookMember userToEdit) {
        phoneBookDAO.edit(newUser, userToEdit);
    }

    @Override
    public PhoneBookMember getById(Long id) throws UserNotFoundException {
        return phoneBookDAO.getById(id);
    }

    @Override
    public User getUserByIdFromPhoneBook(Long id, Long userId) throws UserNotFoundException {
        return phoneBookDAO.getUserByIdFromPhoneBook(id, userId);
    }

    @Override
    public void addInPhoneBook(User user, Long id) throws UserAlreadyExistsException, IllegalUserIDException {
        phoneBookDAO.addInPhoneBook(user, id);
    }

    @Override
    public void delInPhoneBook(Long id, Long userId) {
        phoneBookDAO.delInPhoneBook(id, userId);
    }

    @Override
    public void editInPhoneBook(User user, Long id, Long userId) {
        phoneBookDAO.editInPhoneBook(user, id, userId);
    }

    @Override
    public List<PhoneBookMember> getUsersByName(String name) {
       return phoneBookDAO.getUsersByName(name);
    }

    @Override
    public List<PhoneBookMember> getUsersByPhone(String phone) {
        return phoneBookDAO.getUsersByPhone(phone);
    }

    @Override
    public List<User> getUsersInPhoneBookByName(Long id, String name) {
        return phoneBookDAO.getUsersInPhoneBookByName(id, name);
    }

    @Override
    public List<User> getUsersInPhoneBookByPhone(Long id, String phone){
        return phoneBookDAO.getUsersInPhoneBookByPhone(id, phone);
    }
}
