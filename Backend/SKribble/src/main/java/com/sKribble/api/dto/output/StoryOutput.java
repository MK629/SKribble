package com.sKribble.api.dto.output;

import java.util.List;

import com.sKribble.api.database.entity.entityFields.Chapter;
import com.sKribble.api.database.entity.entityFields.StoryCharacter;
import com.sKribble.api.database.entity.enums.ProjectTypes;

public record StoryOutput(
    String id,
    String title,
    ProjectTypes type,
    List<Chapter> chapters,
    List<StoryCharacter> characters,
    String owner
) {} 

