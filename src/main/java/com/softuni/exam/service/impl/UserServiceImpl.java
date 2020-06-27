package com.softuni.exam.service.impl;

import com.softuni.exam.model.entity.User;
import com.softuni.exam.model.service.UserServiceModel;
import com.softuni.exam.repository.UserRepository;
import com.softuni.exam.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final ModelMapper modelMapper;

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(ModelMapper modelMapper, UserRepository userRepository) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }

    @Override
    public UserServiceModel findByUsername(String username) {

        User user = this.userRepository.findByUsername(username).orElse(null);

        if (user == null) {
            return null;
        }
        return this.modelMapper.map(user, UserServiceModel.class);
    }

    @Override
    public void registerUser(UserServiceModel userServiceModel) {
        this.userRepository.saveAndFlush(this.modelMapper.map(userServiceModel, User.class));
    }
}
