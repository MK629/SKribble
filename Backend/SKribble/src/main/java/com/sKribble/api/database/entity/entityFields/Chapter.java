package com.sKribble.api.database.entity.entityFields;

import org.springframework.data.annotation.PersistenceCreator;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Chapter {

    Integer chapterNumber;

    String chapterName;

    String text;

    @PersistenceCreator
    public Chapter(Integer chapterNumber, String chapterName, String text){
        this.chapterNumber = chapterNumber;
        this.chapterName = chapterName;
        this.text = text;
    }
}
