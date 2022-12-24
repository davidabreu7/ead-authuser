package com.ead.authuser.controllers;


import com.ead.authuser.dto.UserDto;
import com.ead.authuser.models.UserModel;
import com.ead.authuser.services.UserService;
import com.ead.authuser.services.impl.UserServiceImpl;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {

private final UserService userService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<Page<EntityModel<UserModel>>> getAllUsers(@PageableDefault() Pageable pageable) {
        return ResponseEntity.ok().body(userService.getAllUsers(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<UserModel>> findById(@PathVariable String id){
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

}
