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
import com.sKribble.api.dto.input.story.AddLandmarkForm;
import com.sKribble.api.dto.input.story.ChangeCharacterImageForm;
import com.sKribble.api.dto.input.story.ChangeLandmarkImageForm;
import com.sKribble.api.dto.input.story.ChangeStoryTitleForm;
import com.sKribble.api.dto.input.story.DeleteChapterForm;
import com.sKribble.api.dto.input.story.DeleteCharacterForm;
import com.sKribble.api.dto.input.story.DeleteLandmarkForm;
import com.sKribble.api.dto.input.story.EditChapterForm;
import com.sKribble.api.dto.input.story.EditCharacterForm;
import com.sKribble.api.dto.input.story.EditLandmarkForm;
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

        DeleteChapterForm badDeleteChapterForm = new DeleteChapterForm(storyOutput.id(), 69);
        assertThrows(ContentNotFoundException.class, () -> sKribbleStoryService.deleteChapter(badDeleteChapterForm));
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

        StoryOutput storyOutput2 = sKribbleStoryService.findStoriesByTitle(makeStoryTitleInput(StoryTestConstants.STORY_TEST_TITLE)).get(0);

        assertFalse(storyOutput2.characters().isEmpty());

        EditCharacterForm editCharacterForm = new EditCharacterForm(storyOutput2.id(), storyOutput2.characters().get(0).getCharacterId(), StoryTestConstants.STORY_TEST_CHARACTER_NAME_2, StoryTestConstants.STORY_TEST_NULL_STRING);
        assertEquals(CRUDSuccessMessages.CHARACTER_EDIT_SUCCESS, sKribbleStoryService.editCharacter(editCharacterForm));

        EditCharacterForm badEditCharacterForm = new EditCharacterForm(storyOutput2.id(), "fklsjclkascnadjskj", StoryTestConstants.STORY_TEST_CHARACTER_NAME_2, StoryTestConstants.STORY_TEST_NULL_STRING);
        assertThrows(ContentNotFoundException.class, () -> sKribbleStoryService.editCharacter(badEditCharacterForm));

        ChangeCharacterImageForm changeCharacterImageForm = new ChangeCharacterImageForm(storyOutput2.id(), storyOutput2.characters().get(0).getCharacterId(), StoryTestConstants.STORY_TEST_FULL_STRING);
        assertEquals(CRUDSuccessMessages.CHARACTER_IMAGE_UPLOAD_SUCCESS, sKribbleStoryService.changeCharacterImage(changeCharacterImageForm));

        ChangeCharacterImageForm badChangeCharacterImageForm = new ChangeCharacterImageForm(storyOutput2.id(), "LKHdIGSbcNCNdkaslkjdask", StoryTestConstants.STORY_TEST_NULL_STRING);
        assertThrows(ContentNotFoundException.class, () -> sKribbleStoryService.changeCharacterImage(badChangeCharacterImageForm));

        DeleteCharacterForm deleteCharacterForm = new DeleteCharacterForm(storyOutput2.id(), storyOutput2.characters().get(0).getCharacterId());
        assertEquals(CRUDSuccessMessages.CHARACTER_DELETE_SUCCESS, sKribbleStoryService.deleteCharacter(deleteCharacterForm));

        DeleteCharacterForm badDeleteCharacterForm = new DeleteCharacterForm(storyOutput2.id(), "XbkkjHJEGRFvcKSDjhas");
        assertThrows(ContentNotFoundException.class, () -> sKribbleStoryService.deleteCharacter(badDeleteCharacterForm));
    }

    @Test
    @Order(5)
    void StoryLandmarkOperationsTest(){
        mockLogin(UserTestConstants.TEST_USERNAME);

        NewStoryForm newStoryForm = new NewStoryForm(StoryTestConstants.STORY_TEST_TITLE);
        sKribbleStoryService.newStory(newStoryForm);

        StoryOutput storyOutput = sKribbleStoryService.findStoriesByTitle(makeStoryTitleInput(StoryTestConstants.STORY_TEST_TITLE)).get(0);

        AddLandmarkForm addLandmarkForm = new AddLandmarkForm(storyOutput.id(), StoryTestConstants.STORY_TEST_LANDMARK_NAME_1, StoryTestConstants.STORY_TEST_FULL_STRING, StoryTestConstants.STORY_TEST_NULL_STRING);
        AddLandmarkForm badAddLandmarkForm = new AddLandmarkForm("dkasjflkcajsfaslcksamclkjfoipew", StoryTestConstants.STORY_TEST_LANDMARK_NAME_2, StoryTestConstants.STORY_TEST_FULL_STRING, StoryTestConstants.STORY_TEST_NULL_STRING);

        mockLogin(UserTestConstants.TEST_DIFFERENT_USERNAME);
        assertThrows(AssetNotOwnedException.class, () -> sKribbleStoryService.newLandmark(addLandmarkForm));

        mockLogin(UserTestConstants.TEST_USERNAME);
        assertThrows(ProjectNotFoundException.class, () -> sKribbleStoryService.newLandmark(badAddLandmarkForm));
        assertEquals(CRUDSuccessMessages.LANDMARK_CREATION_SUCCESS, sKribbleStoryService.newLandmark(addLandmarkForm));

        StoryOutput storyOutput2 = sKribbleStoryService.findStoriesByTitle(makeStoryTitleInput(StoryTestConstants.STORY_TEST_TITLE)).get(0);

        assertFalse(storyOutput2.landmarks().isEmpty());

        EditLandmarkForm editLandmarkForm = new EditLandmarkForm(storyOutput2.id(), storyOutput2.landmarks().get(0).getLandmarkId(), StoryTestConstants.STORY_TEST_LANDMARK_NAME_2, StoryTestConstants.STORY_TEST_FULL_STRING);
        assertEquals(CRUDSuccessMessages.LANDMARK_EDIT_SUCCESS, sKribbleStoryService.editLandmark(editLandmarkForm));

        EditLandmarkForm badEditLandmarkForm = new EditLandmarkForm(storyOutput2.id(), "fsalkdjcacavghdfehgjf", StoryTestConstants.STORY_TEST_LANDMARK_NAME_2, StoryTestConstants.STORY_TEST_FULL_STRING);
        assertThrows(ContentNotFoundException.class, () -> sKribbleStoryService.editLandmark(badEditLandmarkForm));

        ChangeLandmarkImageForm changeLandmarkImageForm = new ChangeLandmarkImageForm(storyOutput2.id(), storyOutput2.landmarks().get(0).getLandmarkId(), StoryTestConstants.STORY_TEST_FULL_STRING);
        assertEquals(CRUDSuccessMessages.LANDMARK_IMAGE_UPLOAD_SUCCESS, sKribbleStoryService.changeLandmarkImage(changeLandmarkImageForm));

        ChangeLandmarkImageForm badChangeLandmarkImageForm = new ChangeLandmarkImageForm(storyOutput2.id(), "khsdjkhchkjbaskcjqiuh", StoryTestConstants.STORY_TEST_FULL_STRING);
        assertThrows(ContentNotFoundException.class, () -> sKribbleStoryService.changeLandmarkImage(badChangeLandmarkImageForm));

        DeleteLandmarkForm deleteLandmarkForm = new DeleteLandmarkForm(storyOutput2.id(), storyOutput2.landmarks().get(0).getLandmarkId());
        assertEquals(CRUDSuccessMessages.LANDMARK_DELETE_SUCCESS, sKribbleStoryService.deleteLandmark(deleteLandmarkForm));

        DeleteLandmarkForm badDeleteLandmarkForm = new DeleteLandmarkForm(storyOutput2.id(), "mklqsjcsancbaseghkbkhiu");
        assertThrows(ContentNotFoundException.class, () -> sKribbleStoryService.deleteLandmark(badDeleteLandmarkForm));
    }

    /**
     * Very important test.
     * Like, super-duper important.
     */
    @Test
    @Order(6)
    void mentionedContentInsideChaptersTest(){
        
    }

    private StoryTitleInput makeStoryTitleInput(String storyTitle){
        return new StoryTitleInput(storyTitle);
    }
}
