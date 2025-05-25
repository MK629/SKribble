package com.sKribble.api.database.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.sKribble.api.database.entity.Project;
import com.sKribble.api.database.entity.childEntities.Story;

public interface ProjectRepository extends MongoRepository<Project, String>{

    //For 'Story' types.
    @Query("{ $and : [ {'_id' : ?0} , {'type' : 'Story'} ] }")
    Story findStoryById(String id);
    
    @Query("{ $and : [ {'title' : {$regex: ?0, $options: 'i'}} , {'type' : 'Story'} ] }")
    List<Story> findStoriesByTitle(String title);
}
