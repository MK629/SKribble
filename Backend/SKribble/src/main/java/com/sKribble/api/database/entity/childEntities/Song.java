package com.sKribble.api.database.entity.childEntities;

import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.annotation.TypeAlias;

import com.sKribble.api.database.entity.Project;
import com.sKribble.api.database.entity.defaults.SongDefaultContents;
import com.sKribble.api.database.entity.enums.ProjectTypes;
import com.sKribble.api.database.entity.enums.SongGenres;
import com.sKribble.api.utils.StringCheckerUtil;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TypeAlias("Song")
public class Song extends Project{

    private String title;

    private final ProjectTypes type;

    private SongGenres genre;

    private String lyrics;

    private String sheetMusicImageUrl;

    @PersistenceCreator
    public Song(String title, ProjectTypes type, SongGenres genre, String lyrics, String sheetMusicImageUrl, String ownerId){
        super(ownerId);
        this.title = title;
        this.type = type;
        this.genre = genre;
        this.lyrics = StringCheckerUtil.isNotHollow(lyrics) ? lyrics : SongDefaultContents.SONG_DEFAULT_CONTENT;
        this.sheetMusicImageUrl = StringCheckerUtil.isNotHollow(sheetMusicImageUrl) ? sheetMusicImageUrl : SongDefaultContents.SONG_SHEET_MUSIC_URL_DEFAULT_CONTENT;
    }

    public void editSong(String title, String lyrics){
        this.title = title;
        this.lyrics = StringCheckerUtil.isNotHollow(lyrics) ? lyrics : SongDefaultContents.SONG_DEFAULT_CONTENT;
    }

    public void changeSongSheetMusicImage(String sheetMusicImageUrl){
        this.sheetMusicImageUrl = StringCheckerUtil.isNotHollow(sheetMusicImageUrl) ? sheetMusicImageUrl : SongDefaultContents.SONG_SHEET_MUSIC_URL_DEFAULT_CONTENT;
    }

    public void changeGenre(SongGenres newGenre){
        this.genre = newGenre;
    }
}
