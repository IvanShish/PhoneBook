package com.mydomain.phonebook.entities;

import java.util.Objects;

public class User {

    private Long userId;
    private String name;
    private String surname;
    private String phone;

    public User(Long userId, String name, String surname, String phone) {
        this.userId = userId;
        this.name = name;
        this.surname = surname;
        this.phone = phone;
    }

    public User(User user) {
        this.userId = user.getUserId();
        this.name = user.getName();
        this.surname = user.getSurname();
        this.phone = user.getPhone();
    }

    public User() {
        super();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof User))
            return false;
        User employee = (User) o;
        return Objects.equals(this.name, employee.name) && Objects.equals(this.surname, employee.surname)
                && Objects.equals(this.phone, employee.phone);
    }
}
