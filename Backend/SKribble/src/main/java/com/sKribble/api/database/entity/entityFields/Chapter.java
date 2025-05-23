package com.sKribble.api.database.entity.entityFields;

import org.springframework.data.annotation.PersistenceCreator;

import com.sKribble.api.database.entity.constants.DefaultContents;
import com.sKribble.api.utils.StringCheckerUtil;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Chapter {

    private Integer chapterNumber;

    private String chapterName;

    private String text;

    @PersistenceCreator
    public Chapter(Integer chapterNumber, String chapterName, String text){
        this.chapterNumber = chapterNumber;
        this.chapterName = chapterName;
        this.text = StringCheckerUtil.isNotHollow(text) ? text : DefaultContents.STORY_CHAPTER_DEFAULT_CONTENT;
    }
}
