package com.sKribble.api.serviceTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.sKribble.api.constants.StoryTestConstants;
import com.sKribble.api.constants.UserTestConstants;
import com.sKribble.api.dto.input.story.AddChapterForm;
import com.sKribble.api.dto.input.story.AddCharacterForm;
import com.sKribble.api.dto.input.story.ChangeStoryTitleForm;
import com.sKribble.api.dto.input.story.DeleteChapterForm;
import com.sKribble.api.dto.input.story.EditChapterForm;
import com.sKribble.api.dto.input.story.NewStoryForm;
import com.sKribble.api.dto.input.story.StoryTitleInput;
import com.sKribble.api.dto.output.story.StoryOutput;
import com.sKribble.api.error.exceptions.CRUDExceptions.AssetNotOwnedException;
import com.sKribble.api.error.exceptions.CRUDExceptions.ContentNotFoundException;
import com.sKribble.api.error.exceptions.CRUDExceptions.IllogicalNullException;
import com.sKribble.api.error.exceptions.CRUDExceptions.ProjectNotFoundException;
import com.sKribble.api.messages.successMessages.CRUDSuccessMessages;
import com.sKribble.api.service.SKribbleStoryService;
import com.sKribble.api.templates.SKribbleServiceTestTemplate;

public class StoryServiceTests extends SKribbleServiceTestTemplate{

    @Autowired
    private SKribbleStoryService sKribbleStoryService;

    @Test
    @Order(1)
    void createStoryTest(){
        NewStoryForm newStoryForm = new NewStoryForm(StoryTestConstants.STORY_TEST_TITLE);

        //Without any users logged in
        assertThrows(IllogicalNullException.class, () -> sKribbleStoryService.newStory(newStoryForm));

        mockLogin(UserTestConstants.TEST_USERNAME);

        assertEquals(CRUDSuccessMessages.STORY_CREATION_SUCCESS, sKribbleStoryService.newStory(newStoryForm));
        assertFalse(sKribbleStoryService.findStoriesByTitle(makeStoryTitleInput(StoryTestConstants.STORY_TEST_TITLE)).isEmpty());

        StoryOutput storyOutput = sKribbleStoryService.findStoriesByTitle(makeStoryTitleInput(StoryTestConstants.STORY_TEST_TITLE)).get(0);
        assertEquals(UserTestConstants.TEST_USERNAME, storyOutput.owner());
    }

    @Test
    @Order(2)
    void changeStoryTitleTest(){
        mockLogin(UserTestConstants.TEST_USERNAME);

        NewStoryForm newStoryForm = new NewStoryForm(StoryTestConstants.STORY_TEST_TITLE);
        sKribbleStoryService.newStory(newStoryForm);

        StoryOutput storyOutput = sKribbleStoryService.findStoriesByTitle(makeStoryTitleInput(StoryTestConstants.STORY_TEST_TITLE)).get(0);

        ChangeStoryTitleForm changeStoryTitleForm = new ChangeStoryTitleForm(storyOutput.id(), StoryTestConstants.STORY_TEST_TITLE2);
        ChangeStoryTitleForm badChangeStoryTitleForm = new ChangeStoryTitleForm("lkqjdksahfkjagsfhj", StoryTestConstants.STORY_TEST_TITLE2);

        //Try to change title as non-owner
        mockLogin(UserTestConstants.TEST_DIFFERENT_USERNAME);
        assertThrows(AssetNotOwnedException.class, () -> sKribbleStoryService.changeStoryTitle(changeStoryTitleForm));

        //Try to change title as actual owner
        mockLogin(UserTestConstants.TEST_USERNAME);
        assertThrows(ProjectNotFoundException.class, () -> sKribbleStoryService.changeStoryTitle(badChangeStoryTitleForm));
        assertEquals(CRUDSuccessMessages.STORY_TITLE_CHANGE_SUCCESS, sKribbleStoryService.changeStoryTitle(changeStoryTitleForm));

        assertFalse(sKribbleStoryService.findStoriesByTitle(makeStoryTitleInput(StoryTestConstants.STORY_TEST_TITLE2)).isEmpty());
    }

