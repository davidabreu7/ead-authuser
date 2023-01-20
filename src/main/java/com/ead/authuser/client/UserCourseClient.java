package com.ead.authuser.client;

import com.ead.authuser.dto.CourseRecord;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@FeignClient("course-service")
public interface UserCourseClient {

    @GetMapping("/course-service/courses")
    Page<CourseRecord> getAllCoursesByStudent(@RequestParam(name = "users") String id);

    @GetMapping("/course-service/courses/{id}")
    CourseRecord getCourseById(@PathVariable(name = "id") String courseId);

    @DeleteMapping("/course-service/courses/users/{userId}")
    void deleteUserFromCourse(@PathVariable(name = "userId") String userId);
}
