package com.mydomain.phonebook.controller;

import com.mydomain.phonebook.PhoneBookModelAssembler;
import com.mydomain.phonebook.entities.PhoneBookMember;
import com.mydomain.phonebook.entities.User;
import com.mydomain.phonebook.exceptions.*;
import com.mydomain.phonebook.service.PhoneBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/data/users")
public class PhoneBookController {
    private PhoneBookService phoneBookService;
    private final PhoneBookModelAssembler assembler;

    public PhoneBookController(PhoneBookModelAssembler assembler) {
        this.assembler = assembler;
    }

    @Autowired
    public void setPhoneBookService(PhoneBookService phoneBookService) {
        this.phoneBookService = phoneBookService;
    }

    @GetMapping
    public CollectionModel<EntityModel<PhoneBookMember>> allUsers() {
        List<EntityModel<PhoneBookMember>> phoneBookMembers = phoneBookService.allPhoneBookMembers().stream()
                .map(assembler::phoneBookMemberToModel)
                .collect(Collectors.toList());
        return CollectionModel.of(phoneBookMembers,
                linkTo(methodOn(PhoneBookController.class).allUsers()).withSelfRel());
    }

    @PostMapping
    public void addUser(@RequestBody User newUser) throws UserAlreadyExistsException, IllegalUserIDException, PhoneNotMatchException, NameNotMatchException {
//        curl -X POST -H "Content-Type:application/json" -H "Cache-Control:no-cache" localhost:8080/data/users -d "{\"name\":\"add\", \"surname\":\"test\", \"phone\":\"881232819345\"}"
        phoneBookService.add(newUser);
    }

    @GetMapping("/{id}")
    public EntityModel<User> getUser(@PathVariable("id") Long id) throws UserNotFoundException {
        User user = phoneBookService.getById(id);
        return assembler.userToModel(user);
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
    public EntityModel<User> getUserFromPhoneBook(@PathVariable("id") Long id, @PathVariable("userId") Long userId) throws UserNotFoundException {
        User user = phoneBookService.getUserByIdFromPhoneBook(id, userId);
        return assembler.phoneBookToModel(user, id);
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
    public CollectionModel<EntityModel<User>> getUserPhoneBook(@PathVariable("id") Long id) throws UserNotFoundException {
        PhoneBookMember user = phoneBookService.getById(id);
        List<EntityModel<User>> userPhoneBook = user.getPhoneBook().stream()
                .map(u -> assembler.phoneBookToModel(u, id)).collect(Collectors.toList());

        return CollectionModel.of(userPhoneBook,
                linkTo(methodOn(PhoneBookController.class).getUserPhoneBook(id)).withSelfRel());
    }

    @GetMapping("/find/name/{name}")
    public CollectionModel<EntityModel<PhoneBookMember>> getUsersByName(@PathVariable("name") String name) throws UserNotFoundException {
//        http://localhost:8080/data/users/find/name/an
        List<EntityModel<PhoneBookMember>> phoneBookMembers = phoneBookService.getUsersByName(name).stream()
                .map(assembler::phoneBookMemberToModel)
                .collect(Collectors.toList());
        return CollectionModel.of(phoneBookMembers,
                linkTo(methodOn(PhoneBookController.class).getUsersByPhone(name)).withSelfRel());
    }

    @GetMapping("/find/phone/{phone}")
    public CollectionModel<EntityModel<PhoneBookMember>> getUsersByPhone(@PathVariable("phone") String phone) throws UserNotFoundException {
//        http://localhost:8080/data/users/find/phone/
        List<EntityModel<PhoneBookMember>> phoneBookMembers = phoneBookService.getUsersByPhone(phone).stream()
                .map(assembler::phoneBookMemberToModel)
                .collect(Collectors.toList());
        return CollectionModel.of(phoneBookMembers,
                linkTo(methodOn(PhoneBookController.class).getUsersByPhone(phone)).withSelfRel());
    }

    @GetMapping("/{id}/find/name/{name}")
    public CollectionModel<EntityModel<User>> getUsersInPhoneBookByName(@PathVariable("id") Long id, @PathVariable("name") String name) throws UserNotFoundException {
        List<EntityModel<User>> userPhoneBook = phoneBookService.getUsersInPhoneBookByName(id, name).stream()
                .map(u -> assembler.phoneBookToModel(u, id)).collect(Collectors.toList());

        return CollectionModel.of(userPhoneBook,
                linkTo(methodOn(PhoneBookController.class).getUsersInPhoneBookByName(id, name)).withSelfRel());
    }

    @GetMapping("/{id}/find/phone/{phone}")
    public CollectionModel<EntityModel<User>> getUsersInPhoneBookByPhone(@PathVariable("id") Long id, @PathVariable("phone") String phone) throws UserNotFoundException {
        List<EntityModel<User>> userPhoneBook = phoneBookService.getUsersInPhoneBookByPhone(id, phone).stream()
                .map(u -> assembler.phoneBookToModel(u, id)).collect(Collectors.toList());

        return CollectionModel.of(userPhoneBook,
                linkTo(methodOn(PhoneBookController.class).getUsersInPhoneBookByPhone(id, phone)).withSelfRel());
    }
}
