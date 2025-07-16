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
            assertEquals(fetchedRockSong.getTitle(), SongTestConstants.SONG_TEST_TITLE_ROCK);
            assertEquals(fetchedRockSong.getType(), ProjectTypes.Song);
            assertEquals(fetchedRockSong.getGenre(), SongTestConstants.SONG_TEST_GENRE_ROCK);
            assertEquals(fetchedRockSong.getLyrics(), SongTestConstants.SONG_TEST_FULL_STRING);
            assertEquals(fetchedRockSong.getSheetMusicImageUrl(), SongTestConstants.SONG_TEST_FULL_STRING);
            assertEquals(fetchedRockSong.getOwnerId(), SongTestConstants.SONG_TEST_OWNER_ID);

            assertEquals(fetchedCountrySong.getTitle(), SongTestConstants.SONG_TEST_TITLE_COUNTRY);
            assertEquals(fetchedCountrySong.getType(), ProjectTypes.Song);
            assertEquals(fetchedCountrySong.getGenre(), SongTestConstants.SONG_TEST_GENRE_COUNTRY);
            assertEquals(fetchedCountrySong.getLyrics(), SongDefaultContents.SONG_DEFAULT_CONTENT);
            assertEquals(fetchedCountrySong.getSheetMusicImageUrl(), SongDefaultContents.SONG_SHEET_MUSIC_URL_DEFAULT_CONTENT);
            assertEquals(fetchedCountrySong.getOwnerId(), SongTestConstants.SONG_TEST_OWNER_ID);

            assertEquals(fetchedJazzSong.getTitle(), SongTestConstants.SONG_TEST_TITLE_JAZZ);
            assertEquals(fetchedJazzSong.getType(), ProjectTypes.Song);
            assertEquals(fetchedJazzSong.getGenre(), SongTestConstants.SONG_TEST_GENRE_JAZZ);
            assertEquals(fetchedJazzSong.getLyrics(), SongDefaultContents.SONG_DEFAULT_CONTENT);
            assertEquals(fetchedJazzSong.getSheetMusicImageUrl(), SongDefaultContents.SONG_SHEET_MUSIC_URL_DEFAULT_CONTENT);
            assertEquals(fetchedJazzSong.getOwnerId(), SongTestConstants.SONG_TEST_OWNER_ID);

            assertEquals(fetchedFolkSong.getTitle(), SongTestConstants.SONG_TEST_TITLE_FOLK);
            assertEquals(fetchedFolkSong.getType(), ProjectTypes.Song);
            assertEquals(fetchedFolkSong.getGenre(), SongTestConstants.SONG_TEST_GENRE_FOLK);
            assertEquals(fetchedFolkSong.getLyrics(), SongDefaultContents.SONG_DEFAULT_CONTENT);
            assertEquals(fetchedFolkSong.getSheetMusicImageUrl(), SongDefaultContents.SONG_SHEET_MUSIC_URL_DEFAULT_CONTENT);
            assertEquals(fetchedFolkSong.getOwnerId(), SongTestConstants.SONG_TEST_OWNER_ID);
        });
    }    

    @Test
    @Order(4)
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
