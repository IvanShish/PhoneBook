package com.mydomain.phonebook.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class PhoneBookMember extends User {
    private AtomicLong autoId;
    private List<User> phoneBook;

    public PhoneBookMember(Long userId, String name, String surname, String phone) {
        super(userId, name, surname, phone);
        this.phoneBook = new ArrayList<>();
        this.autoId = new AtomicLong(0);
    }

    public PhoneBookMember(Long userId, String name, String surname, String phone, List<User> phoneBook, AtomicLong autoId) {
        super(userId, name, surname, phone);
        this.phoneBook = new ArrayList<>(phoneBook);
        this.autoId = new AtomicLong(autoId.get());
    }

    public PhoneBookMember(User user) {
        super(user);
        this.phoneBook = new ArrayList<>();
        this.autoId = new AtomicLong(0);
    }

    public List<User> getPhoneBook() {
        return phoneBook;
    }

    public void setPhoneBook(List<User> phoneBook) {
        this.phoneBook = new ArrayList<>(phoneBook);
    }

    public AtomicLong getAutoId() {
        return autoId;
    }

    public void setAutoId(AtomicLong autoId) {
        this.autoId = autoId;
    }

    public Long incrementId() {
        return autoId.getAndIncrement();
    }

    public void addNewUserInPhoneBook(User user) {
        phoneBook.add(user);
    }

    public void delUserInPhoneBook(User user) {
        phoneBook.remove(user);
    }

    public void editUserInPhoneBook(User user, int index) {
        phoneBook.set(index, user);
    }
}
