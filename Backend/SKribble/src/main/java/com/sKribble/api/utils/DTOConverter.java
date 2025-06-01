package com.sKribble.api.utils;

import com.sKribble.api.database.entity.childEntities.Story;
import com.sKribble.api.dto.output.TokenCarrier;
import com.sKribble.api.dto.output.story.StoryOutput;

public class DTOConverter {

    public static StoryOutput getStoryOutput(Story story, String owner){
        return new StoryOutput(story.getId(), story.getTitle(), story.getType(), story.getChaptersAsList(), story.getCharactersAsList(), story.getLandmarksAsList(), owner);
    }

    public static TokenCarrier makeTokenCarrier(String message, String token){
        return new TokenCarrier(message, token);
    }
}
