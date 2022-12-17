package com.ead.authuser.services;

import com.ead.authuser.models.UserModel;

import java.util.List;

public interface UserService {

    UserModel createUser(UserModel userModel);

    List<UserModel> getAllUsers();
}
