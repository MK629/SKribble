package com.sKribble.api.serviceTests;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.sKribble.api.constants.SongTestConstants;
import com.sKribble.api.constants.StoryTestConstants;
import com.sKribble.api.constants.UserTestConstants;
import com.sKribble.api.dto.input.common.ChangeOwnershipForm;
import com.sKribble.api.dto.input.song.EditSongForm;
import com.sKribble.api.dto.input.song.NewSongForm;
import com.sKribble.api.dto.input.song.SongTitleInput;
import com.sKribble.api.dto.input.story.ChangeStoryTitleForm;
import com.sKribble.api.dto.input.story.NewStoryForm;
import com.sKribble.api.dto.input.story.StoryTitleInput;
import com.sKribble.api.error.exceptions.CRUDExceptions.AssetNotOwnedException;
import com.sKribble.api.error.exceptions.CRUDExceptions.UserNotFoundException;
import com.sKribble.api.messages.successMessages.CRUDSuccessMessages;
import com.sKribble.api.service.SKribbleCommonService;
import com.sKribble.api.service.SKribbleSongService;
import com.sKribble.api.service.SKribbleStoryService;
import com.sKribble.api.templates.SKribbleServiceTestTemplate;

public class CommonServiceTests extends SKribbleServiceTestTemplate{

    @Autowired
    SKribbleCommonService sKribbleCommonService;

    @Autowired
    private SKribbleStoryService sKribbleStoryService;

    @Autowired
    private SKribbleSongService sKribbleSongService;

    
    @Test
    @Order(1)
    void changeOwnerShipTest(){
        String DifferenttestUserId = userRepository.findUserByUsernameOrEmail(UserTestConstants.TEST_DIFFERENT_USERNAME).getId();

        mockLogin(UserTestConstants.TEST_USERNAME);

        makeProjects();

        String storyId = sKribbleStoryService.findStoriesByTitle(new StoryTitleInput(StoryTestConstants.STORY_TEST_TITLE)).get(0).id();
        String songId = sKribbleSongService.findSongsByTitle(new SongTitleInput(SongTestConstants.SONG_TEST_TITLE_ROCK)).get(0).id();

        assertEquals(CRUDSuccessMessages.STORY_TITLE_CHANGE_SUCCESS, sKribbleStoryService.changeStoryTitle(new ChangeStoryTitleForm(storyId, "The Hobbit")));
        assertEquals(CRUDSuccessMessages.SONG_EDIT_SUCCESS, sKribbleSongService.editSong(new EditSongForm(songId, "I wanna rock", "can't remember the lyrics tho..")));

        //Try to change ownership of projects that the logged in user does not own.
        mockLogin(UserTestConstants.TEST_DIFFERENT_USERNAME);
        assertAll(() -> {
            assertThrows(AssetNotOwnedException.class, () -> sKribbleCommonService.changeOwnership(new ChangeOwnershipForm(storyId, DifferenttestUserId)));
            assertThrows(AssetNotOwnedException.class, () -> sKribbleCommonService.changeOwnership(new ChangeOwnershipForm(songId, DifferenttestUserId)));
        });

        //Correct user changes ownership to another user.
        mockLogin(UserTestConstants.TEST_USERNAME);

        //Try to enter random bullshit as new owner's ID.
        assertAll(() -> {
            assertThrows(UserNotFoundException.class, () -> sKribbleCommonService.changeOwnership(new ChangeOwnershipForm(storyId, "kanskjhdkjahcjkdsncksahfeiuhaiu")));
            assertThrows(UserNotFoundException.class, () -> sKribbleCommonService.changeOwnership(new ChangeOwnershipForm(songId, "182092eudiwqjkffq83u2uiojoijijkjkj")));
        });

        //Now the actual correct attempt.
        ResponseEntity<String> storyOwnerShipResponse = sKribbleCommonService.changeOwnership(new ChangeOwnershipForm(storyId, DifferenttestUserId));
        ResponseEntity<String> songOwnerShipResponse = sKribbleCommonService.changeOwnership(new ChangeOwnershipForm(songId, DifferenttestUserId));

        assertAll(() -> {
            assertEquals(HttpStatus.OK, storyOwnerShipResponse.getStatusCode());
            assertEquals(CRUDSuccessMessages.PROJECT_OWNERSHIP_CHANGE_SUCCESS + UserTestConstants.TEST_DIFFERENT_USERNAME, storyOwnerShipResponse.getBody());

            assertEquals(HttpStatus.OK, songOwnerShipResponse.getStatusCode());
            assertEquals(CRUDSuccessMessages.PROJECT_OWNERSHIP_CHANGE_SUCCESS + UserTestConstants.TEST_DIFFERENT_USERNAME, songOwnerShipResponse.getBody());
        });

        //Try to do CRUD with the old owner after handing ownership to another user.
        assertThrows(AssetNotOwnedException.class, () -> sKribbleStoryService.changeStoryTitle(new ChangeStoryTitleForm(storyId, "The Witcher")));
        assertThrows(AssetNotOwnedException.class, () -> sKribbleSongService.editSong(new EditSongForm(songId, "Welcome to the jungle", "Sha-na-na-na-na-na-na-na-na-na-na-na knees")));

        //Try to do CRUD with the new owner after being handed ownership.
        mockLogin(UserTestConstants.TEST_DIFFERENT_USERNAME);

        assertEquals(CRUDSuccessMessages.STORY_TITLE_CHANGE_SUCCESS, sKribbleStoryService.changeStoryTitle(new ChangeStoryTitleForm(storyId, "The Witcher")));
        assertEquals(CRUDSuccessMessages.SONG_EDIT_SUCCESS, sKribbleSongService.editSong(new EditSongForm(songId, "Welcome to the jungle", "Sha-na-na-na-na-na-na-na-na-na-na-na knees")));
    }

    private void makeProjects(){
        NewStoryForm newStoryForm = new NewStoryForm(StoryTestConstants.STORY_TEST_TITLE);
        NewSongForm newSongForm = new NewSongForm(SongTestConstants.SONG_TEST_TITLE_ROCK, SongTestConstants.SONG_TEST_GENRE_ROCK, SongTestConstants.SONG_TEST_FULL_STRING, SongTestConstants.SONG_TEST_FULL_STRING);

        sKribbleStoryService.newStory(newStoryForm);
        sKribbleSongService.newSong(newSongForm);
    }
}
