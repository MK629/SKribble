package com.sKribble.api.database.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sKribble.api.database.entity.Project;

public interface ProjectRepository extends MongoRepository<Project, String>{
    
}
