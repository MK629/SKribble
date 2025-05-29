package com.sKribble.api.database.entity.entityFields;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.annotation.Transient;

import com.sKribble.api.database.entity.defaults.DefaultContents;
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

    //My masterstroke function
    public void listMentionedCharacters(List<StoryCharacter> allCharacters){
        if(this.mentionedCharacters == null){
            this.mentionedCharacters = new ArrayList<>();
        }

        //Track if a character is already added via a full name mention.
        Map<String, Boolean> inconclusivityTracker = new HashMap<>();

        String chapterContent = this.text.toLowerCase();

        for(StoryCharacter character: allCharacters){
            String firstName = character.getName().split(" ")[0].toLowerCase();
            String fullName = character.getName().toLowerCase();

            //If a character is mentioned by a full name...
            if(chapterContent.contains(fullName)){
                mentionedCharacters.removeIf((c) -> c.getName().split(" ")[0].equalsIgnoreCase(firstName));

                this.mentionedCharacters.add(character);

                inconclusivityTracker.put(firstName, false);
            } 
            //Fallback if only first names were used. 
            else if(chapterContent.contains(firstName)){
                Boolean inconclusive = inconclusivityTracker.getOrDefault(firstName, true);

                //However, if another character who shares an identical first name was already mentioned on a full name basis, this skips.
                if(inconclusive == true){
                    this.mentionedCharacters.add(character);
                    inconclusivityTracker.put(firstName, true);
                }
            }
        }
    }
}
