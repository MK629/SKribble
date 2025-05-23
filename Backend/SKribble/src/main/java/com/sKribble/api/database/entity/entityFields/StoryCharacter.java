package com.sKribble.api.database.entity.entityFields;
import org.springframework.data.annotation.PersistenceCreator;

import com.sKribble.api.database.entity.constants.DefaultContents;
import com.sKribble.api.utils.StringCheckerUtil;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StoryCharacter {

    String name;

    String description;

    @PersistenceCreator
    public StoryCharacter(String name, String description){
        this.name = name;
        this.description = StringCheckerUtil.isNotHollow(description) ? description : DefaultContents.STORY_CHARACTER_DESC_DEFAULT_CONTENT;
    }
}
