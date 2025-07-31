package com.sKribble.api.CRUDTests;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;

import com.sKribble.api.constants.SongTestConstants;
import com.sKribble.api.database.entity.childEntities.Song;
import com.sKribble.api.database.entity.defaults.SongDefaultContents;
import com.sKribble.api.database.entity.enums.ProjectTypes;
import com.sKribble.api.database.repository.ProjectRepository;

@DataMongoTest
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SongCRUDTests {

    @Autowired
    private ProjectRepository projectRepository;

    @AfterEach
    void clean(){
        projectRepository.deleteAll();
    }

    @Test
    @Order(1)
    void songCreationTest(){
        Song rockSong = songInstanceRock();
        assertNotNull(projectRepository.save(rockSong)); 
    }

    @Test
    @Order(2)
    void songFetchTest(){
        Song rockSong = songInstanceRock();
        projectRepository.save(rockSong);

        assertAll(() -> {
            assertNotNull(projectRepository.findSongById(rockSong.getId()));
            assertNotNull(projectRepository.findSongsByTitle(SongTestConstants.SONG_TEST_TITLE_ROCK));
        });
    }

    @Test
    @Order(3)
    void songExpectedValuesTest(){
        Song rockSong = songInstanceRock();
        Song countrySong = songInstanceCountry();
        Song jazzSong = songInstanceJazz();
        Song folkSong = songInstanceFolk();

        projectRepository.save(rockSong);
        projectRepository.save(countrySong);
        projectRepository.save(jazzSong);
        projectRepository.save(folkSong);

        Song fetchedRockSong = projectRepository.findSongById(rockSong.getId());
        Song fetchedCountrySong = projectRepository.findSongById(countrySong.getId());
        Song fetchedJazzSong = projectRepository.findSongById(jazzSong.getId());
        Song fetchedFolkSong = projectRepository.findSongById(folkSong.getId());

        assertAll(() -> {
            assertEquals(SongTestConstants.SONG_TEST_TITLE_ROCK, fetchedRockSong.getTitle());
            assertEquals(ProjectTypes.Song, fetchedRockSong.getType());
            assertEquals(SongTestConstants.SONG_TEST_GENRE_ROCK, fetchedRockSong.getGenre());
            assertEquals(SongTestConstants.SONG_TEST_FULL_STRING, fetchedRockSong.getLyrics());
            assertEquals(SongTestConstants.SONG_TEST_FULL_STRING, fetchedRockSong.getSheetMusicImageUrl());
            assertEquals(SongTestConstants.SONG_TEST_OWNER_ID, fetchedRockSong.getOwnerId());

            assertEquals(SongTestConstants.SONG_TEST_TITLE_COUNTRY, fetchedCountrySong.getTitle());
            assertEquals(ProjectTypes.Song, fetchedCountrySong.getType());
            assertEquals(SongTestConstants.SONG_TEST_GENRE_COUNTRY, fetchedCountrySong.getGenre());
            assertEquals(SongDefaultContents.SONG_DEFAULT_CONTENT, fetchedCountrySong.getLyrics());
            assertEquals(SongDefaultContents.SONG_SHEET_MUSIC_URL_DEFAULT_CONTENT, fetchedCountrySong.getSheetMusicImageUrl());
            assertEquals(SongTestConstants.SONG_TEST_OWNER_ID, fetchedCountrySong.getOwnerId());

            assertEquals(SongTestConstants.SONG_TEST_TITLE_JAZZ, fetchedJazzSong.getTitle());
            assertEquals(ProjectTypes.Song, fetchedJazzSong.getType());
            assertEquals(SongTestConstants.SONG_TEST_GENRE_JAZZ, fetchedJazzSong.getGenre());
            assertEquals(SongDefaultContents.SONG_DEFAULT_CONTENT, fetchedJazzSong.getLyrics());
            assertEquals(SongDefaultContents.SONG_SHEET_MUSIC_URL_DEFAULT_CONTENT, fetchedJazzSong.getSheetMusicImageUrl());
            assertEquals(SongTestConstants.SONG_TEST_OWNER_ID, fetchedJazzSong.getOwnerId());

            assertEquals(SongTestConstants.SONG_TEST_TITLE_FOLK, fetchedFolkSong.getTitle());
            assertEquals(ProjectTypes.Song, fetchedFolkSong.getType());
            assertEquals(SongTestConstants.SONG_TEST_GENRE_FOLK, fetchedFolkSong.getGenre());
            assertEquals(SongDefaultContents.SONG_DEFAULT_CONTENT, fetchedFolkSong.getLyrics());
            assertEquals(SongDefaultContents.SONG_SHEET_MUSIC_URL_DEFAULT_CONTENT, fetchedFolkSong.getSheetMusicImageUrl());
            assertEquals(SongTestConstants.SONG_TEST_OWNER_ID, fetchedFolkSong.getOwnerId());
        });
    }
    
    @Test
    @Order(4)
    void songEditTest(){
        Song rockSong = songInstanceRock();
        projectRepository.save(rockSong);

        Song fetchedRockSong = projectRepository.findSongById(rockSong.getId());
        fetchedRockSong.editSong(SongTestConstants.SONG_TEST_TITLE_JAZZ, SongTestConstants.SONG_TEST_NULL_STRING);
        fetchedRockSong.changeSongSheetMusicImage(SongTestConstants.SONG_TEST_NULL_STRING);
        fetchedRockSong.changeGenre(SongTestConstants.SONG_TEST_GENRE_JAZZ);

        assertAll(() -> {
            assertEquals(SongTestConstants.SONG_TEST_TITLE_JAZZ, fetchedRockSong.getTitle());
            assertEquals(SongDefaultContents.SONG_DEFAULT_CONTENT, fetchedRockSong.getLyrics());
            assertEquals(SongDefaultContents.SONG_SHEET_MUSIC_URL_DEFAULT_CONTENT, fetchedRockSong.getSheetMusicImageUrl());
            assertEquals(SongTestConstants.SONG_TEST_GENRE_JAZZ, fetchedRockSong.getGenre());
        });
    }

    @Test
    @Order(5)
    void songDeletionTest(){
        Song rockSong = songInstanceRock();

        projectRepository.save(rockSong);
        assertNotNull(projectRepository.findSongById(rockSong.getId()));

        projectRepository.deleteById(rockSong.getId());
        assertNull(projectRepository.findSongById(rockSong.getId()));
    }

    @AfterAll
    void cleanup(){
        projectRepository.deleteAll();
    }

    private Song songInstanceRock(){
        return new Song(SongTestConstants.SONG_TEST_TITLE_ROCK, ProjectTypes.Song, SongTestConstants.SONG_TEST_GENRE_ROCK, SongTestConstants.SONG_TEST_FULL_STRING, SongTestConstants.SONG_TEST_FULL_STRING, SongTestConstants.SONG_TEST_OWNER_ID);
    }

    private Song songInstanceCountry(){
        return new Song(SongTestConstants.SONG_TEST_TITLE_COUNTRY, ProjectTypes.Song, SongTestConstants.SONG_TEST_GENRE_COUNTRY, SongTestConstants.SONG_TEST_NULL_STRING, SongTestConstants.SONG_TEST_NULL_STRING, SongTestConstants.SONG_TEST_OWNER_ID);
    }

    private Song songInstanceJazz(){
        return new Song(SongTestConstants.SONG_TEST_TITLE_JAZZ, ProjectTypes.Song, SongTestConstants.SONG_TEST_GENRE_JAZZ, SongTestConstants.SONG_TEST_EMPTY_STRING, SongTestConstants.SONG_TEST_EMPTY_STRING, SongTestConstants.SONG_TEST_OWNER_ID);
    }

    private Song songInstanceFolk(){
        return new Song(SongTestConstants.SONG_TEST_TITLE_FOLK, ProjectTypes.Song, SongTestConstants.SONG_TEST_GENRE_FOLK, SongTestConstants.SONG_TEST_BLANK_STRING, SongTestConstants.SONG_TEST_BLANK_STRING, SongTestConstants.SONG_TEST_OWNER_ID);
    }
}
