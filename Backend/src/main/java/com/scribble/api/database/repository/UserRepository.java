package com.author.api.database.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.author.api.database.entity.User;

public interface UserRepository extends MongoRepository<User, String>{

}
