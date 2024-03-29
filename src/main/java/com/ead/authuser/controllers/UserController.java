package com.ead.authuser.controllers;


import com.ead.authuser.dto.UserDto;
import com.ead.authuser.models.UserModel;
import com.ead.authuser.services.UserService;
import com.ead.authuser.services.impl.UserServiceImpl;
import com.fasterxml.jackson.annotation.JsonView;
import com.querydsl.core.types.Predicate;
import lombok.extern.log4j.Log4j2;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.controller.path}"+"/users")
@CrossOrigin(origins = "*", maxAge = 3600)
@Log4j2
public class UserController {

    private final UserService userService;

    private final DiscoveryClient discoveryClient;

    public UserController(UserServiceImpl userService, DiscoveryClient discoveryClient) {
        this.userService = userService;
        this.discoveryClient = discoveryClient;
    }

    @GetMapping
    public ResponseEntity<Page<UserModel>> getAllUsers(@QuerydslPredicate(root = UserModel.class) Predicate predicate,
                                                                    @PageableDefault() Pageable pageable) {
        return ResponseEntity.ok().body(userService.getAllUsers(predicate, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserModel> findById(@PathVariable String id){
        return ResponseEntity.ok(userService.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserModel> updateUser(
            @PathVariable String id,
            @JsonView(UserDto.UserView.UserPut.class)
            @RequestBody @Validated(UserDto.UserView.UserPut.class) UserDto userModel)
    {
        return ResponseEntity.ok(userService.updateUser(id, userModel));
    }

    @PutMapping("/{id}/password")
    public ResponseEntity<String> updatePassword(
            @PathVariable String id,
            @JsonView(UserDto.UserView.PasswordPut.class)
            @RequestBody @Validated(UserDto.UserView.PasswordPut.class) UserDto userModel)
    {
        return ResponseEntity.ok(userService.updatePassword(id, userModel));
    }

    @PutMapping("/{id}/image")
    public ResponseEntity<UserModel> updateImage(
            @PathVariable String id,
            @JsonView(UserDto.UserView.ImagePut.class)
            @RequestBody @Validated(UserDto.UserView.ImagePut.class) UserDto userModel)
    {
        return ResponseEntity.ok(userService.updateImage(id, userModel));
    }

    @RequestMapping("/service-instances/{applicationName}")
    public List<ServiceInstance> serviceInstancesByApplicationName(
            @PathVariable String applicationName) {
        return this.discoveryClient.getInstances(applicationName);
    }

}


