package com.ead.authuser.client;

import com.ead.authuser.dto.CourseRecord;
import com.ead.authuser.dto.ResponsePageDto;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange()
public interface UserCourseClient {

    @GetExchange("/courses")
    ResponsePageDto<CourseRecord> getAllCoursesByStudent(@RequestParam(name = "users") String id);

    @GetExchange("/{id}")
    CourseRecord getCourseById(@PathVariable(name = "id") String courseId);
}
