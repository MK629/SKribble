package com.sKribble.api.CRUDTests;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
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

        assertAll(() -> {
            assertNotNull(projectRepository.findStoryById(testStory.getId()));
            assertNotNull(projectRepository.findStoriesByTitle(StoryCRUDTestConstants.STORY_TEST_TITLE));
        });
    }

    @Test
    @Order(3)
    void storyExpectedValuesTest(){
        Story testStory = testStoryInstance();
        projectRepository.save(testStory);

        Story fetchedStory = projectRepository.findStoryById(testStory.getId());

        assertAll(() -> {
            assertEquals(fetchedStory.getTitle(), StoryCRUDTestConstants.STORY_TEST_TITLE);
            assertEquals(fetchedStory.getType(), ProjectTypes.Story);
            assertEquals(fetchedStory.getOwnerId(), StoryCRUDTestConstants.STORY_TEST_OWNER_ID);
        });
    }

    @Test
    @Order(4)
    void storyChapterOperationsTest(){
        Story testStory = testStoryInstance();

        testStory.addChapter(new Chapter(StoryCRUDTestConstants.STORY_TEST_CHAPTER_NUMBER_1, StoryCRUDTestConstants.STORY_TEST_CHAPTER_NAME_1, StoryCRUDTestConstants.STORY_TEST_FULL_STRING));
        testStory.addChapter(new Chapter(StoryCRUDTestConstants.STORY_TEST_CHAPTER_NUMBER_2, StoryCRUDTestConstants.STORY_TEST_CHAPTER_NAME_2, StoryCRUDTestConstants.STORY_TEST_NULL_STRING));
        testStory.addChapter(new Chapter(StoryCRUDTestConstants.STORY_TEST_CHAPTER_NUMBER_3, StoryCRUDTestConstants.STORY_TEST_CHAPTER_NAME_3, StoryCRUDTestConstants.STORY_TEST_EMPTY_STRING));
        testStory.addChapter(new Chapter(StoryCRUDTestConstants.STORY_TEST_CHAPTER_NUMBER_4, StoryCRUDTestConstants.STORY_TEST_CHAPTER_NAME_4, StoryCRUDTestConstants.STORY_TEST_BLANK_STRING));

        //Test for duplicate chapter number
        assertThrows(DuplicateChapterException.class, () -> testStory.addChapter(new Chapter(StoryCRUDTestConstants.STORY_TEST_CHAPTER_NUMBER_1, StoryCRUDTestConstants.STORY_TEST_CHAPTER_NAME_1, StoryCRUDTestConstants.STORY_TEST_FULL_STRING)));

        projectRepository.save(testStory);

        Story fetchedStory = projectRepository.findStoryById(testStory.getId());

        Chapter testChapterOne = fetchedStory.getChapters().get(StoryCRUDTestConstants.STORY_TEST_CHAPTER_NUMBER_1);
        Chapter testChapterTwo = fetchedStory.getChapters().get(StoryCRUDTestConstants.STORY_TEST_CHAPTER_NUMBER_2);
        Chapter testChapterThree = fetchedStory.getChapters().get(StoryCRUDTestConstants.STORY_TEST_CHAPTER_NUMBER_3);
        Chapter testChapterFour = fetchedStory.getChapters().get(StoryCRUDTestConstants.STORY_TEST_CHAPTER_NUMBER_4);

        assertAll(() -> {
            assertNotNull(testChapterOne);
            assertEquals(testChapterOne.getChapterNumber(), StoryCRUDTestConstants.STORY_TEST_CHAPTER_NUMBER_1);
            assertEquals(testChapterOne.getChapterName(), StoryCRUDTestConstants.STORY_TEST_CHAPTER_NAME_1);
            assertEquals(testChapterOne.getText(), StoryCRUDTestConstants.STORY_TEST_FULL_STRING);

            assertNotNull(testChapterTwo);
            assertEquals(testChapterTwo.getChapterNumber(), StoryCRUDTestConstants.STORY_TEST_CHAPTER_NUMBER_2);
            assertEquals(testChapterTwo.getChapterName(), StoryCRUDTestConstants.STORY_TEST_CHAPTER_NAME_2);
            assertEquals(testChapterTwo.getText(), StoryDefaultContents.STORY_CHAPTER_DEFAULT_CONTENT);

            assertNotNull(testChapterThree);
            assertEquals(testChapterThree.getChapterNumber(), StoryCRUDTestConstants.STORY_TEST_CHAPTER_NUMBER_3);
            assertEquals(testChapterThree.getChapterName(), StoryCRUDTestConstants.STORY_TEST_CHAPTER_NAME_3);
            assertEquals(testChapterThree.getText(), StoryDefaultContents.STORY_CHAPTER_DEFAULT_CONTENT);

            assertNotNull(testChapterFour);
            assertEquals(testChapterFour.getChapterNumber(), StoryCRUDTestConstants.STORY_TEST_CHAPTER_NUMBER_4);
            assertEquals(testChapterFour.getChapterName(), StoryCRUDTestConstants.STORY_TEST_CHAPTER_NAME_4);
            assertEquals(testChapterFour.getText(), StoryDefaultContents.STORY_CHAPTER_DEFAULT_CONTENT);
        });

        fetchedStory.deleteChapter(StoryCRUDTestConstants.STORY_TEST_CHAPTER_NUMBER_1);

        projectRepository.save(fetchedStory);

        Story fetchedStory2 = projectRepository.findStoryById(fetchedStory.getId());

        assertNull(fetchedStory2.getChapters().get(StoryCRUDTestConstants.STORY_TEST_CHAPTER_NUMBER_1));
    }

    @Test
    @Order(5)
    void storyCharacterOperationsTest(){
        Story testStory = testStoryInstance();

        testStory.addCharacter(new StoryCharacter(StoryCRUDTestConstants.STORY_TEST_CHARACTER_ID_1, StoryCRUDTestConstants.STORY_TEST_CHARACTER_NAME_1, StoryCRUDTestConstants.STORY_TEST_FULL_STRING, StoryCRUDTestConstants.STORY_TEST_FULL_STRING));
        testStory.addCharacter(new StoryCharacter(StoryCRUDTestConstants.STORY_TEST_CHARACTER_ID_2, StoryCRUDTestConstants.STORY_TEST_CHARACTER_NAME_2, StoryCRUDTestConstants.STORY_TEST_NULL_STRING, StoryCRUDTestConstants.STORY_TEST_NULL_STRING));
        testStory.addCharacter(new StoryCharacter(StoryCRUDTestConstants.STORY_TEST_CHARACTER_ID_3, StoryCRUDTestConstants.STORY_TEST_CHARACTER_NAME_3, StoryCRUDTestConstants.STORY_TEST_EMPTY_STRING, StoryCRUDTestConstants.STORY_TEST_EMPTY_STRING));
        testStory.addCharacter(new StoryCharacter(StoryCRUDTestConstants.STORY_TEST_CHARACTER_ID_4, StoryCRUDTestConstants.STORY_TEST_CHARACTER_NAME_4, StoryCRUDTestConstants.STORY_TEST_BLANK_STRING, StoryCRUDTestConstants.STORY_TEST_BLANK_STRING));

        projectRepository.save(testStory);

        Story fetchedStory = projectRepository.findStoryById(testStory.getId());

        StoryCharacter testCharacterOne = fetchedStory.getCharacters().get(StoryCRUDTestConstants.STORY_TEST_CHARACTER_ID_1);
        StoryCharacter testCharacterTwo = fetchedStory.getCharacters().get(StoryCRUDTestConstants.STORY_TEST_CHARACTER_ID_2);
        StoryCharacter testCharacterThree = fetchedStory.getCharacters().get(StoryCRUDTestConstants.STORY_TEST_CHARACTER_ID_3);
        StoryCharacter testCharacterFour = fetchedStory.getCharacters().get(StoryCRUDTestConstants.STORY_TEST_CHARACTER_ID_4);

        assertAll(() -> {
            assertNotNull(testCharacterOne);
            assertEquals(testCharacterOne.getCharacterId(), StoryCRUDTestConstants.STORY_TEST_CHARACTER_ID_1);
            assertEquals(testCharacterOne.getCharacterName(), StoryCRUDTestConstants.STORY_TEST_CHARACTER_NAME_1);
            assertEquals(testCharacterOne.getDescription(), StoryCRUDTestConstants.STORY_TEST_FULL_STRING);
            assertEquals(testCharacterOne.getImageUrl(), StoryCRUDTestConstants.STORY_TEST_FULL_STRING);

            assertNotNull(testCharacterTwo);
            assertEquals(testCharacterTwo.getCharacterId(), StoryCRUDTestConstants.STORY_TEST_CHARACTER_ID_2);
            assertEquals(testCharacterTwo.getCharacterName(), StoryCRUDTestConstants.STORY_TEST_CHARACTER_NAME_2);
            assertEquals(testCharacterTwo.getDescription(), StoryDefaultContents.STORY_CHARACTER_DESC_DEFAULT_CONTENT);
            assertEquals(testCharacterTwo.getImageUrl(), StoryDefaultContents.STORY_CHARACTER_IMAGE_URL_DEFAULT_CONTENT);

            assertNotNull(testCharacterThree);
            assertEquals(testCharacterThree.getCharacterId(), StoryCRUDTestConstants.STORY_TEST_CHARACTER_ID_3);
            assertEquals(testCharacterThree.getCharacterName(), StoryCRUDTestConstants.STORY_TEST_CHARACTER_NAME_3);
            assertEquals(testCharacterThree.getDescription(), StoryDefaultContents.STORY_CHARACTER_DESC_DEFAULT_CONTENT);
            assertEquals(testCharacterThree.getImageUrl(), StoryDefaultContents.STORY_CHARACTER_IMAGE_URL_DEFAULT_CONTENT);

            assertNotNull(testCharacterFour);
            assertEquals(testCharacterFour.getCharacterId(), StoryCRUDTestConstants.STORY_TEST_CHARACTER_ID_4);
            assertEquals(testCharacterFour.getCharacterName(), StoryCRUDTestConstants.STORY_TEST_CHARACTER_NAME_4);
            assertEquals(testCharacterFour.getDescription(), StoryDefaultContents.STORY_CHARACTER_DESC_DEFAULT_CONTENT);
            assertEquals(testCharacterFour.getImageUrl(), StoryDefaultContents.STORY_CHARACTER_IMAGE_URL_DEFAULT_CONTENT);
        });

        fetchedStory.deleteCharacter(StoryCRUDTestConstants.STORY_TEST_CHARACTER_ID_1);

        projectRepository.save(fetchedStory);

        Story fetchedStory2 = projectRepository.findStoryById(fetchedStory.getId());

        assertNull(fetchedStory2.getCharacters().get(StoryCRUDTestConstants.STORY_TEST_CHARACTER_ID_1));
    }

    @Test
    @Order(6)
    void storyLandmarkOperationsTest(){
        Story testStory = testStoryInstance();

        testStory.addLandmark(new Landmark(StoryCRUDTestConstants.STORY_TEST_LANDMARK_ID_1, StoryCRUDTestConstants.STORY_TEST_LANDMARK_NAME_1, StoryCRUDTestConstants.STORY_TEST_FULL_STRING, StoryCRUDTestConstants.STORY_TEST_FULL_STRING));
        testStory.addLandmark(new Landmark(StoryCRUDTestConstants.STORY_TEST_LANDMARK_ID_2, StoryCRUDTestConstants.STORY_TEST_LANDMARK_NAME_2, StoryCRUDTestConstants.STORY_TEST_NULL_STRING, StoryCRUDTestConstants.STORY_TEST_NULL_STRING));
        testStory.addLandmark(new Landmark(StoryCRUDTestConstants.STORY_TEST_LANDMARK_ID_3, StoryCRUDTestConstants.STORY_TEST_LANDMARK_NAME_3, StoryCRUDTestConstants.STORY_TEST_EMPTY_STRING, StoryCRUDTestConstants.STORY_TEST_EMPTY_STRING));
        testStory.addLandmark(new Landmark(StoryCRUDTestConstants.STORY_TEST_LANDMARK_ID_4, StoryCRUDTestConstants.STORY_TEST_LANDMARK_NAME_4, StoryCRUDTestConstants.STORY_TEST_BLANK_STRING, StoryCRUDTestConstants.STORY_TEST_BLANK_STRING));

        projectRepository.save(testStory);

        Story fetchedStory = projectRepository.findStoryById(testStory.getId());

        Landmark testLandmarkOne = fetchedStory.getLandmarks().get(StoryCRUDTestConstants.STORY_TEST_LANDMARK_ID_1);
        Landmark testLandmarkTwo = fetchedStory.getLandmarks().get(StoryCRUDTestConstants.STORY_TEST_LANDMARK_ID_2);
        Landmark testLandmarkThree = fetchedStory.getLandmarks().get(StoryCRUDTestConstants.STORY_TEST_LANDMARK_ID_3);
        Landmark testLandmarkFour = fetchedStory.getLandmarks().get(StoryCRUDTestConstants.STORY_TEST_LANDMARK_ID_4);

        assertAll(() -> {
            assertNotNull(testLandmarkOne);
            assertEquals(testLandmarkOne.getLandmarkId(), StoryCRUDTestConstants.STORY_TEST_LANDMARK_ID_1);
            assertEquals(testLandmarkOne.getLandmarkName(), StoryCRUDTestConstants.STORY_TEST_LANDMARK_NAME_1);
            assertEquals(testLandmarkOne.getDescription(), StoryCRUDTestConstants.STORY_TEST_FULL_STRING);
            assertEquals(testLandmarkOne.getImageUrl(), StoryCRUDTestConstants.STORY_TEST_FULL_STRING);

            assertNotNull(testLandmarkTwo);
            assertEquals(testLandmarkTwo.getLandmarkId(), StoryCRUDTestConstants.STORY_TEST_LANDMARK_ID_2);
            assertEquals(testLandmarkTwo.getLandmarkName(), StoryCRUDTestConstants.STORY_TEST_LANDMARK_NAME_2);
            assertEquals(testLandmarkTwo.getDescription(), StoryDefaultContents.STORY_LANDMARK_DESC_DEFAULT_CONTENT);
            assertEquals(testLandmarkTwo.getImageUrl(), StoryDefaultContents.STORY_LANDMARK_IMAGE_URL_DEFAULT_CONTENT);

            assertNotNull(testLandmarkThree);
            assertEquals(testLandmarkThree.getLandmarkId(), StoryCRUDTestConstants.STORY_TEST_LANDMARK_ID_3);
            assertEquals(testLandmarkThree.getLandmarkName(), StoryCRUDTestConstants.STORY_TEST_LANDMARK_NAME_3);
            assertEquals(testLandmarkThree.getDescription(), StoryDefaultContents.STORY_LANDMARK_DESC_DEFAULT_CONTENT);
            assertEquals(testLandmarkThree.getImageUrl(), StoryDefaultContents.STORY_LANDMARK_IMAGE_URL_DEFAULT_CONTENT);

            assertNotNull(testLandmarkFour);
            assertEquals(testLandmarkFour.getLandmarkId(), StoryCRUDTestConstants.STORY_TEST_LANDMARK_ID_4);
            assertEquals(testLandmarkFour.getLandmarkName(), StoryCRUDTestConstants.STORY_TEST_LANDMARK_NAME_4);
            assertEquals(testLandmarkFour.getDescription(), StoryDefaultContents.STORY_LANDMARK_DESC_DEFAULT_CONTENT);
            assertEquals(testLandmarkFour.getImageUrl(), StoryDefaultContents.STORY_LANDMARK_IMAGE_URL_DEFAULT_CONTENT);
        });

        fetchedStory.deleteLandmark(StoryCRUDTestConstants.STORY_TEST_LANDMARK_ID_1);

        projectRepository.save(fetchedStory);

        Story fetchedStory2 = projectRepository.findStoryById(fetchedStory.getId());

        assertNull(fetchedStory2.getLandmarks().get(StoryCRUDTestConstants.STORY_TEST_LANDMARK_ID_1));
    }

    @Test
    @Order(7)
    void storyDeletionTest(){
        Story testStory = testStoryInstance();
        
        projectRepository.save(testStory);
        assertNotNull(projectRepository.findStoryById(testStory.getId()));

        projectRepository.deleteById(testStory.getId());
        assertNull(projectRepository.findStoryById(testStory.getId()));
    }

    @AfterAll
    void cleanup(){
        projectRepository.deleteAll();
    }

    private Story testStoryInstance(){
        return new Story(StoryCRUDTestConstants.STORY_TEST_TITLE, ProjectTypes.Story, null, null, null, StoryCRUDTestConstants.STORY_TEST_OWNER_ID);
    }
}
