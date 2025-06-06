package com.sKribble.api.utils;

import com.sKribble.api.database.entity.childEntities.Song;
import com.sKribble.api.database.entity.childEntities.Story;
import com.sKribble.api.dto.output.song.SongOutput;
import com.sKribble.api.dto.output.story.StoryOutput;
import com.sKribble.api.dto.output.user.TokenCarrier;

public class DTOConverter {

    public static StoryOutput getStoryOutput(Story story, String owner){
        return new StoryOutput(story.getId(), story.getTitle(), story.getType(), story.getChaptersAsList(), story.getCharactersAsList(), story.getLandmarksAsList(), owner);
    }

    public static SongOutput getSongOutput(Song song, String owner){
        return new SongOutput(song.getId(), song.getTitle(), song.getType(), song.getGenre(), song.getLyrics(), song.getSheetMusicImageUrl(), owner);
    }

    public static TokenCarrier makeTokenCarrier(String message, String token){
        return new TokenCarrier(message, token);
    }
}
