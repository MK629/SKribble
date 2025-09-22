package com.sKribble.api.dto.output.story;

import java.util.List;

import com.sKribble.api.database.entity.entityFields.storyFields.Chapter;
import com.sKribble.api.database.entity.entityFields.storyFields.Landmark;
import com.sKribble.api.database.entity.entityFields.storyFields.StoryCharacter;
import com.sKribble.api.database.entity.enums.ProjectTypes;
import com.sKribble.api.dto.output.common.ProjectOutput;

public record StoryOutput(
    String id,
    String title,
    ProjectTypes type,
    List<Chapter> chapters,
    List<StoryCharacter> characters,
    List<Landmark> landmarks,
    String owner
) implements ProjectOutput {} 

