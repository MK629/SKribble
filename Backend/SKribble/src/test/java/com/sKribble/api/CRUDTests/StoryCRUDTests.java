package com.sKribble.api.CRUDTests;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;

import com.sKribble.api.constants.StoryCRUDTestConstants;
import com.sKribble.api.database.entity.childEntities.Story;
import com.sKribble.api.database.entity.entityFields.storyFields.Chapter;
import com.sKribble.api.database.entity.entityFields.storyFields.Landmark;
import com.sKribble.api.database.entity.entityFields.storyFields.StoryCharacter;
import com.sKribble.api.database.entity.enums.ProjectTypes;
import com.sKribble.api.database.repository.ProjectRepository;

@DataMongoTest
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StoryCRUDTests {

    @Autowired
    private ProjectRepository projectRepository;

    @BeforeAll
    void setUp(){

    }

    @Test
    @Order(1)
    void storyCreationTest(){

    }

    @AfterAll
    void cleanup(){
        projectRepository.deleteAll();
    }

    private Story testStoryInstance(){
        return new Story(StoryCRUDTestConstants.STORY_TEST_TITLE, ProjectTypes.Story, null, null, null, null);
    }

    private Chapter testChapterInstance(){
        return null;
    }

    private StoryCharacter testCharacterInstance(){
        return null;
    }

    private Landmark testLandmarkInstance(){
        return null;
    }
}
