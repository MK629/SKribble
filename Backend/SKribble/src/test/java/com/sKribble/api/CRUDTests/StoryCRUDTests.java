package com.sKribble.api.CRUDTests;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;

import com.sKribble.api.constants.StoryTestConstants;
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
            assertNotNull(projectRepository.findStoriesByTitle(StoryTestConstants.STORY_TEST_TITLE));
        });
    }

    @Test
    @Order(3)
    void storyExpectedValuesTest(){
        Story testStory = testStoryInstance();
        projectRepository.save(testStory);

        Story fetchedStory = projectRepository.findStoryById(testStory.getId());

        assertAll(() -> {
            assertEquals(fetchedStory.getTitle(), StoryTestConstants.STORY_TEST_TITLE);
            assertEquals(fetchedStory.getType(), ProjectTypes.Story);
            assertEquals(fetchedStory.getOwnerId(), StoryTestConstants.STORY_TEST_OWNER_ID);
        });
    }

    @Test
    @Order(4)
    void storyChapterOperationsTest(){
        Story testStory = testStoryInstance();

        testStory.addChapter(new Chapter(StoryTestConstants.STORY_TEST_CHAPTER_NUMBER_1, StoryTestConstants.STORY_TEST_CHAPTER_NAME_1, StoryTestConstants.STORY_TEST_FULL_STRING));
        testStory.addChapter(new Chapter(StoryTestConstants.STORY_TEST_CHAPTER_NUMBER_2, StoryTestConstants.STORY_TEST_CHAPTER_NAME_2, StoryTestConstants.STORY_TEST_NULL_STRING));
        testStory.addChapter(new Chapter(StoryTestConstants.STORY_TEST_CHAPTER_NUMBER_3, StoryTestConstants.STORY_TEST_CHAPTER_NAME_3, StoryTestConstants.STORY_TEST_EMPTY_STRING));
        testStory.addChapter(new Chapter(StoryTestConstants.STORY_TEST_CHAPTER_NUMBER_4, StoryTestConstants.STORY_TEST_CHAPTER_NAME_4, StoryTestConstants.STORY_TEST_BLANK_STRING));

        //Test for duplicate chapter number
        assertThrows(DuplicateChapterException.class, () -> testStory.addChapter(new Chapter(StoryTestConstants.STORY_TEST_CHAPTER_NUMBER_1, StoryTestConstants.STORY_TEST_CHAPTER_NAME_1, StoryTestConstants.STORY_TEST_FULL_STRING)));

        projectRepository.save(testStory);

        Story fetchedStory = projectRepository.findStoryById(testStory.getId());

        Chapter testChapterOne = fetchedStory.getChapters().get(StoryTestConstants.STORY_TEST_CHAPTER_NUMBER_1);
        Chapter testChapterTwo = fetchedStory.getChapters().get(StoryTestConstants.STORY_TEST_CHAPTER_NUMBER_2);
        Chapter testChapterThree = fetchedStory.getChapters().get(StoryTestConstants.STORY_TEST_CHAPTER_NUMBER_3);
        Chapter testChapterFour = fetchedStory.getChapters().get(StoryTestConstants.STORY_TEST_CHAPTER_NUMBER_4);

        assertAll(() -> {
            assertNotNull(testChapterOne);
            assertEquals(StoryTestConstants.STORY_TEST_CHAPTER_NUMBER_1, testChapterOne.getChapterNumber());
            assertEquals(StoryTestConstants.STORY_TEST_CHAPTER_NAME_1, testChapterOne.getChapterName());
            assertEquals(StoryTestConstants.STORY_TEST_FULL_STRING, testChapterOne.getText());

            assertNotNull(testChapterTwo);
            assertEquals(StoryTestConstants.STORY_TEST_CHAPTER_NUMBER_2, testChapterTwo.getChapterNumber());
            assertEquals(StoryTestConstants.STORY_TEST_CHAPTER_NAME_2, testChapterTwo.getChapterName());
            assertEquals(StoryDefaultContents.STORY_CHAPTER_DEFAULT_CONTENT, testChapterTwo.getText());

            assertNotNull(testChapterThree);
            assertEquals(StoryTestConstants.STORY_TEST_CHAPTER_NUMBER_3, testChapterThree.getChapterNumber());
            assertEquals(StoryTestConstants.STORY_TEST_CHAPTER_NAME_3, testChapterThree.getChapterName());
            assertEquals(StoryDefaultContents.STORY_CHAPTER_DEFAULT_CONTENT, testChapterThree.getText());

            assertNotNull(testChapterFour);
            assertEquals(StoryTestConstants.STORY_TEST_CHAPTER_NUMBER_4, testChapterFour.getChapterNumber());
            assertEquals(StoryTestConstants.STORY_TEST_CHAPTER_NAME_4, testChapterFour.getChapterName());
            assertEquals(StoryDefaultContents.STORY_CHAPTER_DEFAULT_CONTENT, testChapterFour.getText());
        });
        
        fetchedStory.editChapter(StoryTestConstants.STORY_TEST_CHAPTER_NUMBER_1, StoryTestConstants.STORY_TEST_CHAPTER_NAME_2, StoryTestConstants.STORY_TEST_NULL_STRING);

        Chapter testEditedChapterOne = fetchedStory.getChapters().get(StoryTestConstants.STORY_TEST_CHAPTER_NUMBER_1);

        assertAll(() -> {
            assertEquals(StoryTestConstants.STORY_TEST_CHAPTER_NAME_2, testEditedChapterOne.getChapterName());
            assertEquals(StoryDefaultContents.STORY_CHAPTER_DEFAULT_CONTENT, testEditedChapterOne.getText());
        });

        fetchedStory.deleteChapter(StoryTestConstants.STORY_TEST_CHAPTER_NUMBER_1);

        projectRepository.save(fetchedStory);

        Story fetchedStory2 = projectRepository.findStoryById(fetchedStory.getId());

        assertNull(fetchedStory2.getChapters().get(StoryTestConstants.STORY_TEST_CHAPTER_NUMBER_1));
    }

    @Test
    @Order(5)
    void storyCharacterOperationsTest(){
        Story testStory = testStoryInstance();
        
        testStory.addCharacter(new StoryCharacter(StoryTestConstants.STORY_TEST_CHARACTER_ID_1, StoryTestConstants.STORY_TEST_CHARACTER_NAME_1, StoryTestConstants.STORY_TEST_FULL_STRING, StoryTestConstants.STORY_TEST_FULL_STRING));
        testStory.addCharacter(new StoryCharacter(StoryTestConstants.STORY_TEST_CHARACTER_ID_2, StoryTestConstants.STORY_TEST_CHARACTER_NAME_2, StoryTestConstants.STORY_TEST_NULL_STRING, StoryTestConstants.STORY_TEST_NULL_STRING));
        testStory.addCharacter(new StoryCharacter(StoryTestConstants.STORY_TEST_CHARACTER_ID_3, StoryTestConstants.STORY_TEST_CHARACTER_NAME_3, StoryTestConstants.STORY_TEST_EMPTY_STRING, StoryTestConstants.STORY_TEST_EMPTY_STRING));
        testStory.addCharacter(new StoryCharacter(StoryTestConstants.STORY_TEST_CHARACTER_ID_4, StoryTestConstants.STORY_TEST_CHARACTER_NAME_4, StoryTestConstants.STORY_TEST_BLANK_STRING, StoryTestConstants.STORY_TEST_BLANK_STRING));

        projectRepository.save(testStory);

        Story fetchedStory = projectRepository.findStoryById(testStory.getId());

        StoryCharacter testCharacterOne = fetchedStory.getCharacters().get(StoryTestConstants.STORY_TEST_CHARACTER_ID_1);
        StoryCharacter testCharacterTwo = fetchedStory.getCharacters().get(StoryTestConstants.STORY_TEST_CHARACTER_ID_2);
        StoryCharacter testCharacterThree = fetchedStory.getCharacters().get(StoryTestConstants.STORY_TEST_CHARACTER_ID_3);
        StoryCharacter testCharacterFour = fetchedStory.getCharacters().get(StoryTestConstants.STORY_TEST_CHARACTER_ID_4);

        assertAll(() -> {
            assertNotNull(testCharacterOne);
            assertEquals(StoryTestConstants.STORY_TEST_CHARACTER_ID_1, testCharacterOne.getCharacterId());
            assertEquals(StoryTestConstants.STORY_TEST_CHARACTER_NAME_1, testCharacterOne.getCharacterName());
            assertEquals(StoryTestConstants.STORY_TEST_FULL_STRING, testCharacterOne.getDescription());
            assertEquals(StoryTestConstants.STORY_TEST_FULL_STRING, testCharacterOne.getImageUrl());

            assertNotNull(testCharacterTwo);
            assertEquals(StoryTestConstants.STORY_TEST_CHARACTER_ID_2, testCharacterTwo.getCharacterId());
            assertEquals(StoryTestConstants.STORY_TEST_CHARACTER_NAME_2, testCharacterTwo.getCharacterName());
            assertEquals(StoryDefaultContents.STORY_CHARACTER_DESC_DEFAULT_CONTENT, testCharacterTwo.getDescription());
            assertEquals(StoryDefaultContents.STORY_CHARACTER_IMAGE_URL_DEFAULT_CONTENT, testCharacterTwo.getImageUrl());

            assertNotNull(testCharacterThree);
            assertEquals(StoryTestConstants.STORY_TEST_CHARACTER_ID_3, testCharacterThree.getCharacterId());
            assertEquals(StoryTestConstants.STORY_TEST_CHARACTER_NAME_3, testCharacterThree.getCharacterName());
            assertEquals(StoryDefaultContents.STORY_CHARACTER_DESC_DEFAULT_CONTENT, testCharacterThree.getDescription());
            assertEquals(StoryDefaultContents.STORY_CHARACTER_IMAGE_URL_DEFAULT_CONTENT, testCharacterThree.getImageUrl());

            assertNotNull(testCharacterFour);
            assertEquals(StoryTestConstants.STORY_TEST_CHARACTER_ID_4, testCharacterFour.getCharacterId());
            assertEquals(StoryTestConstants.STORY_TEST_CHARACTER_NAME_4, testCharacterFour.getCharacterName());
            assertEquals(StoryDefaultContents.STORY_CHARACTER_DESC_DEFAULT_CONTENT, testCharacterFour.getDescription());
            assertEquals(StoryDefaultContents.STORY_CHARACTER_IMAGE_URL_DEFAULT_CONTENT, testCharacterFour.getImageUrl());
        });

        fetchedStory.editCharacter(StoryTestConstants.STORY_TEST_CHARACTER_ID_1, StoryTestConstants.STORY_TEST_CHARACTER_NAME_2, StoryTestConstants.STORY_TEST_NULL_STRING);
        fetchedStory.changeCharacterImage(StoryTestConstants.STORY_TEST_CHARACTER_ID_1, StoryTestConstants.STORY_TEST_NULL_STRING);

        StoryCharacter testEditedCharacterOne = fetchedStory.getCharacters().get(StoryTestConstants.STORY_TEST_CHARACTER_ID_1);
        
        assertAll(() -> {
            assertEquals(StoryTestConstants.STORY_TEST_CHARACTER_NAME_2, testEditedCharacterOne.getCharacterName());
            assertEquals(StoryDefaultContents.STORY_CHARACTER_DESC_DEFAULT_CONTENT, testEditedCharacterOne.getDescription());
            assertEquals(StoryDefaultContents.STORY_CHARACTER_IMAGE_URL_DEFAULT_CONTENT, testEditedCharacterOne.getImageUrl());
        });

        fetchedStory.deleteCharacter(StoryTestConstants.STORY_TEST_CHARACTER_ID_1);

        projectRepository.save(fetchedStory);

        Story fetchedStory2 = projectRepository.findStoryById(fetchedStory.getId());

        assertNull(fetchedStory2.getCharacters().get(StoryTestConstants.STORY_TEST_CHARACTER_ID_1));
    }

    @Test
    @Order(6)
    void storyLandmarkOperationsTest(){
        Story testStory = testStoryInstance();

        testStory.addLandmark(new Landmark(StoryTestConstants.STORY_TEST_LANDMARK_ID_1, StoryTestConstants.STORY_TEST_LANDMARK_NAME_1, StoryTestConstants.STORY_TEST_FULL_STRING, StoryTestConstants.STORY_TEST_FULL_STRING));
        testStory.addLandmark(new Landmark(StoryTestConstants.STORY_TEST_LANDMARK_ID_2, StoryTestConstants.STORY_TEST_LANDMARK_NAME_2, StoryTestConstants.STORY_TEST_NULL_STRING, StoryTestConstants.STORY_TEST_NULL_STRING));
        testStory.addLandmark(new Landmark(StoryTestConstants.STORY_TEST_LANDMARK_ID_3, StoryTestConstants.STORY_TEST_LANDMARK_NAME_3, StoryTestConstants.STORY_TEST_EMPTY_STRING, StoryTestConstants.STORY_TEST_EMPTY_STRING));
        testStory.addLandmark(new Landmark(StoryTestConstants.STORY_TEST_LANDMARK_ID_4, StoryTestConstants.STORY_TEST_LANDMARK_NAME_4, StoryTestConstants.STORY_TEST_BLANK_STRING, StoryTestConstants.STORY_TEST_BLANK_STRING));

        projectRepository.save(testStory);

        Story fetchedStory = projectRepository.findStoryById(testStory.getId());

        Landmark testLandmarkOne = fetchedStory.getLandmarks().get(StoryTestConstants.STORY_TEST_LANDMARK_ID_1);
        Landmark testLandmarkTwo = fetchedStory.getLandmarks().get(StoryTestConstants.STORY_TEST_LANDMARK_ID_2);
        Landmark testLandmarkThree = fetchedStory.getLandmarks().get(StoryTestConstants.STORY_TEST_LANDMARK_ID_3);
        Landmark testLandmarkFour = fetchedStory.getLandmarks().get(StoryTestConstants.STORY_TEST_LANDMARK_ID_4);

        assertAll(() -> {
            assertNotNull(testLandmarkOne);
            assertEquals(StoryTestConstants.STORY_TEST_LANDMARK_ID_1, testLandmarkOne.getLandmarkId());
            assertEquals(StoryTestConstants.STORY_TEST_LANDMARK_NAME_1, testLandmarkOne.getLandmarkName());
            assertEquals(StoryTestConstants.STORY_TEST_FULL_STRING, testLandmarkOne.getDescription());
            assertEquals(StoryTestConstants.STORY_TEST_FULL_STRING, testLandmarkOne.getImageUrl());

            assertNotNull(testLandmarkTwo);
            assertEquals(StoryTestConstants.STORY_TEST_LANDMARK_ID_2, testLandmarkTwo.getLandmarkId());
            assertEquals(StoryTestConstants.STORY_TEST_LANDMARK_NAME_2, testLandmarkTwo.getLandmarkName());
            assertEquals(StoryDefaultContents.STORY_LANDMARK_DESC_DEFAULT_CONTENT, testLandmarkTwo.getDescription());
            assertEquals(StoryDefaultContents.STORY_LANDMARK_IMAGE_URL_DEFAULT_CONTENT, testLandmarkTwo.getImageUrl());

            assertNotNull(testLandmarkThree);
            assertEquals(StoryTestConstants.STORY_TEST_LANDMARK_ID_3, testLandmarkThree.getLandmarkId());
            assertEquals(StoryTestConstants.STORY_TEST_LANDMARK_NAME_3, testLandmarkThree.getLandmarkName());
            assertEquals(StoryDefaultContents.STORY_LANDMARK_DESC_DEFAULT_CONTENT, testLandmarkThree.getDescription());
            assertEquals(StoryDefaultContents.STORY_LANDMARK_IMAGE_URL_DEFAULT_CONTENT, testLandmarkThree.getImageUrl());

            assertNotNull(testLandmarkFour);
            assertEquals(StoryTestConstants.STORY_TEST_LANDMARK_ID_4, testLandmarkFour.getLandmarkId());
            assertEquals(StoryTestConstants.STORY_TEST_LANDMARK_NAME_4, testLandmarkFour.getLandmarkName());
            assertEquals(StoryDefaultContents.STORY_LANDMARK_DESC_DEFAULT_CONTENT, testLandmarkFour.getDescription());
            assertEquals(StoryDefaultContents.STORY_LANDMARK_IMAGE_URL_DEFAULT_CONTENT, testLandmarkFour.getImageUrl());
        });

        fetchedStory.editLandmark(StoryTestConstants.STORY_TEST_LANDMARK_ID_1, StoryTestConstants.STORY_TEST_LANDMARK_NAME_2, StoryTestConstants.STORY_TEST_NULL_STRING);
        fetchedStory.changeLandmarkImage(StoryTestConstants.STORY_TEST_LANDMARK_ID_1, StoryTestConstants.STORY_TEST_NULL_STRING);

        Landmark testEditedLandmarkOne = fetchedStory.getLandmarks().get(StoryTestConstants.STORY_TEST_LANDMARK_ID_1);

        assertAll(() -> {
            assertEquals(StoryTestConstants.STORY_TEST_LANDMARK_NAME_2, testEditedLandmarkOne.getLandmarkName());
            assertEquals(StoryDefaultContents.STORY_LANDMARK_DESC_DEFAULT_CONTENT, testEditedLandmarkOne.getDescription());
            assertEquals(StoryDefaultContents.STORY_LANDMARK_IMAGE_URL_DEFAULT_CONTENT, testEditedLandmarkOne.getImageUrl());
        });

        fetchedStory.deleteLandmark(StoryTestConstants.STORY_TEST_LANDMARK_ID_1);

        projectRepository.save(fetchedStory);

        Story fetchedStory2 = projectRepository.findStoryById(fetchedStory.getId());

        assertNull(fetchedStory2.getLandmarks().get(StoryTestConstants.STORY_TEST_LANDMARK_ID_1));
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
        return new Story(StoryTestConstants.STORY_TEST_TITLE, ProjectTypes.Story, null, null, null, StoryTestConstants.STORY_TEST_OWNER_ID);
    }
}
