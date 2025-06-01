package com.sKribble.api.database.entity.entityFields.storyFields;

import org.springframework.data.annotation.PersistenceCreator;

import com.sKribble.api.database.entity.defaults.StoryDefaultContents;
import com.sKribble.api.utils.StringCheckerUtil;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StoryCharacter {

    private final String characterId;

    private String characterName;

    private String description;

    private String imageUrl;

    @PersistenceCreator
    public StoryCharacter(String characterId, String characterName, String description, String imageUrl){
        this.characterId = characterId;
        this.characterName = characterName;
        this.description = StringCheckerUtil.isNotHollow(description) ? description : StoryDefaultContents.STORY_CHARACTER_DESC_DEFAULT_CONTENT;
        this.imageUrl = StringCheckerUtil.isNotHollow(imageUrl) ? imageUrl : StoryDefaultContents.STORY_CHARACTER_IMAGE_URL_DEFAULT_CONTENT;
    }
}
