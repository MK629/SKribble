package com.sKribble.api.database.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sKribble.api.database.entity.User;

public interface UserRepository extends MongoRepository<User, String>{

}
