package com.ead.authuser.controllers;

import com.ead.authuser.dto.CourseRecord;
import com.ead.authuser.dto.ResponsePageDto;
import com.ead.authuser.dto.UserRecord;
import com.ead.authuser.services.UserCourseService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@Log4j2
public class UserCourseController {

    private final UserCourseService userCourseService;

    public UserCourseController(UserCourseService userCourseService) {
        this.userCourseService = userCourseService;
    }

    @GetMapping("/users/{id}/courses")
    public ResponseEntity<ResponsePageDto<CourseRecord>> getCoursesByUser(@PathVariable String id) {
        return ResponseEntity.ok(userCourseService.getAllCoursesByStudent(id));
    }

    @PostMapping("/users/{userId}/subscription")
    public ResponseEntity<UserRecord> subscribeUserInCourse(@PathVariable String userId, @RequestParam String courseId) {
        return ResponseEntity.ok(userCourseService.subscribeUserInCourse(userId, courseId));
    }
}
