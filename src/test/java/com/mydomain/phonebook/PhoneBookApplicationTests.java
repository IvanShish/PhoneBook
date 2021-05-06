package com.mydomain.phonebook;

import com.mydomain.phonebook.entities.PhoneBookMember;
import com.mydomain.phonebook.entities.User;
import com.mydomain.phonebook.service.PhoneBookService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@SpringBootTest
class PhoneBookApplicationTests {
    @Autowired
    private PhoneBookService phoneBookService;

    @Test
    void allPhoneBookMembersTest() {
        Collection<PhoneBookMember> phoneBookMembers = phoneBookService.allPhoneBookMembers();
        Assertions.assertEquals(phoneBookMembers.size(), 3);
    }

    @Test
    void firstPhoneBookUserTest() {
        List<PhoneBookMember> phoneBookMembers = new ArrayList<>(phoneBookService.allPhoneBookMembers());
        PhoneBookMember phoneBookMember = phoneBookMembers.get(0);
        User user1 = phoneBookMember.getPhoneBook().get(0);
        Assertions.assertEquals(user1.getName(), "Anthony");
        Assertions.assertEquals(user1.getSurname(), "McBride");
        Assertions.assertEquals(user1.getPhone(), "88124955665");
    }

    @Test
    void addUserTest() {
        phoneBookService.add(new User(4L, "add", "test", "88123281935"));
        List<PhoneBookMember> phoneBookMembers = new ArrayList<>(phoneBookService.allPhoneBookMembers());
        Assertions.assertEquals(phoneBookMembers.size(), 4);
        phoneBookService.delete(4L);
    }

    @Test
    void delUserTest() {
        phoneBookService.delete(1L);
        List<PhoneBookMember> phoneBookMembers = new ArrayList<>(phoneBookService.allPhoneBookMembers());
        Assertions.assertEquals(phoneBookMembers.size(), 2);
        phoneBookService.add(new User(null, "Anthony", "McBride", "88124955665"));
        phoneBookService.addInPhoneBook(new User(null, "Ivan", "Shishkin", "88005553535"), 2L);
    }

    @Test
    void editUserTest() {
        PhoneBookMember was = phoneBookService.getById(2L);
        phoneBookService.edit(new User(null, "Samwise", "Gamgee", null), was);
        List<PhoneBookMember> phoneBookMembers = new ArrayList<>(phoneBookService.allPhoneBookMembers());
        PhoneBookMember phoneBookMember = phoneBookMembers.get(2);
        Assertions.assertEquals(phoneBookMember.getName(), "Samwise");
        Assertions.assertEquals(phoneBookMember.getSurname(), "Gamgee");
        Assertions.assertEquals(phoneBookMember.getPhone(), was.getPhone());
    }

    @Test
    void addInPhoneBookTest() {
        List<User> was = new ArrayList<>(phoneBookService.getById(0L).getPhoneBook());
        phoneBookService.addInPhoneBook(new User(4L, "add", "test", "88123281935"), 0L);
        List<User> now = phoneBookService.getById(0L).getPhoneBook();
        Assertions.assertEquals(now.size(), was.size() + 1);
        phoneBookService.delInPhoneBook(0L, 4L);
    }

    @Test
    void delInPhoneBookTest() {
        List<User> was = new ArrayList<>(phoneBookService.getById(0L).getPhoneBook());
        User user = phoneBookService.getUserByIdFromPhoneBook(0L, 1L);
        phoneBookService.delInPhoneBook(0L, 1L);
        List<User> now = phoneBookService.getById(0L).getPhoneBook();
        Assertions.assertEquals(now.size(), was.size() - 1);
        phoneBookService.addInPhoneBook(user, 0L);
    }

    @Test
    void editUserInPhoneBookTest() {
        User was = phoneBookService.getUserByIdFromPhoneBook(0L, 1L);
        phoneBookService.editInPhoneBook(new User(null, "Samwise", "Gamgee", null), 0L, 1L);
        User now = phoneBookService.getUserByIdFromPhoneBook(0L, 1L);
        Assertions.assertEquals(now.getName(), "Samwise");
        Assertions.assertEquals(now.getSurname(), "Gamgee");
        Assertions.assertEquals(now.getPhone(), was.getPhone());
        phoneBookService.editInPhoneBook(new User(null, was.getName(), was.getSurname(), null), 0L, 1L);
    }

}
