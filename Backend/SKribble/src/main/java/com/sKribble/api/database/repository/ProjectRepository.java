package com.sKribble.api.database.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.sKribble.api.database.entity.Project;
import com.sKribble.api.database.entity.childEntities.Song;
import com.sKribble.api.database.entity.childEntities.Story;

public interface ProjectRepository extends MongoRepository<Project, String>{

    //=================================[ Common ]==================================
    @Query("{ownerId: ?0}")
    Page<Project> getCurrentUserProjects(String ownerId, Pageable pageable);

    //=================================[ Story ]=================================

    @Query("{ $and : [ {'_id' : ?0} , {'type' : 'Story'} ] }")
    Story findStoryById(String id);
    
    @Query("{ $and : [ {'title' : {$regex: ?0, $options: 'i'}} , {'type' : 'Story'} ] }")
    Page<Story> findStoriesByTitle(String title, Pageable pageable);

    //=================================[ Song ]=================================

    @Query("{ $and : [ {'_id' : ?0} , {'type' : 'Song'} ] }")
    Song findSongById(String id);

    @Query("{ $and: [ {'title' : {$regex: ?0, $options: 'i'}} , {'type' : 'Song'} ] }")
    Page<Song> findSongsByTitle(String title, Pageable pageable);
}
