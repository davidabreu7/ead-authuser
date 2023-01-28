package com.ead.authuser.services;

import com.ead.authuser.client.UserCourseClient;
import com.ead.authuser.dto.CourseRecord;
import com.ead.authuser.dto.UserRecord;
import com.ead.authuser.exceptions.ResourceNotFoundException;
import com.ead.authuser.models.UserModel;
import com.ead.authuser.repositories.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Log4j2
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

    public Page<CourseRecord> getAllCoursesByStudent(String id) {

        return userCourseClient.getAllCoursesByStudent(id);
    }


    @Transactional
    public UserRecord subscribeUserInCourse(String userId, String courseId) {
        UserModel userModel = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        if (userModel.getCourses().contains(courseId)) {
            throw new ResourceNotFoundException("User already subscribed in this course");
        }

        userModel.getCourses().add(courseId);

        return new UserRecord(userRepository.save(userModel));
    }

    public void deleteUserFromCourse(String userId) {
        userCourseClient.deleteUserFromCourse(userId);
    }

    public void deleteCourseFromUser(String courseId) {
        List<UserModel> users = userRepository.findUserByCourse(courseId);
        log.info("users: {}", users);
        if(users.isEmpty()) {
            throw new ResourceNotFoundException("Course not found");
        }
        users.forEach(user -> {
            user.getCourses().remove(courseId);
            userRepository.save(user);
        });
    }
}

