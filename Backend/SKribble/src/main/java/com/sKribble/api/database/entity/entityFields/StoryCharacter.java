package com.sKribble.api.database.entity.entityFields;
import org.springframework.data.annotation.PersistenceCreator;

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
        this.description = description;
    }
}
