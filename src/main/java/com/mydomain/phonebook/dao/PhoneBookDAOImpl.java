package com.mydomain.phonebook.dao;

import com.mydomain.phonebook.entities.PhoneBookMember;
import com.mydomain.phonebook.entities.User;
import com.mydomain.phonebook.exceptions.IllegalUserIDException;
import com.mydomain.phonebook.exceptions.UserAlreadyExistsException;
import com.mydomain.phonebook.exceptions.UserNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

@Repository
public class PhoneBookDAOImpl implements PhoneBookDAO {
    private static final AtomicLong AUTO_ID = new AtomicLong(0);
    private static Map<Long, PhoneBookMember> phoneBookMembers = new HashMap<>();

    public PhoneBookDAOImpl() {
//        User user1 = new User(AUTO_ID.getAndIncrement(), "Ivan", "Shishkin", "88005553535");
//        users.put(user1.getUserId(), user1);
//
//        User user2 = new User(AUTO_ID.getAndIncrement(), "Anthony", "McBride", "88124955665");
//        users.put(user2.getUserId(), user2);
//
//        User user3 = new User(AUTO_ID.getAndIncrement(), "Michael", "Norman", "88122764767");
//        users.put(user3.getUserId(), user3);
//
//        User user4 = new User(AUTO_ID.getAndIncrement(), "Lionel", "Hall", "88122764767");
//        users.put(user4.getUserId(), user4);
//
//        User user5 = new User(AUTO_ID.getAndIncrement(), "Ruth", "Willis", "88127507567");
//        users.put(user5.getUserId(), user5);

        PhoneBookMember user1 = new PhoneBookMember(AUTO_ID.getAndIncrement(), "Ivan", "Shishkin", "88005553535");
        List<User> phoneBookUser1 = new ArrayList<>();
        phoneBookUser1.add(new User(user1.incrementId(), "Anthony", "McBride", "88124955665"));
        phoneBookUser1.add(new User(user1.incrementId(), "Michael", "Norman", "88122764767"));
        user1.setPhoneBook(phoneBookUser1);
        phoneBookMembers.put(user1.getUserId(), user1);

        PhoneBookMember user2 = new PhoneBookMember(AUTO_ID.getAndIncrement(), "Anthony", "McBride", "88124955665");
        List<User> phoneBookUser2 = new ArrayList<>();
        phoneBookUser2.add(new User(user2.incrementId(), "Ivan", "Shishkin", "88005553535"));
        user2.setPhoneBook(phoneBookUser2);
        phoneBookMembers.put(user2.getUserId(), user2);

        PhoneBookMember user3 = new PhoneBookMember(AUTO_ID.getAndIncrement(), "Michael", "Norman", "88122764767");
        phoneBookMembers.put(user3.getUserId(), user3);
    }

    @Override
    public Collection<PhoneBookMember> allPhoneBookMembers() {
        return new ArrayList<>(phoneBookMembers.values());
    }

    @Override
    public void add(User user) {
        PhoneBookMember newUser = new PhoneBookMember(user);
        if (phoneBookMembers.containsValue(newUser)) {
            throw new UserAlreadyExistsException(user);
        }
        else if (newUser.getUserId() == null) {
            newUser.setUserId(AUTO_ID.getAndIncrement());
        }
        else if (newUser.getUserId() < 0) {
            throw new IllegalUserIDException(newUser.getUserId());
        }
        else if (phoneBookMembers.containsKey(newUser.getUserId())) {
            throw new UserAlreadyExistsException(newUser.getUserId());
        }
        phoneBookMembers.put(newUser.getUserId(), newUser);
    }

    @Override
    public void delete(Long id) {
        phoneBookMembers.remove(id);
    }

    @Override
    public void edit(User newUser, PhoneBookMember userToEdit) {
        phoneBookMembers.put(userToEdit.getUserId(), new PhoneBookMember(
                userToEdit.getUserId(),
                newUser.getName() != null ? newUser.getName() : userToEdit.getName(),
                newUser.getSurname() != null ? newUser.getSurname() : userToEdit.getSurname(),
                newUser.getPhone() != null ? newUser.getPhone() : userToEdit.getPhone()
        ));
    }

    @Override
    public PhoneBookMember getById(Long id) {
        return phoneBookMembers.computeIfAbsent(id, v -> {
            throw new UserNotFoundException(v);
        });
    }

