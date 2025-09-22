package com.sKribble.api.dto.output.common;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.sKribble.api.dto.output.song.SongOutput;
import com.sKribble.api.dto.output.story.StoryOutput;

@JsonTypeInfo(
    use = Id.CLASS  
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = StoryOutput.class),
    @JsonSubTypes.Type(value = SongOutput.class)
})
public interface ProjectOutput {}
