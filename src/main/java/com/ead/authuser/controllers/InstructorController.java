package com.ead.authuser.controllers;

import com.ead.authuser.dto.InstructorDto;
import com.ead.authuser.models.UserModel;
import com.ead.authuser.services.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.controller.path}"+"/instructors")
@CrossOrigin(origins = "*", maxAge = 3600)
@Log4j2
public class InstructorController {

    private final UserService userService;

    public InstructorController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserModel> registerInstructor(@RequestBody InstructorDto instructorDto) {
        return ResponseEntity.ok(userService.registerInstructor(instructorDto));
    }
}
