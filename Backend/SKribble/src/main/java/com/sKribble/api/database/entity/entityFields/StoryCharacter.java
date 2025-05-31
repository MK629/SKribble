package com.sKribble.api.database.entity.entityFields;
import org.springframework.data.annotation.PersistenceCreator;

import com.sKribble.api.database.entity.defaults.DefaultContents;
import com.sKribble.api.utils.StringCheckerUtil;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StoryCharacter {

    private String characterId;

    private String characterName;

    private String description;

    @PersistenceCreator
    public StoryCharacter(String characterId, String characterName, String description){
        this.characterId = characterId;
        this.characterName = characterName;
        this.description = StringCheckerUtil.isNotHollow(description) ? description : DefaultContents.STORY_CHARACTER_DESC_DEFAULT_CONTENT;
    }
}
