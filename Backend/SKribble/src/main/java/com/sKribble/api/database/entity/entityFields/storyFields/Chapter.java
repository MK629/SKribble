package com.sKribble.api.database.entity.entityFields.storyFields;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.annotation.Transient;

import com.sKribble.api.database.entity.defaults.StoryDefaultContents;
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

    @Transient
    private List<Landmark> mentionedLandmarks;

    @PersistenceCreator
    public Chapter(Integer chapterNumber, String chapterName, String text){
        this.chapterNumber = chapterNumber;
        this.chapterName = chapterName;
        this.text = StringCheckerUtil.isNotHollow(text) ? text : StoryDefaultContents.STORY_CHAPTER_DEFAULT_CONTENT;
    }

    //Very cool function. A custom algorithm, kind of... Not very efficient tho..
    public void listMentionedCharacters(List<StoryCharacter> allCharacters){
        if(this.mentionedCharacters == null){
            this.mentionedCharacters = new ArrayList<>();
        }

        //Track if a character is already added via a full name mention.
        Map<String, Boolean> inconclusivityTracker = new HashMap<>();

        String chapterContent = this.text.toLowerCase();

        for(StoryCharacter character: allCharacters){
            String firstName = character.getCharacterName().split(" ")[0].toLowerCase();
            String fullName = character.getCharacterName().toLowerCase();

            Pattern firstNamePattern = Pattern.compile("\\b" + Pattern.quote(firstName) + "\\b", Pattern.CASE_INSENSITIVE);
            Pattern fullNamePattern = Pattern.compile("\\b" + Pattern.quote(fullName) + "\\b", Pattern.CASE_INSENSITIVE);

            //If a character is mentioned by a full name.
            //(Sometimes, when characters might share the same first names, this full-name mention detection eliminates all ambigiously mentioned characters.)
            if(fullNamePattern.matcher(chapterContent).find()){
                Boolean inconclusive = inconclusivityTracker.getOrDefault(firstName, true);

                if(inconclusive){
                    //Remove previously, inconclusively added characters with the same first names.
                    mentionedCharacters.removeIf((c) -> c.getCharacterName().split(" ")[0].equalsIgnoreCase(firstName));
                }

                this.mentionedCharacters.add(character);

                //Claim this first name and disallow further first-name mentions to add characters.
                inconclusivityTracker.put(firstName, false);
            } 
            //Fallback if only first names were used. (This is actually a default since characters are usually mentioned by first names.)
            else if(firstNamePattern.matcher(chapterContent).find()){
                Boolean inconclusive = inconclusivityTracker.getOrDefault(firstName, true);

                //When nobody's been conclusively mentioned yet.
                if(inconclusive){
                    this.mentionedCharacters.add(character);
                    inconclusivityTracker.put(firstName, true);
                }
            }
        }
    }

    public void listMentionedLandmarks(List<Landmark> allLandmarks){
        if(this.mentionedLandmarks == null){
            this.mentionedLandmarks = new ArrayList<>();
        }

        String chapterContent = this.text.toLowerCase();

        for(Landmark landmark : allLandmarks){
            String landmarkName = landmark.getLandmarkName().toLowerCase();

            Pattern landmarkNamePatern = Pattern.compile("\\b" + Pattern.quote(landmarkName) + "\\b", Pattern.CASE_INSENSITIVE);

            if(landmarkNamePatern.matcher(chapterContent).find()){
                this.mentionedLandmarks.add(landmark);
            }
        }
    }
}
