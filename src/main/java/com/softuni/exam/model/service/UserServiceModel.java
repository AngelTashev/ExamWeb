package com.softuni.exam.model.service;

import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

public class UserServiceModel {

    private String username;
    private String password;
    private String email;

    public UserServiceModel() {
    }

    @Length(min = 3, max = 20, message = "Username must be between 3 and 20 characters!")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Length(min = 3, max = 20, message = "Password must be between 3 and 20 characters!")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @NotNull(message = "Email cannot be empty!")
    @Email
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
