package com.ead.authuser.controllers;

import com.ead.authuser.dto.UserDto;
import com.ead.authuser.models.UserModel;
import com.ead.authuser.services.UserService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private final UserService userService;

    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<UserModel> createUser(
            @JsonView(UserDto.UserView.RegistrationPost.class)
            @RequestBody @Validated(UserDto.UserView.RegistrationPost.class) UserDto userModel)
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(userModel));
    }

}
