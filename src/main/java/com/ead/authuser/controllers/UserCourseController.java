package com.ead.authuser.controllers;

import com.ead.authuser.dto.CourseRecord;
import com.ead.authuser.dto.ResponsePageDto;
import com.ead.authuser.services.UserCourseService;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@Log4j2
public class UserCourseController {

    private final UserCourseService userCourseService;

    public UserCourseController(UserCourseService userCourseService) {
        this.userCourseService = userCourseService;
    }

    @GetMapping("/users/{id}/courses")
    public ResponseEntity<ResponsePageDto<CourseRecord>> getCoursesByUser(@PathVariable String id, Pageable pageable) {
        return ResponseEntity.ok(userCourseService.getAllCoursesByStudent(id));
    }
}
