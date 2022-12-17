package com.ead.authuser.services;

import com.ead.authuser.models.User;

import java.util.List;

public interface UserService {

    public User createUser(User user);

    public List<User> getAllUsers();
}