    @Override
    public User getUserByIdFromPhoneBook(Long id, Long userId) {
        PhoneBookMember phoneBookMember = phoneBookMembers.computeIfAbsent(id, v -> {
            throw new UserNotFoundException(v);
        });

        for (User user : phoneBookMember.getPhoneBook()) {
            if (user.getUserId().equals(userId)) return user;
        }
        throw new UserNotFoundException(userId);
    }

    @Override
    public void addInPhoneBook(User user, Long id) {
        PhoneBookMember userById = phoneBookMembers.computeIfAbsent(id, v -> {
            throw new UserNotFoundException(v);
        });
        List<User> userPhoneBook = userById.getPhoneBook();
        if (userPhoneBook.contains(user)) {
            throw new UserAlreadyExistsException(user);
        } else if (user.getUserId() == null) {
            user.setUserId(userById.incrementId());
        } else if (user.getUserId() < 0) {
            throw new IllegalUserIDException(user.getUserId());
        }
        else {
            for (User u : userPhoneBook) {
                if (u.getUserId().equals(user.getUserId())) throw new UserAlreadyExistsException(userById.getUserId());
            }
        }

        userById.addNewUserInPhoneBook(user);
    }

    @Override
    public void delInPhoneBook(Long id, Long userId) {
        PhoneBookMember userById = phoneBookMembers.computeIfAbsent(id, v -> {
            throw new UserNotFoundException(v);
        });
        List<User> userPhoneBook = userById.getPhoneBook();

        for (User user : userPhoneBook) {
            if (user.getUserId().equals(userId)) {
                userById.delUserInPhoneBook(user);
                return;
            }
        }

        throw new UserNotFoundException(userId);
    }

    @Override
    public void editInPhoneBook(User user, Long id, Long userId) {
        PhoneBookMember userById = phoneBookMembers.computeIfAbsent(id, v -> {
            throw new UserNotFoundException(v);
        });
        List<User> userPhoneBook = userById.getPhoneBook();

        for (int i = 0; i < userPhoneBook.size(); i++) {
            User u = userPhoneBook.get(i);
            if (u.getUserId().equals(userId)) {
                userById.editUserInPhoneBook(new User(
                        u.getUserId(),
                        user.getName() != null ? user.getName() : u.getName(),
                        user.getSurname() != null ? user.getSurname() : u.getSurname(),
                        user.getPhone() != null ? user.getPhone() : u.getPhone()
                ), i);
                return;
            }
        }

        throw new UserNotFoundException(userId);
    }

    @Override
    public List<PhoneBookMember> getUsersByName(String name) {
        List<PhoneBookMember> users = new ArrayList<>();

        for (Map.Entry<Long, PhoneBookMember> entry : phoneBookMembers.entrySet()) {
            PhoneBookMember phoneBookMember = entry.getValue();
            if (phoneBookMember.getName().toLowerCase().contains(name)) {
                users.add(phoneBookMember);
            }
        }

        if (users.size() == 0) throw new UserNotFoundException(name);
        return users;
    }

    @Override
    public List<PhoneBookMember> getUsersByPhone(String phone) {
        List<PhoneBookMember> users = new ArrayList<>();

        for (Map.Entry<Long, PhoneBookMember> entry : phoneBookMembers.entrySet()) {
            PhoneBookMember phoneBookMember = entry.getValue();
            if (phoneBookMember.getPhone().contains(phone)) {
                users.add(phoneBookMember);
            }
        }

        if (users.size() == 0) throw new UserNotFoundException(phone);
        return users;
    }

    @Override
    public List<User> getUsersInPhoneBookByName(Long id, String name) {
        PhoneBookMember userById = phoneBookMembers.computeIfAbsent(id, v -> {
            throw new UserNotFoundException(v);
        });
        List<User> users = new ArrayList<>();

        for (User user : userById.getPhoneBook()) {
            if (user.getName().toLowerCase().contains(name)) {
                users.add(user);
            }
        }

        if (users.size() == 0) throw new UserNotFoundException(name);
        return users;
    }

    @Override
    public List<User> getUsersInPhoneBookByPhone(Long id, String phone){
        PhoneBookMember userById = phoneBookMembers.computeIfAbsent(id, v -> {
            throw new UserNotFoundException(v);
        });
        List<User> users = new ArrayList<>();

        for (User user : userById.getPhoneBook()) {
            if (user.getPhone().contains(phone)) {
                users.add(user);
            }
        }

        if (users.size() == 0) throw new UserNotFoundException(phone);
        return users;
    }
}
