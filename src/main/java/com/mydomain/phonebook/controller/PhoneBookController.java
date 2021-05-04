package com.mydomain.phonebook.controller;

import com.mydomain.phonebook.entities.User;
import com.mydomain.phonebook.exceptions.IllegalUserIDException;
import com.mydomain.phonebook.exceptions.UserAlreadyExistsException;
import com.mydomain.phonebook.exceptions.UserNotFoundException;
import com.mydomain.phonebook.service.PhoneBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/phonebook/users")
public class PhoneBookController {
    private PhoneBookService phoneBookService;

    @Autowired
    public void setPhoneBookService(PhoneBookService phoneBookService) {
        this.phoneBookService = phoneBookService;
    }

    @GetMapping
    public Collection<User> allUsers() {
        return phoneBookService.allUsers();
    }

    @PostMapping
    public void addUser(@RequestBody User newUser) throws UserAlreadyExistsException, IllegalUserIDException {
//        curl -X POST -H "Content-Type:application/json" -H "Cache-Control:no-cache" localhost:8080/phonebook/users -d "{\"name\":\"add\", \"surname\":\"test\", \"phone\":\"881232819345\"}"
        phoneBookService.add(newUser);
        System.out.println("Added " + newUser);
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable("id") Long id) throws UserNotFoundException {
        return phoneBookService.getById(id);
    }

    @DeleteMapping("/{id}")
    public void delUser(@PathVariable("id") Long id) throws UserNotFoundException {
//        curl -X DELETE localhost:8080/phonebook/users/3
        User user = phoneBookService.getById(id);
        phoneBookService.delete(id);
        System.out.println("Deleted " + user);
    }

    @PutMapping("/{id}")
    public void editUser(@RequestBody User newUser, @PathVariable Long id) throws UserNotFoundException {
//        curl -X PUT localhost:8080/phonebook/users/1 -H "Content-type:application/json" -d "{\"name\":\"Samwise\", \"surname\":\"Gamgee\"}"
        System.out.println("Before: " + phoneBookService.getById(id));
        phoneBookService.edit(newUser, phoneBookService.getById(id));
        System.out.println("After: " + phoneBookService.getById(id));
    }
}
