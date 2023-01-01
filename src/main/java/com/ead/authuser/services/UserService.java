package com.ead.authuser.services;

import com.ead.authuser.dto.UserDto;
import com.ead.authuser.models.UserModel;
import com.querydsl.core.types.Predicate;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;


public interface UserService {

    UserModel createUser(@Valid UserDto userModel);
//
    Page<EntityModel<UserModel>> getAllUsers(Predicate predicate, Pageable pageable);

    EntityModel<UserModel> findById(String id);

    UserModel updateUser(String id, UserDto userModel);

    void deleteUser(String id);

    String updatePassword(String id, UserDto userModel);

    UserModel updateImage(String id, UserDto userModel);
}
