package com.ead.authuser.repositories;

import com.ead.authuser.models.UserModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository  extends MongoRepository<UserModel, String> {

}

