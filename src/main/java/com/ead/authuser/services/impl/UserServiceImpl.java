package com.ead.authuser.services.impl;

import com.ead.authuser.dto.UserDto;
import com.ead.authuser.exceptions.ResourceNotFoundException;
import com.ead.authuser.models.UserModel;
import com.ead.authuser.repositories.UserRepository;
import com.ead.authuser.services.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserModel createUser(UserDto userModel) {
        return userRepository.save(new UserModel(userModel));
    }

    public List<UserModel> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserModel findById(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }
}
