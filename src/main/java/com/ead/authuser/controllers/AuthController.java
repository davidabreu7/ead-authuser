package com.ead.authuser.controllers;

import com.ead.authuser.dto.UserDto;
import com.ead.authuser.models.UserModel;
import com.ead.authuser.services.UserService;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.controller.path}")
@CrossOrigin(origins = "*", maxAge = 3600)
@Log4j2
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<UserModel> createUser(
            @JsonView(UserDto.UserView.RegistrationPost.class)
            @RequestBody @Validated(UserDto.UserView.RegistrationPost.class) UserDto userModel)
    {
        log.debug("POST /auth/signup createUser userDTO received : {}", userModel.toString());

        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(userModel));
    }
}
