package com.scribble.api.database.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.scribble.api.database.entity.User;

public interface UserRepository extends MongoRepository<User, String>{

}
