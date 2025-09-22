package com.sKribble.api.database.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.sKribble.api.database.entity.Project;
import com.sKribble.api.database.entity.childEntities.Song;
import com.sKribble.api.database.entity.childEntities.Story;

public interface ProjectRepository extends MongoRepository<Project, String>{

    //=================================[ Common ]==================================
    @Query("{ownerId: ?0}")
    List<Project> getCurrentUserProjects(String ownerId);

    //=================================[ Story ]=================================

    @Query("{ $and : [ {'_id' : ?0} , {'type' : 'Story'} ] }")
    Story findStoryById(String id);
    
    @Query("{ $and : [ {'title' : {$regex: ?0, $options: 'i'}} , {'type' : 'Story'} ] }")
    List<Story> findStoriesByTitle(String title);

    //=================================[ Song ]=================================

    @Query("{ $and : [ {'_id' : ?0} , {'type' : 'Song'} ] }")
    Song findSongById(String id);

    @Query("{ $and: [ {'title' : {$regex: ?0, $options: 'i'}} , {'type' : 'Song'} ] }")
    List<Song> findSongsByTitle(String title);
}
