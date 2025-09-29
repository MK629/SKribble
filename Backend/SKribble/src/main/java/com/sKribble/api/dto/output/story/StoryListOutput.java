package com.sKribble.api.dto.output.story;

import java.util.List;

public record StoryListOutput(
    List<StoryOutput> storyList,
    Boolean hasNext
){}
