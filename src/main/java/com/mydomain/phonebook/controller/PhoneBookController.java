package com.mydomain.phonebook.controller;

import com.mydomain.phonebook.entities.PhoneBookMember;
import com.mydomain.phonebook.entities.User;
import com.mydomain.phonebook.exceptions.IllegalUserIDException;
import com.mydomain.phonebook.exceptions.UserAlreadyExistsException;
import com.mydomain.phonebook.exceptions.UserNotFoundException;
import com.mydomain.phonebook.service.PhoneBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/data/users")
public class PhoneBookController {
    private PhoneBookService phoneBookService;

    @Autowired
    public void setPhoneBookService(PhoneBookService phoneBookService) {
        this.phoneBookService = phoneBookService;
    }

    @GetMapping
    public Collection<PhoneBookMember> allUsers() {
        return phoneBookService.allPhoneBookMembers();
    }

    @PostMapping
    public void addUser(@RequestBody User newUser) throws UserAlreadyExistsException, IllegalUserIDException {
//        curl -X POST -H "Content-Type:application/json" -H "Cache-Control:no-cache" localhost:8080/data/users -d "{\"name\":\"add\", \"surname\":\"test\", \"phone\":\"881232819345\"}"
        phoneBookService.add(newUser);
        System.out.println("Added " + phoneBookService.getById(newUser.getUserId()));
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable("id") Long id) throws UserNotFoundException {
        return phoneBookService.getById(id);
    }

    @DeleteMapping("/{id}")
    public void delUser(@PathVariable("id") Long id) throws UserNotFoundException {
//        curl -X DELETE localhost:8080/data/users/3
        PhoneBookMember user = phoneBookService.getById(id);
        phoneBookService.delete(id);
        System.out.println("Deleted " + user);
    }

    @PutMapping("/{id}")
    public void editUser(@RequestBody User newUser, @PathVariable Long id) throws UserNotFoundException {
//        curl -X PUT localhost:8080/data/users/1 -H "Content-type:application/json" -d "{\"name\":\"Samwise\", \"surname\":\"Gamgee\"}"
        System.out.println("Before: " + phoneBookService.getById(id));
        phoneBookService.edit(newUser, phoneBookService.getById(id));
        System.out.println("After: " + phoneBookService.getById(id));
    }

    @GetMapping("/{id}/{userId}")
    public User getUserFromPhoneBook(@PathVariable("id") Long id, @PathVariable("userId") Long userId) throws UserNotFoundException {
        return phoneBookService.getUserByIdFromPhoneBook(id, userId);
    }

    @PostMapping("/{id}")
    public void addUserInPhoneBook(@RequestBody User newUser, @PathVariable Long id) throws UserAlreadyExistsException, IllegalUserIDException {
//        curl -X POST -H "Content-Type:application/json" -H "Cache-Control:no-cache" localhost:8080/data/users/1 -d "{\"name\":\"add\", \"surname\":\"test\", \"phone\":\"881232819345\"}"
        phoneBookService.addInPhoneBook(newUser, id);
        System.out.println("Added to the phone book of the user with id = " + id + ": " + newUser);
    }

    @DeleteMapping("/{id}/{userId}")
    public void delUserInPhoneBook(@PathVariable("id") Long id, @PathVariable("userId") Long userId) throws UserNotFoundException {
//        curl -X DELETE localhost:8080/data/users/1/0
        User user = phoneBookService.getUserByIdFromPhoneBook(id, userId);
        phoneBookService.delInPhoneBook(id, userId);
        System.out.println("Deleted " + user);
    }

    @PutMapping("/{id}/{userId}")
    public void editUser(@RequestBody User newUser, @PathVariable("id") Long id, @PathVariable("userId") Long userId) throws UserNotFoundException {
//        curl -X PUT localhost:8080/data/users/1/0 -H "Content-type:application/json" -d "{\"name\":\"Samwise\", \"surname\":\"Gamgee\"}"
        System.out.println("Before: " + phoneBookService.getUserByIdFromPhoneBook(id, userId));
        phoneBookService.editInPhoneBook(newUser, id, userId);
        System.out.println("After: " + phoneBookService.getUserByIdFromPhoneBook(id, userId));
    }

    @GetMapping("/{id}/phonebook")
    public List<User> getUserPhoneBook(@PathVariable("id") Long id) throws UserNotFoundException {
        PhoneBookMember user = phoneBookService.getById(id);
        return user.getPhoneBook();
    }

    @GetMapping("/find/name/{name}")
    public List<PhoneBookMember> getUsersByName(@PathVariable("name") String name) throws UserNotFoundException {
//        http://localhost:8080/data/users/find/name/an
        return phoneBookService.getUsersByName(name);
    }

    @GetMapping("/find/phone/{phone}")
    public List<PhoneBookMember> getUsersByPhone(@PathVariable("phone") String phone) throws UserNotFoundException {
//        http://localhost:8080/data/users/find/phone/
        return phoneBookService.getUsersByPhone(phone);
    }

    @GetMapping("/{id}/find/name/{name}")
    public List<User> getUsersInPhoneBookByName(@PathVariable("id") Long id, @PathVariable("name") String name) throws UserNotFoundException {
        return phoneBookService.getUsersInPhoneBookByName(id, name);
    }

    @GetMapping("/{id}/find/phone/{phone}")
    public List<User> getUsersInPhoneBookByPhone(@PathVariable("id") Long id, @PathVariable("phone") String phone) throws UserNotFoundException {
        return phoneBookService.getUsersInPhoneBookByPhone(id, phone);
    }
}
