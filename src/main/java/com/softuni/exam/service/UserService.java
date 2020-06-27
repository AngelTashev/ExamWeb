package com.softuni.exam.service;

import com.softuni.exam.model.service.UserServiceModel;

public interface UserService {

    UserServiceModel findByUsername(String username);

    void registerUser(UserServiceModel userServiceModel);
}
