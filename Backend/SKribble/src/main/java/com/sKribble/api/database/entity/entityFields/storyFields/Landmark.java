package com.sKribble.api.database.entity.entityFields.storyFields;

import org.springframework.data.annotation.PersistenceCreator;

import com.sKribble.api.database.entity.defaults.StoryDefaultContents;
import com.sKribble.api.utils.StringCheckerUtil;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Landmark {

    private String landmarkId;

    private String landmarkName;

    private String description;

    private String imageUrl;

    @PersistenceCreator
    public Landmark(String landmarkId, String landmarkName, String description, String imageUrl){
        this.landmarkId = landmarkId;
        this.landmarkName = landmarkName;
        this.description = StringCheckerUtil.isNotHollow(description) ? description: StoryDefaultContents.STORY_LANDMARK_DESC_DEFAULT_CONTENT;
        this.imageUrl = StringCheckerUtil.isNotHollow(imageUrl) ? imageUrl: StoryDefaultContents.STORY_LANDMARK_IMAGE_URL_DEFAULT_CONTENT;
    }
}
