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
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
@Log4j2
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

        log.debug("POST createUser UserModel saved: {}", user.toString());
        log.info("User saved successfully userId {}", user.getId());

        return userRepository.save(user);
    }

    @Override
    public Page<EntityModel<UserModel>> getAllUsers(Pageable pageable) {

        Page<UserModel> page = userRepository.findAll(pageable);
        Page<EntityModel<UserModel>> pageEntity = Page.empty();
        if (!page.isEmpty()) {
            pageEntity =  page.map(user -> EntityModel.of(user,
                    linkTo(methodOn(UserController.class).findById(user.getId())).withSelfRel(),
                    linkTo(methodOn(UserController.class).getAllUsers(pageable)).withRel("users")));
        }
        return pageEntity;
    }

    @Override
    public EntityModel<UserModel> findById(String id) {
        UserModel userModel = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND + id));

        return EntityModel.of(userModel, linkTo(methodOn(UserController.class).findById(id)).withSelfRel(),
                linkTo(methodOn(UserController.class).getAllUsers(null)).withRel("users"));
    }

    @Override
    public UserModel updateUser(String id, UserDto userModel) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setFullname(userModel.getFullname());
                    user.setCpf(userModel.getCpf());
                    user.setPhoneNumber(userModel.getPhoneNumber());
                    user.setUpdatedAt(LocalDateTime.now());

                    log.debug("PUT updateUser UserModel updated: {}", user.toString());
                    log.info("User updated successfully userId {}", user.getId());

                    return userRepository.save(user);
                })
                .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND + id));
    }

    @Override
    public void deleteUser(String id) {
        userRepository.deleteById(id);
        log.debug("DELETE deleteUser userId deleted: {}", id);
        log.info("User deleted successfully userId {}", id);
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
