package com.ead.authuser.services;

import com.ead.authuser.client.UserCourseClient;
import com.ead.authuser.dto.CourseRecord;
import com.ead.authuser.dto.ResponsePageDto;
import com.ead.authuser.dto.UserRecord;
import com.ead.authuser.exceptions.ResourceNotFoundException;
import com.ead.authuser.models.UserModel;
import com.ead.authuser.repositories.UserRepository;
import org.springframework.stereotype.Service;


@Service
public class UserCourseService {
    private final UserCourseClient userCourseClient;
    private final UserRepository userRepository;

    public UserCourseService(UserCourseClient userCourseClient, UserRepository userRepository) {
        this.userCourseClient = userCourseClient;
        this.userRepository = userRepository;
    }

    public CourseRecord getCourseById(String id) {
        return userCourseClient.getCourseById(id);
    }

    public ResponsePageDto<CourseRecord> getAllCoursesByStudent(String id) {

        return userCourseClient.getAllCoursesByStudent(id);
    }

    public UserRecord subscribeUserInCourse(String userId, String courseId) {
        UserModel userModel = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        if (userModel.getCourses().contains(courseId)) {
            throw new ResourceNotFoundException("User already subscribed in this course");
        }

        userModel.getCourses().add(courseId);

        return new UserRecord(userRepository.save(userModel));
    }
}

