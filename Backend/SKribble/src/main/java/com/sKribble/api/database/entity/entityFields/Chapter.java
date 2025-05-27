package com.sKribble.api.database.entity.entityFields;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.annotation.Transient;

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

    @Transient
    private List<StoryCharacter> mentionedCharacters;

    @PersistenceCreator
    public Chapter(Integer chapterNumber, String chapterName, String text){
        this.chapterNumber = chapterNumber;
        this.chapterName = chapterName;
        this.text = StringCheckerUtil.isNotHollow(text) ? text : DefaultContents.STORY_CHAPTER_DEFAULT_CONTENT;
    }

    public void listMentionedCharacters(List<StoryCharacter> allCharacters){
        if(this.mentionedCharacters == null){
            this.mentionedCharacters = new ArrayList<>();
        }

        allCharacters.forEach((character) -> {
            if(this.text.toLowerCase().contains(character.getName().split(" ")[0].toLowerCase())){
                this.mentionedCharacters.add(character);
            }
        });
    }
}
