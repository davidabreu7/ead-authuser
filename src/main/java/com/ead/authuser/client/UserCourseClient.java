package com.ead.authuser.client;

import com.ead.authuser.dto.CourseRecord;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("course")
public interface UserCourseClient {

    @GetMapping("/courses")
    Page<CourseRecord> getAllCoursesByStudent(@RequestParam(name = "users") String id);

    @GetMapping("/{id}")
    CourseRecord getCourseById(@PathVariable(name = "id") String courseId);
}
