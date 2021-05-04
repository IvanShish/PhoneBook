package com.mydomain.phonebook.dao;

import com.mydomain.phonebook.entities.User;
import com.mydomain.phonebook.exceptions.IllegalUserIDException;
import com.mydomain.phonebook.exceptions.UserAlreadyExistsException;
import com.mydomain.phonebook.exceptions.UserNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class PhoneBookDAOImpl implements PhoneBookDAO {
    private static final AtomicLong AUTO_ID = new AtomicLong(0);
    private static Map<Long, User> users = new HashMap<>();

    public PhoneBookDAOImpl() {
        User user1 = new User(AUTO_ID.getAndIncrement(), "Ivan", "Shishkin", "88005553535");
        users.put(user1.getUserId(), user1);

        User user2 = new User(AUTO_ID.getAndIncrement(), "Anthony", "McBride", "88124955665");
        users.put(user2.getUserId(), user2);

        User user3 = new User(AUTO_ID.getAndIncrement(), "Michael", "Norman", "88122764767");
        users.put(user3.getUserId(), user3);

        User user4 = new User(AUTO_ID.getAndIncrement(), "Lionel", "Hall", "88122764767");
        users.put(user4.getUserId(), user4);

        User user5 = new User(AUTO_ID.getAndIncrement(), "Ruth", "Willis", "88127507567");
        users.put(user5.getUserId(), user5);
    }

    @Override
    public Collection<User> allUsers() {
        return new ArrayList<>(users.values());
    }

    @Override
    public void add(User user) {
        if (users.containsValue(user)) {
            throw new UserAlreadyExistsException(user);
        }
        else if (user.getUserId() == null) {
            user.setUserId(AUTO_ID.getAndIncrement());
        }
        else if (user.getUserId() < 0) {
            throw new IllegalUserIDException(user.getUserId());
        }
        else if (users.containsKey(user.getUserId())) {
            throw new UserAlreadyExistsException(user.getUserId());
        }
        users.put(user.getUserId(), user);
    }

    @Override
    public void delete(Long userId) {
        users.remove(userId);
    }

    @Override
    public void edit(User newUser, User userToEdit) {
        users.put(userToEdit.getUserId(), new User(
                userToEdit.getUserId(),
                newUser.getName() != null ? newUser.getName() : userToEdit.getName(),
                newUser.getSurname() != null ? newUser.getSurname() : userToEdit.getSurname(),
                newUser.getPhone() != null ? newUser.getPhone() : userToEdit.getPhone()
        ));
    }

    @Override
    public User getById(Long id) {
        return users.computeIfAbsent(id, v -> {
            throw new UserNotFoundException(v);
        });
    }
}
