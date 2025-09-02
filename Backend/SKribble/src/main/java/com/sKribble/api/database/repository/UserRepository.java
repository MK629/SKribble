package com.sKribble.api.database.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import com.sKribble.api.database.entity.User;

public interface UserRepository extends MongoRepository<User, String>{

	@Query("{'_id': ?0}")
	public User findByIdentification(String id);
	
	@Query("{ $or : [ { 'username': ?0 } , { 'email': ?0} ] }")
	public User findUserByUsernameOrEmail(String usernameOrEmail);
}
