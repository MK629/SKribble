package com.sKribble.api.database.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.sKribble.api.database.entity.Project;
import com.sKribble.api.database.entity.childEntities.Story;
import com.sKribble.api.database.entity.enums.ProjectTypes;

public interface ProjectRepository extends MongoRepository<Project, String>{

    @Query("{ $and : [ {'_id' : ?0} , {'type' : ?1} ] }")
    Story findStoryByIdentification(String id, ProjectTypes type);
    
    @Query("{ $and : [ {'title' : {$regex: ?0, $options: 'i'}} , {'type' : ?1} ] }")
    List<Story> findStoriesByTitle(String title, ProjectTypes type);
}
