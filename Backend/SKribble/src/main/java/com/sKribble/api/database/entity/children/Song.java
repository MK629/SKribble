package com.sKribble.api.database.entity.children;

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

    private final ProjectTypes type = ProjectTypes.Song;

    SongGenres genre;

    public Song(String name, SongGenres genre){
        super(name);
        this.genre = genre;
    }
}
