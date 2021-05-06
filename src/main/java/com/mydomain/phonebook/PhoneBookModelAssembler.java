package com.mydomain.phonebook;

import com.mydomain.phonebook.controller.PhoneBookController;
import com.mydomain.phonebook.entities.PhoneBookMember;
import com.mydomain.phonebook.entities.User;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PhoneBookModelAssembler {
    public EntityModel<User> userToModel(User user) {
        return EntityModel.of(user,
                linkTo(methodOn(PhoneBookController.class).getUser(user.getUserId())).withSelfRel(),
                linkTo(methodOn(PhoneBookController.class).allUsers()).withRel("users"));
    }

    public EntityModel<PhoneBookMember> phoneBookMemberToModel(PhoneBookMember phoneBookMember) {
        return EntityModel.of(phoneBookMember,
                linkTo(methodOn(PhoneBookController.class).getUser(phoneBookMember.getUserId())).withSelfRel(),
                linkTo(methodOn(PhoneBookController.class).allUsers()).withRel("users"));
    }

    public EntityModel<User> phoneBookToModel(User user, Long id) {
        return EntityModel.of(user,
                linkTo(methodOn(PhoneBookController.class).getUserFromPhoneBook(id, user.getUserId())).withSelfRel(),
                linkTo(methodOn(PhoneBookController.class).getUserPhoneBook(id)).withRel("phonebook"));
    }
}
