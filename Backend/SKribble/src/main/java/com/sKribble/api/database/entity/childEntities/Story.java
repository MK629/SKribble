package com.sKribble.api.database.entity.childEntities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.annotation.TypeAlias;

import com.sKribble.api.database.entity.Project;
import com.sKribble.api.database.entity.defaults.StoryDefaultContents;
import com.sKribble.api.database.entity.entityFields.storyFields.Chapter;
import com.sKribble.api.database.entity.entityFields.storyFields.Landmark;
import com.sKribble.api.database.entity.entityFields.storyFields.StoryCharacter;
import com.sKribble.api.database.entity.enums.ProjectTypes;
import com.sKribble.api.error.exceptions.CRUDExceptions.ContentNotFoundException;
import com.sKribble.api.error.exceptions.CRUDExceptions.story.DuplicateChapterException;
import com.sKribble.api.error.exceptions.CRUDExceptions.story.DuplicateCharacterException;
import com.sKribble.api.error.exceptions.CRUDExceptions.story.DuplicateLandmarkException;
import com.sKribble.api.messages.errorMessages.CRUDErrorMessages;
import com.sKribble.api.utils.StringCheckerUtil;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TypeAlias("Story")
public class Story extends Project{

    private String title;

    private final ProjectTypes type;

    private Map<Integer, Chapter> chapters;

    private Map<String, StoryCharacter> characters;

    private Map<String, Landmark> landmarks;

    @PersistenceCreator
    public Story(String title, ProjectTypes type, Map<Integer, Chapter> chapters, Map<String, StoryCharacter> characters, Map<String, Landmark> landmarks,String ownerId) {
        super(ownerId);
        this.title = title;
        this.type = type;
        this.chapters = (chapters == null) ? new HashMap<Integer, Chapter>() : chapters;
        this.characters = (characters == null) ? new HashMap<String, StoryCharacter>() : characters;
        this.landmarks = (landmarks == null) ? new HashMap<String, Landmark>() : landmarks;
    }

    public void changeTitle(String newTitle){
        this.title = newTitle;
    }

//========================================================[ Chapter functions ]================================================================//

    public void addChapter(Chapter newChapter){
        if(this.chapters.containsKey(newChapter.getChapterNumber())){
            throw new DuplicateChapterException(CRUDErrorMessages.DUPLICATE_CHAPTER);
        }

        this.chapters.put(newChapter.getChapterNumber(), newChapter);
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
            this.chapters.get(chapterNumber).setText(StoryDefaultContents.STORY_CHAPTER_DEFAULT_CONTENT);
        }
    }

    public void deleteChapter(Integer chapterNumber){
        if(!this.chapters.containsKey(chapterNumber)){
            throw new ContentNotFoundException(CRUDErrorMessages.CHAPTER_NOT_FOUND);
        }

        this.chapters.remove(chapterNumber);
    }

    public List<Chapter> getChaptersAsList(){
        List<StoryCharacter> charactersList = getCharactersAsList();

        List<Landmark> landmarksList = getLandmarksAsList();

        List<Chapter> chaptersForDTO = new ArrayList<Chapter>(this.chapters.values());

        chaptersForDTO.stream().forEach((chapter) -> {
            chapter.listMentionedCharacters(charactersList);
            chapter.listMentionedLandmarks(landmarksList);
        });

        chaptersForDTO.sort((chap1, chap2) -> {return Integer.compare(chap1.getChapterNumber(), chap2.getChapterNumber());});

        return chaptersForDTO;
    }

//========================================================[ Character functions ]================================================================//

    public void addCharacter(StoryCharacter newCharacter){
        if(this.characters.containsKey(newCharacter.getCharacterId())){
            throw new DuplicateCharacterException(CRUDErrorMessages.DUPLICATE_CHARACTER_ID);
        }

        this.characters.put(newCharacter.getCharacterId(), newCharacter);
    }

    public void editCharacter(String characterId, String name, String description){
        if(!this.characters.containsKey(characterId)){
            throw new ContentNotFoundException(CRUDErrorMessages.CHARACTER_NOT_FOUND);
        }

        this.characters.get(characterId).setCharacterName(name);

        if(StringCheckerUtil.isNotHollow(description)){
            this.characters.get(characterId).setDescription(description);
        }
        else{
            this.characters.get(characterId).setDescription(StoryDefaultContents.STORY_CHARACTER_DESC_DEFAULT_CONTENT);
        }
    }

    public void deleteCharacter(String characterId){
        if(!this.characters.containsKey(characterId)){
            throw new ContentNotFoundException(CRUDErrorMessages.CHARACTER_NOT_FOUND);
        }

        this.characters.remove(characterId);
    }

    public void changeCharacterImage(String characterId, String newImageUrl){
        if(!this.characters.containsKey(characterId)){
            throw new ContentNotFoundException(CRUDErrorMessages.CHARACTER_NOT_FOUND);
        }

        if(StringCheckerUtil.isNotHollow(newImageUrl)){
            this.characters.get(characterId).setImageUrl(newImageUrl);
        }
        else{
            this.characters.get(characterId).setImageUrl(StoryDefaultContents.STORY_CHARACTER_IMAGE_URL_DEFAULT_CONTENT);
        }
    }

    public List<StoryCharacter> getCharactersAsList(){
        return new ArrayList<>(this.characters.values());
    }

//========================================================[ Landmark functions ]================================================================//

    public void addLandmark(Landmark newLandmark){
        if(this.landmarks.containsKey(newLandmark.getLandmarkId())){
            throw new DuplicateLandmarkException(CRUDErrorMessages.DUPLICATE_LANDMARK_ID);
        }

        this.landmarks.put(newLandmark.getLandmarkId(), newLandmark);
    }

    public void editLandmark(String landmarkId, String landmarkName, String description){
        if(!this.landmarks.containsKey(landmarkId)){
            throw new ContentNotFoundException(CRUDErrorMessages.LANDMARK_NOT_FOUND);
        }

        this.landmarks.get(landmarkId).setLandmarkName(landmarkName);

        if(StringCheckerUtil.isNotHollow(description)){
            this.landmarks.get(landmarkId).setDescription(description);
        }
        else{
            this.landmarks.get(landmarkId).setDescription(StoryDefaultContents.STORY_LANDMARK_DESC_DEFAULT_CONTENT);;
        }
    }

    public void deleteLandmark(String landmarkId){
        if(!this.landmarks.containsKey(landmarkId)){
            throw new ContentNotFoundException(CRUDErrorMessages.LANDMARK_NOT_FOUND);
        }

        this.landmarks.remove(landmarkId);
    }

    public void changeLandmarkImage(String landmarkId, String newImageUrl){
        if(!this.landmarks.containsKey(landmarkId)){
            throw new ContentNotFoundException(CRUDErrorMessages.LANDMARK_NOT_FOUND);
        }

        if(StringCheckerUtil.isNotHollow(newImageUrl)){
            this.landmarks.get(landmarkId).setImageUrl(newImageUrl);
        }
        else{
            this.landmarks.get(landmarkId).setImageUrl(StoryDefaultContents.STORY_LANDMARK_IMAGE_URL_DEFAULT_CONTENT);
        }
    }

    public List<Landmark> getLandmarksAsList(){
        return new ArrayList<>(this.landmarks.values());
    }
}
