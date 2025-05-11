package com.sKribble.api.database.entity.childEntities;

import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.annotation.TypeAlias;

import com.sKribble.api.database.entity.Project;
import com.sKribble.api.database.entity.enums.ProjectTypes;
import com.sKribble.api.database.entity.enums.SongGenres;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TypeAlias("Song")
public class Song extends Project{

    private String name;

    private final ProjectTypes type;

    private SongGenres genre;

    private String lyrics;

    @PersistenceCreator
    public Song(String name, ProjectTypes type, SongGenres genre, String lyrics, String author){
        super(author);
        this.name = name;
        this.type = type;
        this.genre = genre;
        this.lyrics = lyrics;
    }
}
