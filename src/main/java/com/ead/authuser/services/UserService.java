package com.ead.authuser.services;

import com.ead.authuser.dto.UserDto;
import com.ead.authuser.models.UserModel;
import jakarta.validation.Valid;

import java.util.List;

public interface UserService {

    UserModel createUser(@Valid UserDto userModel);

    List<UserModel> getAllUsers();

    UserModel findById(String id);

    UserModel updateUser(String id, UserDto userModel);

    void deleteUser(String id);

    String updatePassword(String id, UserDto userModel);

    UserModel updateImage(String id, UserDto userModel);
}
