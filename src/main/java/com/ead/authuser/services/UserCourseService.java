package com.ead.authuser.services;

import com.ead.authuser.client.UserCourseClient;
import com.ead.authuser.dto.CourseRecord;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;


@Service
public class UserCourseService {
    private final UserCourseClient userCourseClient;


    public UserCourseService(UserCourseClient userCourseClient) {
        this.userCourseClient = userCourseClient;
    }

    public Page<CourseRecord> getAllCoursesByStudent(String id) {

        return userCourseClient.getAllCoursesByStudent(id);
    }

}

