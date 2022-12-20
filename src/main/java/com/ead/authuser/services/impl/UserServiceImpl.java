package com.ead.authuser.services.impl;

import com.ead.authuser.controllers.UserController;
import com.ead.authuser.dto.UserDto;
import com.ead.authuser.enums.UserStatus;
import com.ead.authuser.enums.UserType;
import com.ead.authuser.exceptions.FieldException;
import com.ead.authuser.exceptions.ResourceNotFoundException;
import com.ead.authuser.models.UserModel;
import com.ead.authuser.repositories.UserRepository;
import com.ead.authuser.services.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private static final String  USER_NOT_FOUND = "User not found";

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserModel createUser(UserDto userModel) {
        UserModel user = new UserModel(userModel);

        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        user.setUserType(UserType.STUDENT);
        user.setUserStatus(UserStatus.ACTIVE);

        return userRepository.save(user);
    }

    @Override
    public Page<UserModel> getAllUsers(Pageable pageable) {

        Page<UserModel> page = userRepository.findAll(pageable);

        if (!page.isEmpty()) {
            page.toList().forEach(user -> {
                user.add(linkTo(methodOn(UserController.class).findById(user.getId())).withSelfRel());
                user.add(linkTo(methodOn(UserController.class).getAllUsers(pageable)).withRel("users"));
            });
        }
        return page;
    }

    @Override
    public UserModel findById(String id) {
        UserModel userModel = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND + id));

        userModel.add(linkTo(methodOn(UserController.class).findById(id)).withSelfRel());
        userModel.add(linkTo(methodOn(UserController.class).getAllUsers(null)).withRel("users"));

        return userModel;
    }

    @Override
    public UserModel updateUser(String id, UserDto userModel) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setFullname(userModel.getFullname());
                    user.setCpf(userModel.getCpf());
                    user.setPhoneNumber(userModel.getPhoneNumber());
                    user.setUpdatedAt(LocalDateTime.now());
                    return userRepository.save(user);
                })
                .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND + id));
    }

    @Override
    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }

    @Override
    public String updatePassword(String id, UserDto userModel) {
        return userRepository.findById(id)
                .map(user -> {
                    if (user.getPassword().equals(userModel.getOldPassword())) {
                        user.setPassword(userModel.getPassword());
                        user.setUpdatedAt(LocalDateTime.now());
                        userRepository.save(user);
                        return "Password updated successfully";
                    } else {
                        throw new FieldException("Error: Mismatched old password ");
                    }
                })
                .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND + id));
    }

    @Override
    public UserModel updateImage(String id, UserDto userModel) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setImageUrl(userModel.getImageUrl());
                    user.setUpdatedAt(LocalDateTime.now());
                    return userRepository.save(user);
                })
                .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND + id));
    }

}
