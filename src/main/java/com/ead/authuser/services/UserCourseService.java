package com.ead.authuser.services;

import com.ead.authuser.client.UserCourseClient;
import com.ead.authuser.dto.CourseRecord;
import com.ead.authuser.dto.ResponsePageDto;
import org.springframework.stereotype.Service;

@Service
public class UserCourseService {
    private final UserCourseClient userCourseClient;

    public UserCourseService(UserCourseClient userCourseClient) {
        this.userCourseClient = userCourseClient;
    }

    public CourseRecord getCourseById(String id) {
        return userCourseClient.getCourseById(id);
    }

    public ResponsePageDto<CourseRecord> getAllCoursesByStudent(String id) {

        return userCourseClient.getAllCoursesByStudent(id);
    }
}

