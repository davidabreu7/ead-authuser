package com.ead.authuser.services;

import com.ead.authuser.dto.InstructorDto;
import com.ead.authuser.dto.UserDto;
import com.ead.authuser.models.UserModel;
import com.querydsl.core.types.Predicate;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface UserService {

    UserModel createUser(@Valid UserDto userModel);
//
    Page<UserModel> getAllUsers(Predicate predicate, Pageable pageable);

    UserModel findById(String id);

    UserModel updateUser(String id, UserDto userModel);

    void deleteUser(String id);

    String updatePassword(String id, UserDto userModel);

    UserModel updateImage(String id, UserDto userModel);

    UserModel registerInstructor(InstructorDto instructorDto);

}
