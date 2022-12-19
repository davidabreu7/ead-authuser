package com.ead.authuser.repositories;

import com.ead.authuser.models.UserModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository  extends MongoRepository<UserModel, String> {

    Optional<UserModel> findById(String id);


    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

}

