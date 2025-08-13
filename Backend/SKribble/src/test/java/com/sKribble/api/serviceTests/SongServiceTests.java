package com.sKribble.api.serviceTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.sKribble.api.constants.SongTestConstants;
import com.sKribble.api.constants.UserTestConstants;
import com.sKribble.api.dto.input.song.ChangeSongGenreForm;
import com.sKribble.api.dto.input.song.ChangeSongSheetMusicImageForm;
import com.sKribble.api.dto.input.song.EditSongForm;
import com.sKribble.api.dto.input.song.NewSongForm;
import com.sKribble.api.dto.input.song.SongTitleInput;
import com.sKribble.api.dto.output.song.SongOutput;
import com.sKribble.api.error.exceptions.CRUDExceptions.AssetNotOwnedException;
import com.sKribble.api.error.exceptions.CRUDExceptions.IllogicalNullException;
import com.sKribble.api.messages.successMessages.CRUDSuccessMessages;
import com.sKribble.api.service.SKribbleSongService;
import com.sKribble.api.templates.SKribbleServiceTestTemplate;

public class SongServiceTests extends SKribbleServiceTestTemplate{

    @Autowired
    protected SKribbleSongService sKribbleSongService;

    @Test
    @Order(1)
    void createSongTest(){
        NewSongForm newSongForm = new NewSongForm(SongTestConstants.SONG_TEST_TITLE_ROCK, SongTestConstants.SONG_TEST_GENRE_ROCK, SongTestConstants.SONG_TEST_FULL_STRING, SongTestConstants.SONG_TEST_FULL_STRING);

        //Without any users logged in
        assertThrows(IllogicalNullException.class, () -> sKribbleSongService.newSong(newSongForm));

        mockLogin(UserTestConstants.TEST_USERNAME);

        //After logging in
        assertEquals(CRUDSuccessMessages.SONG_CREATION_SUCCESS, sKribbleSongService.newSong(newSongForm));
        assertNotNull(sKribbleSongService.findSongsByTitle(makeSongTitleInput(SongTestConstants.SONG_TEST_TITLE_ROCK)));
    }

    @Test
    @Order(2)
    void editSongAndChangeGenreTest(){
        mockLogin(UserTestConstants.TEST_USERNAME);

        NewSongForm newSongForm = new NewSongForm(SongTestConstants.SONG_TEST_TITLE_ROCK, SongTestConstants.SONG_TEST_GENRE_ROCK, SongTestConstants.SONG_TEST_FULL_STRING, SongTestConstants.SONG_TEST_FULL_STRING);

        sKribbleSongService.newSong(newSongForm);

        SongOutput songOutput = sKribbleSongService.findSongsByTitle(makeSongTitleInput(SongTestConstants.SONG_TEST_TITLE_ROCK)).get(0);

        assertEquals(UserTestConstants.TEST_USERNAME, songOutput.owner());

        EditSongForm editSongForm = new EditSongForm(songOutput.id(), SongTestConstants.SONG_TEST_TITLE_COUNTRY, SongTestConstants.SONG_TEST_EMPTY_STRING);
        ChangeSongSheetMusicImageForm changeSongSheetMusicImageForm = new ChangeSongSheetMusicImageForm(songOutput.id(), SongTestConstants.SONG_TEST_EMPTY_STRING);
        ChangeSongGenreForm changeSongGenreForm = new ChangeSongGenreForm(songOutput.id(), SongTestConstants.SONG_TEST_GENRE_JAZZ);

        //Login with a non-owner and try to edit/change the song data. This must not be allowed. Very bad behaviour.
        mockLogin(UserTestConstants.TEST_DIFFERENT_USERNAME);
        assertThrows(AssetNotOwnedException.class, () -> sKribbleSongService.editSong(editSongForm));
        assertThrows(AssetNotOwnedException.class, () -> sKribbleSongService.changeSongSheetMusicImage(changeSongSheetMusicImageForm));
        assertThrows(AssetNotOwnedException.class, () -> sKribbleSongService.changeSongGenre(changeSongGenreForm));

        //Login with the actual owner of the song to be edited. This is the way. :)
        mockLogin(UserTestConstants.TEST_USERNAME);
        assertEquals(CRUDSuccessMessages.SONG_EDIT_SUCCESS, sKribbleSongService.editSong(editSongForm));
        assertEquals(CRUDSuccessMessages.SONG_SHEET_MUSIC_IMAGE_UPLOAD_SUCCESS, sKribbleSongService.changeSongSheetMusicImage(changeSongSheetMusicImageForm));
        assertEquals(CRUDSuccessMessages.SONG_GENRE_CHANGE_SUCCESS, sKribbleSongService.changeSongGenre(changeSongGenreForm));
    }

    private SongTitleInput makeSongTitleInput(String title){
        return new SongTitleInput(title);
    }
}
