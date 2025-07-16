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
            assertEquals(testChapterOne.getChapterNumber(), StoryTestConstants.STORY_TEST_CHAPTER_NUMBER_1);
            assertEquals(testChapterOne.getChapterName(), StoryTestConstants.STORY_TEST_CHAPTER_NAME_1);
            assertEquals(testChapterOne.getText(), StoryTestConstants.STORY_TEST_FULL_STRING);

            assertNotNull(testChapterTwo);
            assertEquals(testChapterTwo.getChapterNumber(), StoryTestConstants.STORY_TEST_CHAPTER_NUMBER_2);
            assertEquals(testChapterTwo.getChapterName(), StoryTestConstants.STORY_TEST_CHAPTER_NAME_2);
            assertEquals(testChapterTwo.getText(), StoryDefaultContents.STORY_CHAPTER_DEFAULT_CONTENT);

            assertNotNull(testChapterThree);
            assertEquals(testChapterThree.getChapterNumber(), StoryTestConstants.STORY_TEST_CHAPTER_NUMBER_3);
            assertEquals(testChapterThree.getChapterName(), StoryTestConstants.STORY_TEST_CHAPTER_NAME_3);
            assertEquals(testChapterThree.getText(), StoryDefaultContents.STORY_CHAPTER_DEFAULT_CONTENT);

            assertNotNull(testChapterFour);
            assertEquals(testChapterFour.getChapterNumber(), StoryTestConstants.STORY_TEST_CHAPTER_NUMBER_4);
            assertEquals(testChapterFour.getChapterName(), StoryTestConstants.STORY_TEST_CHAPTER_NAME_4);
            assertEquals(testChapterFour.getText(), StoryDefaultContents.STORY_CHAPTER_DEFAULT_CONTENT);
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
            assertEquals(testCharacterOne.getCharacterId(), StoryTestConstants.STORY_TEST_CHARACTER_ID_1);
            assertEquals(testCharacterOne.getCharacterName(), StoryTestConstants.STORY_TEST_CHARACTER_NAME_1);
            assertEquals(testCharacterOne.getDescription(), StoryTestConstants.STORY_TEST_FULL_STRING);
            assertEquals(testCharacterOne.getImageUrl(), StoryTestConstants.STORY_TEST_FULL_STRING);

            assertNotNull(testCharacterTwo);
            assertEquals(testCharacterTwo.getCharacterId(), StoryTestConstants.STORY_TEST_CHARACTER_ID_2);
            assertEquals(testCharacterTwo.getCharacterName(), StoryTestConstants.STORY_TEST_CHARACTER_NAME_2);
            assertEquals(testCharacterTwo.getDescription(), StoryDefaultContents.STORY_CHARACTER_DESC_DEFAULT_CONTENT);
            assertEquals(testCharacterTwo.getImageUrl(), StoryDefaultContents.STORY_CHARACTER_IMAGE_URL_DEFAULT_CONTENT);

            assertNotNull(testCharacterThree);
            assertEquals(testCharacterThree.getCharacterId(), StoryTestConstants.STORY_TEST_CHARACTER_ID_3);
            assertEquals(testCharacterThree.getCharacterName(), StoryTestConstants.STORY_TEST_CHARACTER_NAME_3);
            assertEquals(testCharacterThree.getDescription(), StoryDefaultContents.STORY_CHARACTER_DESC_DEFAULT_CONTENT);
            assertEquals(testCharacterThree.getImageUrl(), StoryDefaultContents.STORY_CHARACTER_IMAGE_URL_DEFAULT_CONTENT);

            assertNotNull(testCharacterFour);
            assertEquals(testCharacterFour.getCharacterId(), StoryTestConstants.STORY_TEST_CHARACTER_ID_4);
            assertEquals(testCharacterFour.getCharacterName(), StoryTestConstants.STORY_TEST_CHARACTER_NAME_4);
            assertEquals(testCharacterFour.getDescription(), StoryDefaultContents.STORY_CHARACTER_DESC_DEFAULT_CONTENT);
            assertEquals(testCharacterFour.getImageUrl(), StoryDefaultContents.STORY_CHARACTER_IMAGE_URL_DEFAULT_CONTENT);
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
            assertEquals(testLandmarkOne.getLandmarkId(), StoryTestConstants.STORY_TEST_LANDMARK_ID_1);
            assertEquals(testLandmarkOne.getLandmarkName(), StoryTestConstants.STORY_TEST_LANDMARK_NAME_1);
            assertEquals(testLandmarkOne.getDescription(), StoryTestConstants.STORY_TEST_FULL_STRING);
            assertEquals(testLandmarkOne.getImageUrl(), StoryTestConstants.STORY_TEST_FULL_STRING);

            assertNotNull(testLandmarkTwo);
            assertEquals(testLandmarkTwo.getLandmarkId(), StoryTestConstants.STORY_TEST_LANDMARK_ID_2);
            assertEquals(testLandmarkTwo.getLandmarkName(), StoryTestConstants.STORY_TEST_LANDMARK_NAME_2);
            assertEquals(testLandmarkTwo.getDescription(), StoryDefaultContents.STORY_LANDMARK_DESC_DEFAULT_CONTENT);
            assertEquals(testLandmarkTwo.getImageUrl(), StoryDefaultContents.STORY_LANDMARK_IMAGE_URL_DEFAULT_CONTENT);

            assertNotNull(testLandmarkThree);
            assertEquals(testLandmarkThree.getLandmarkId(), StoryTestConstants.STORY_TEST_LANDMARK_ID_3);
            assertEquals(testLandmarkThree.getLandmarkName(), StoryTestConstants.STORY_TEST_LANDMARK_NAME_3);
            assertEquals(testLandmarkThree.getDescription(), StoryDefaultContents.STORY_LANDMARK_DESC_DEFAULT_CONTENT);
            assertEquals(testLandmarkThree.getImageUrl(), StoryDefaultContents.STORY_LANDMARK_IMAGE_URL_DEFAULT_CONTENT);

            assertNotNull(testLandmarkFour);
            assertEquals(testLandmarkFour.getLandmarkId(), StoryTestConstants.STORY_TEST_LANDMARK_ID_4);
            assertEquals(testLandmarkFour.getLandmarkName(), StoryTestConstants.STORY_TEST_LANDMARK_NAME_4);
            assertEquals(testLandmarkFour.getDescription(), StoryDefaultContents.STORY_LANDMARK_DESC_DEFAULT_CONTENT);
            assertEquals(testLandmarkFour.getImageUrl(), StoryDefaultContents.STORY_LANDMARK_IMAGE_URL_DEFAULT_CONTENT);
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
