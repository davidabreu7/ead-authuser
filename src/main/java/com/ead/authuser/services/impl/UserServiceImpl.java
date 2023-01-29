package com.ead.authuser.services.impl;

import com.ead.authuser.dto.InstructorDto;
import com.ead.authuser.dto.UserDto;
import com.ead.authuser.dto.UserEventDto;
import com.ead.authuser.enums.ActionType;
import com.ead.authuser.enums.UserStatus;
import com.ead.authuser.enums.UserType;
import com.ead.authuser.exceptions.FieldException;
import com.ead.authuser.exceptions.ResourceNotFoundException;
import com.ead.authuser.models.UserModel;
import com.ead.authuser.publishers.UserEventPublisher;
import com.ead.authuser.repositories.UserRepository;
import com.ead.authuser.services.UserService;
import com.querydsl.core.types.Predicate;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
@Log4j2
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;


    private final UserEventPublisher userEventPublisher;


    private static final String  USER_NOT_FOUND = "User not found";

    public UserServiceImpl(UserRepository userRepository, UserEventPublisher userEventPublisher) {
        this.userRepository = userRepository;
        this.userEventPublisher = userEventPublisher;
    }

    @Transactional
    public UserModel createUser(UserDto userModel) {

        userValidation(userModel);
        UserModel user = new UserModel(userModel);

        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        user.setUserType(UserType.STUDENT);
        user.setUserStatus(UserStatus.ACTIVE);

        UserModel savedUser = userRepository.save(user);
        userEventPublisher.publishUserEvent(new UserEventDto(savedUser), ActionType.CREATE);

        log.debug("POST createUser UserModel saved: {}", user.toString());
        log.info("User saved successfully userId {}", user.getId());

        return savedUser;
    }

    private void userValidation(UserDto userModel) {
        if (userRepository.existsByCpf(userModel.getCpf())) {
            log.error("POST createUser CPF already registered: {}", userModel.getCpf());
            throw new FieldException("CPF already registered");
        }

        if (userRepository.existsByUsername(userModel.getUsername())) {
            log.error("POST createUser username already registered: {}", userModel.getUsername());
            throw new FieldException("username already registered");
        }
    }

    @Override
    public Page<UserModel> getAllUsers(Predicate predicate, Pageable pageable) {

        return userRepository.findAll(predicate, pageable);
    }

    @Override
    public UserModel findById(String id) {

        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND + id));
    }

    @Override
    public UserModel updateUser(String id, UserDto userDto) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setFullname(userDto.getFullname());
                    user.setCpf(userDto.getCpf());
                    user.setPhoneNumber(userDto.getPhoneNumber());
                    user.setEmail(userDto.getEmail());
                    user.setImageUrl(userDto.getImageUrl());
                    user.setUpdatedAt(LocalDateTime.now());

                    userRepository.save(user);
                    userEventPublisher.publishUserEvent(new UserEventDto(user), ActionType.UPDATE);
                    log.debug("PUT updateUser UserModel updated: {}", user.toString());
                    log.info("User updated successfully userId {}", user.getId());
                    return user;

                })
                .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND + id));
    }

    @Override
    @Transactional
    public void deleteUser(String id) {
        var user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND + id));
        userRepository.delete(user);
        userEventPublisher.publishUserEvent(new UserEventDto(user), ActionType.DELETE);
        log.info("User deleted successfully: {}", user);
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
                    userEventPublisher.publishUserEvent(new UserEventDto(user), ActionType.UPDATE);
                    log.debug("PUT updateImage UserModel updated: {}", user.toString());
                    log.info("User updated successfully userId {}", user.getId());
                    return userRepository.save(user);
                })
                .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND + id));
    }

    @Override
    public UserModel registerInstructor(InstructorDto instructorDto) {
        UserModel user = userRepository.findById(instructorDto.getId().toString())
                .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND + instructorDto.getId()));

        user.setUserType(UserType.INSTRUCTOR);
        user.setUpdatedAt(LocalDateTime.now(ZoneId.of("UTC")));
        userEventPublisher.publishUserEvent(new UserEventDto(user), ActionType.UPDATE);
        log.debug("PUT registerInstructor UserModel updated: {}", user.toString());
        log.info("User updated successfully userId {}", user.getId());
        return userRepository.save(user);
    }

}
