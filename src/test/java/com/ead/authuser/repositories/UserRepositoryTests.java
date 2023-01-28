package com.ead.authuser.repositories;

import com.ead.authuser.models.UserModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.util.List;


@DataMongoTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTests {

    @Autowired
    UserRepository userRepository;

    @Test
    void findUserByCourseShouldReturnUserWhenCourseExists() {
        List<UserModel> user = userRepository.findUserByCourse("63cbdaad872eda6900729a7f");
        Assertions.assertNotNull(user);
    }
}
