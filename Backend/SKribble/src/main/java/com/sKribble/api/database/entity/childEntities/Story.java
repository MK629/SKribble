package com.sKribble.api.database.entity.childEntities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.annotation.TypeAlias;

import com.sKribble.api.database.entity.Project;
import com.sKribble.api.database.entity.constants.DefaultContents;
import com.sKribble.api.database.entity.entityFields.StoryCharacter;
import com.sKribble.api.database.entity.entityFields.Chapter;
import com.sKribble.api.database.entity.enums.ProjectTypes;
import com.sKribble.api.error.exceptions.CRUDExceptions.ContentNotFoundException;
import com.sKribble.api.error.exceptions.CRUDExceptions.DuplicateChapterException;
import com.sKribble.api.error.exceptions.CRUDExceptions.DuplicateCharacterException;
import com.sKribble.api.messages.errorMessages.CRUDErrorMessages;
import com.sKribble.api.utils.StringCheckerUtil;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TypeAlias("Story")
public class Story extends Project{

    private final String title;

    private final ProjectTypes type;

    private Map<Integer, Chapter> chapters;

    private Map<String, StoryCharacter> characters;

    @PersistenceCreator
    public Story(String title, ProjectTypes type, Map<Integer, Chapter> chapters, Map<String, StoryCharacter> characters, String ownerId) {
        super(ownerId);
        this.title = title;
        this.type = type;
        this.chapters = (chapters == null) ? new HashMap<Integer, Chapter>() : chapters;
        this.characters = (characters == null) ? new HashMap<String, StoryCharacter>() : characters;
    }

    public void addChapter(Chapter chapter){
        if(this.chapters.containsKey(chapter.getChapterNumber())){
            throw new DuplicateChapterException(CRUDErrorMessages.DUPLICATE_CHAPTER);
        }

        this.chapters.put(chapter.getChapterNumber(), chapter);
    }

    public void editChapter(Integer chapterNumber, String chapterName, String text){
        if(!this.chapters.containsKey(chapterNumber)){
            throw new ContentNotFoundException(CRUDErrorMessages.CHAPTER_NOT_FOUND);
        }

        this.chapters.get(chapterNumber).setChapterName(chapterName);
        
        if(StringCheckerUtil.isNotHollow(text)){
            this.chapters.get(chapterNumber).setText(text);
        }
        else{
            this.chapters.get(chapterNumber).setText(DefaultContents.STORY_CHAPTER_DEFAULT_CONTENT);
        }
    }

    public List<Chapter> getChaptersForDTO(){
        List<Chapter> chaptersForDTO = new ArrayList<>(this.chapters.values());
        chaptersForDTO.sort((chap1, chap2) -> {return Integer.compare(chap1.getChapterNumber(), chap2.getChapterNumber());});
        return chaptersForDTO;
    }

    public void addCharacter(StoryCharacter character){
        if(this.characters.containsKey(character.getCharacterId())){
            throw new DuplicateCharacterException(CRUDErrorMessages.DUPLICATE_CHARACTER_ID);
        }

        this.characters.put(character.getCharacterId(), character);
    }

    public void editCharacter(String characterId, String name, String description){
        if(!this.characters.containsKey(characterId)){
            throw new ContentNotFoundException(CRUDErrorMessages.CHARACTER_NOT_FOUND);
        }

        this.characters.get(characterId).setName(name);

        if(StringCheckerUtil.isNotHollow(description)){
            this.characters.get(characterId).setDescription(description);
        }
        else{
            this.characters.get(characterId).setDescription(DefaultContents.STORY_CHARACTER_DESC_DEFAULT_CONTENT);
        }
    }

    public List<StoryCharacter> getCharactersForDTO(){
        return new ArrayList<>(this.characters.values());
    }
}
