package com.sKribble.api.CRUDTests;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
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
import com.sKribble.api.database.entity.defaults.StoryDefaultContents;
import com.sKribble.api.database.entity.entityFields.storyFields.Chapter;
import com.sKribble.api.database.entity.entityFields.storyFields.Landmark;
import com.sKribble.api.database.entity.entityFields.storyFields.StoryCharacter;
import com.sKribble.api.database.entity.enums.ProjectTypes;
import com.sKribble.api.database.repository.ProjectRepository;
import com.sKribble.api.error.exceptions.CRUDExceptions.story.DuplicateChapterException;

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

    @AfterEach
    void clean(){
        projectRepository.deleteAll();
    }

    @Test
    @Order(1)
    void storyCreationTest(){
        Story testStory = testStoryInstance();
        assertNotNull(projectRepository.save(testStory));
    }

    @Test
    @Order(2)
    void storyFetchTest(){
        Story testStory = testStoryInstance();
        projectRepository.save(testStory);

        assertNotNull(projectRepository.findStoryById(testStory.getId()));
        assertNotNull(projectRepository.findStoriesByTitle(StoryCRUDTestConstants.STORY_TEST_TITLE));
    }

    @Test
    @Order(3)
    void storyExpectedValuesTest(){
        Story testStory = testStoryInstance();
        projectRepository.save(testStory);

        Story fetchedStory = projectRepository.findStoryById(testStory.getId());

        assertEquals(fetchedStory.getTitle(), StoryCRUDTestConstants.STORY_TEST_TITLE);
        assertEquals(fetchedStory.getType(), ProjectTypes.Story);
        assertEquals(fetchedStory.getOwnerId(), StoryCRUDTestConstants.STORY_TEST_OWNER_ID);
    }

    @Test
    @Order(4)
    void storyChapterOperationsTest(){
        Story testStory = testStoryInstance();

        testStory.addChapter(testChapterInstanceOne());
        testStory.addChapter(testChapterInstanceTwo());
        testStory.addChapter(testChapterInstanceThree());
        testStory.addChapter(testChapterInstanceFour());

        //Test for duplicate chapter number
        assertThrows(DuplicateChapterException.class, () -> testStory.addChapter(testChapterInstanceOne()));

        projectRepository.save(testStory);

        Story fetchedStory = projectRepository.findStoryById(testStory.getId());

        Chapter testChapterOne = fetchedStory.getChapters().get(StoryCRUDTestConstants.STORY_TEST_CHAPTER_NUMBER_1);
        Chapter testChapterTwo = fetchedStory.getChapters().get(StoryCRUDTestConstants.STORY_TEST_CHAPTER_NUMBER_2);
        Chapter testChapterThree = fetchedStory.getChapters().get(StoryCRUDTestConstants.STORY_TEST_CHAPTER_NUMBER_3);
        Chapter testChapterFour = fetchedStory.getChapters().get(StoryCRUDTestConstants.STORY_TEST_CHAPTER_NUMBER_4);

        assertAll(() -> {
            assertEquals(testChapterOne.getChapterNumber(), StoryCRUDTestConstants.STORY_TEST_CHAPTER_NUMBER_1);
            assertEquals(testChapterOne.getChapterName(), StoryCRUDTestConstants.STORY_TEST_CHAPTER_NAME_1);
            assertEquals(testChapterOne.getText(), StoryCRUDTestConstants.STORY_TEST_CHAPTER_TEXT);

            assertEquals(testChapterTwo.getChapterNumber(), StoryCRUDTestConstants.STORY_TEST_CHAPTER_NUMBER_2);
            assertEquals(testChapterTwo.getChapterName(), StoryCRUDTestConstants.STORY_TEST_CHAPTER_NAME_2);
            assertEquals(testChapterTwo.getText(), StoryDefaultContents.STORY_CHAPTER_DEFAULT_CONTENT);

            assertEquals(testChapterThree.getChapterNumber(), StoryCRUDTestConstants.STORY_TEST_CHAPTER_NUMBER_3);
            assertEquals(testChapterThree.getChapterName(), StoryCRUDTestConstants.STORY_TEST_CHAPTER_NAME_3);
            assertEquals(testChapterThree.getText(), StoryDefaultContents.STORY_CHAPTER_DEFAULT_CONTENT);

            assertEquals(testChapterFour.getChapterNumber(), StoryCRUDTestConstants.STORY_TEST_CHAPTER_NUMBER_4);
            assertEquals(testChapterFour.getChapterName(), StoryCRUDTestConstants.STORY_TEST_CHAPTER_NAME_4);
            assertEquals(testChapterFour.getText(), StoryDefaultContents.STORY_CHAPTER_DEFAULT_CONTENT);
        });
    }

    @AfterAll
    void cleanup(){
        projectRepository.deleteAll();
    }

    private Story testStoryInstance(){
        return new Story(StoryCRUDTestConstants.STORY_TEST_TITLE, ProjectTypes.Story, null, null, null, StoryCRUDTestConstants.STORY_TEST_OWNER_ID);
    }

    private Chapter testChapterInstanceOne(){
        return new Chapter(StoryCRUDTestConstants.STORY_TEST_CHAPTER_NUMBER_1, StoryCRUDTestConstants.STORY_TEST_CHAPTER_NAME_1, StoryCRUDTestConstants.STORY_TEST_CHAPTER_TEXT);
    }

    private Chapter testChapterInstanceTwo(){
        return new Chapter(StoryCRUDTestConstants.STORY_TEST_CHAPTER_NUMBER_2, StoryCRUDTestConstants.STORY_TEST_CHAPTER_NAME_2, StoryCRUDTestConstants.STORY_TEST_CHAPTER_NULL_TEXT);
    }

    private Chapter testChapterInstanceThree(){
        return new Chapter(StoryCRUDTestConstants.STORY_TEST_CHAPTER_NUMBER_3, StoryCRUDTestConstants.STORY_TEST_CHAPTER_NAME_3, StoryCRUDTestConstants.STORY_TEST_CHAPTER_EMPTY_TEXT);
    }

    private Chapter testChapterInstanceFour(){
        return new Chapter(StoryCRUDTestConstants.STORY_TEST_CHAPTER_NUMBER_4, StoryCRUDTestConstants.STORY_TEST_CHAPTER_NAME_4, StoryCRUDTestConstants.STORY_TEST_CHAPTER_BLANK_TEXT);
    }

    private StoryCharacter testCharacterInstance(){
        return null;
    }

    private Landmark testLandmarkInstance(){
        return null;
    }
}