    @Test
    @Order(3)
    void StoryChapterOperationsTest(){
        mockLogin(UserTestConstants.TEST_USERNAME);

        NewStoryForm newStoryForm = new NewStoryForm(StoryTestConstants.STORY_TEST_TITLE);
        sKribbleStoryService.newStory(newStoryForm);

        StoryOutput storyOutput = sKribbleStoryService.findStoriesByTitle(makeStoryTitleInput(StoryTestConstants.STORY_TEST_TITLE)).get(0);

        AddChapterForm addChapterForm = new AddChapterForm(storyOutput.id(), StoryTestConstants.STORY_TEST_CHAPTER_NUMBER_1, StoryTestConstants.STORY_TEST_CHAPTER_NAME_1, StoryTestConstants.STORY_TEST_NULL_STRING);
        AddChapterForm badAddChapterForm = new AddChapterForm("ajdahjkcbscbashdka", StoryTestConstants.STORY_TEST_CHAPTER_NUMBER_2, StoryTestConstants.STORY_TEST_CHAPTER_NAME_2, StoryTestConstants.STORY_TEST_NULL_STRING);
        
        //Test with non-owner logging in
        mockLogin(UserTestConstants.TEST_DIFFERENT_USERNAME);
        assertThrows(AssetNotOwnedException.class, () -> sKribbleStoryService.newChapter(addChapterForm));

        //Test with actual owner logging in
        mockLogin(UserTestConstants.TEST_USERNAME);
        assertThrows(ProjectNotFoundException.class, () -> sKribbleStoryService.newChapter(badAddChapterForm));
        assertEquals(CRUDSuccessMessages.CHAPTER_ADD_SUCCESS, sKribbleStoryService.newChapter(addChapterForm));

        EditChapterForm editChapterForm = new EditChapterForm(storyOutput.id(), StoryTestConstants.STORY_TEST_CHAPTER_NUMBER_1, StoryTestConstants.STORY_TEST_CHAPTER_NAME_2, StoryTestConstants.STORY_TEST_FULL_STRING);
        assertEquals(CRUDSuccessMessages.CHAPTER_EDIT_SUCCESS, sKribbleStoryService.editChapter(editChapterForm));

        EditChapterForm badEditChapterForm = new EditChapterForm(storyOutput.id(), 69, StoryTestConstants.STORY_TEST_CHAPTER_NAME_2, StoryTestConstants.STORY_TEST_FULL_STRING);
        assertThrows(ContentNotFoundException.class, () -> sKribbleStoryService.editChapter(badEditChapterForm));

        DeleteChapterForm deleteChapterForm = new DeleteChapterForm(storyOutput.id(), StoryTestConstants.STORY_TEST_CHAPTER_NUMBER_1);
        assertEquals(CRUDSuccessMessages.CHAPTER_DELETE_SUCCESS, sKribbleStoryService.deleteChapter(deleteChapterForm));
    }

    @Test
    @Order(4)
    void StoryCharacterOperationsTest(){
        mockLogin(UserTestConstants.TEST_USERNAME);

        NewStoryForm newStoryForm = new NewStoryForm(StoryTestConstants.STORY_TEST_TITLE);
        sKribbleStoryService.newStory(newStoryForm);

        StoryOutput storyOutput = sKribbleStoryService.findStoriesByTitle(makeStoryTitleInput(StoryTestConstants.STORY_TEST_TITLE)).get(0);

        AddCharacterForm addCharacterForm = new AddCharacterForm(storyOutput.id(), StoryTestConstants.STORY_TEST_CHARACTER_NAME_1, StoryTestConstants.STORY_TEST_FULL_STRING, StoryTestConstants.STORY_TEST_NULL_STRING);
        AddCharacterForm badAddCharacterForm = new AddCharacterForm("dnwjkerfsbcsan;kh", StoryTestConstants.STORY_TEST_CHARACTER_NAME_2, StoryTestConstants.STORY_TEST_FULL_STRING, StoryTestConstants.STORY_TEST_NULL_STRING);

        mockLogin(UserTestConstants.TEST_DIFFERENT_USERNAME);
        assertThrows(AssetNotOwnedException.class, () -> sKribbleStoryService.newCharacter(addCharacterForm));

        mockLogin(UserTestConstants.TEST_USERNAME);
        assertThrows(ProjectNotFoundException.class, () -> sKribbleStoryService.newCharacter(badAddCharacterForm));
        assertEquals(CRUDSuccessMessages.CHARACTER_CREATION_SUCCESS, sKribbleStoryService.newCharacter(addCharacterForm));
    }

    @Test
    @Order(5)
    void StoryLandmarkOperationsTest(){

    }

    @Test
    @Order(6)
    void mentionedContentInsideChaptersTest(){

    }

    private StoryTitleInput makeStoryTitleInput(String storyTitle){
        return new StoryTitleInput(storyTitle);
    }
}
