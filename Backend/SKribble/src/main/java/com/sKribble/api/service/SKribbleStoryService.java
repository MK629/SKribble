package com.sKribble.api.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sKribble.api.database.entity.User;
import com.sKribble.api.database.entity.childEntities.Story;
import com.sKribble.api.database.entity.defaults.ProjectDefaultContents;
import com.sKribble.api.database.entity.entityFields.storyFields.Chapter;
import com.sKribble.api.database.entity.entityFields.storyFields.Landmark;
import com.sKribble.api.database.entity.entityFields.storyFields.StoryCharacter;
import com.sKribble.api.database.entity.enums.ProjectTypes;
import com.sKribble.api.database.repository.ProjectRepository;
import com.sKribble.api.database.repository.UserRepository;
import com.sKribble.api.dto.input.story.AddChapterForm;
import com.sKribble.api.dto.input.story.AddCharacterForm;
import com.sKribble.api.dto.input.story.AddLandmarkForm;
import com.sKribble.api.dto.input.story.ChangeCharacterImageForm;
import com.sKribble.api.dto.input.story.ChangeLandmarkImageForm;
import com.sKribble.api.dto.input.story.ChangeStoryTitleForm;
import com.sKribble.api.dto.input.story.DeleteChapterForm;
import com.sKribble.api.dto.input.story.DeleteCharacterForm;
import com.sKribble.api.dto.input.story.DeleteLandmarkForm;
import com.sKribble.api.dto.input.story.EditChapterForm;
import com.sKribble.api.dto.input.story.EditCharacterForm;
import com.sKribble.api.dto.input.story.EditLandmarkForm;
import com.sKribble.api.dto.input.story.StoryTitleInput;
import com.sKribble.api.dto.output.story.StoryOutput;
import com.sKribble.api.error.exceptions.CRUDExceptions.PersistenceErrorException;
import com.sKribble.api.messages.errorMessages.CRUDErrorMessages;
import com.sKribble.api.messages.successMessages.CRUDSuccessMessages;
import com.sKribble.api.utils.CurrentUserInfoUtil;
import com.sKribble.api.utils.DTOConverter;
import com.sKribble.api.utils.OwnershipChecker;
import com.sKribble.api.utils.ProjectEntityUtil;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class SKribbleStoryService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public List<StoryOutput> findStoriesByTitle(@Valid StoryTitleInput storyTitleInput){
        return projectRepository.findStoriesByTitle(storyTitleInput.title())
        .stream()
        .map((story) -> {
            User owner = userRepository.findByIdentification(story.getOwnerId());
            return DTOConverter.getStoryOutput(story, owner != null ? owner.getUsername() : ProjectDefaultContents.DELETED_USER);
        })
        .collect(Collectors.toList());
    }
    @Transactional
    public String newStory(@Valid StoryTitleInput storyTitleInput){
        User invoker = getInvoker();

        CurrentUserInfoUtil.checkExistence(invoker);

        Story newStory = new Story(storyTitleInput.title(), ProjectTypes.Story, null, null, null, invoker.getId());

        persistStory(newStory);

        return CRUDSuccessMessages.STORY_CREATION_SUCCESS;
    }

    @Transactional
    public String changeStoryTitle(@Valid ChangeStoryTitleForm changeStoryTitleForm){
        User invoker = getInvoker();

        CurrentUserInfoUtil.checkExistence(invoker); //Throws an exception. 

        Story storyToChangeTitle = projectRepository.findStoryById(changeStoryTitleForm.storyId());

        ProjectEntityUtil.checkExistence(storyToChangeTitle); //Throws an exception.

        OwnershipChecker.checkOwnership(invoker, storyToChangeTitle); //Throws an exception.

        storyToChangeTitle.changeTitle(changeStoryTitleForm.newTitle());

        persistStory(storyToChangeTitle); //Throws an exception.

        return CRUDSuccessMessages.STORY_TITLE_CHANGE_SUCCESS;
    }

//========================================================[ Chapter functions ]================================================================//

    @Transactional
    public String newChapter(@Valid AddChapterForm addChapterForm){
        User invoker = getInvoker();

        CurrentUserInfoUtil.checkExistence(invoker); 

        Story storyToAddChapter = projectRepository.findStoryById(addChapterForm.storyId());

        ProjectEntityUtil.checkExistence(storyToAddChapter);

        OwnershipChecker.checkOwnership(invoker, storyToAddChapter);

        storyToAddChapter.addChapter(new Chapter(addChapterForm.chapterNumber(), addChapterForm.chapterName(), addChapterForm.text()));

        persistStory(storyToAddChapter); 

        return CRUDSuccessMessages.CHAPTER_ADD_SUCCESS;
    }

    @Transactional
    public String editChapter(@Valid EditChapterForm editChapterForm){
        User invoker = getInvoker();

        CurrentUserInfoUtil.checkExistence(invoker);

        Story storyToEditChapter = projectRepository.findStoryById(editChapterForm.storyId());

        ProjectEntityUtil.checkExistence(storyToEditChapter);

        OwnershipChecker.checkOwnership(invoker, storyToEditChapter);

        storyToEditChapter.editChapter(editChapterForm.chapterNumber(), editChapterForm.chapterName(), editChapterForm.text());

        persistStory(storyToEditChapter);

        return CRUDSuccessMessages.CHAPTER_EDIT_SUCCESS;
    }

    @Transactional
    public String deleteChapter(@Valid DeleteChapterForm deleteChapterForm){
        User invoker = getInvoker();

        CurrentUserInfoUtil.checkExistence(invoker);

        Story storyToDeleteChapter = projectRepository.findStoryById(deleteChapterForm.storyId());

        ProjectEntityUtil.checkExistence(storyToDeleteChapter);

        OwnershipChecker.checkOwnership(invoker, storyToDeleteChapter);

        storyToDeleteChapter.deleteChapter(deleteChapterForm.chapterNumber());

        persistStory(storyToDeleteChapter);

        return CRUDSuccessMessages.CHAPTER_DELETE_SUCCESS;
    }

//========================================================[ Character functions ]================================================================//

    @Transactional
    public String newCharacter(@Valid AddCharacterForm addCharacterForm){
        User invoker = getInvoker();

        CurrentUserInfoUtil.checkExistence(invoker);

        Story storyToAddCharacter = projectRepository.findStoryById(addCharacterForm.storyId());

        ProjectEntityUtil.checkExistence(storyToAddCharacter);

        OwnershipChecker.checkOwnership(invoker, storyToAddCharacter);

        storyToAddCharacter.addCharacter(new StoryCharacter(UUID.randomUUID().toString(), addCharacterForm.characterName(), addCharacterForm.description(), addCharacterForm.imageUrl()));

        persistStory(storyToAddCharacter);

        return CRUDSuccessMessages.CHARACTER_CREATION_SUCCESS;
    }

    @Transactional
    public String editCharacter(@Valid EditCharacterForm editCharacterForm){
        User invoker = getInvoker();

        CurrentUserInfoUtil.checkExistence(invoker);

        Story storyToEditCharacter = projectRepository.findStoryById(editCharacterForm.storyId());

        ProjectEntityUtil.checkExistence(storyToEditCharacter);

        OwnershipChecker.checkOwnership(invoker, storyToEditCharacter);

        storyToEditCharacter.editCharacter(editCharacterForm.characterId(), editCharacterForm.characterName(), editCharacterForm.description());

        persistStory(storyToEditCharacter);

        return CRUDSuccessMessages.CHARACTER_EDIT_SUCCESS;
    }

    @Transactional
    public String deleteCharacter(@Valid DeleteCharacterForm deleteCharacterForm){
        User invoker = getInvoker();

        CurrentUserInfoUtil.checkExistence(invoker);

        Story storyToDeleteCharacter = projectRepository.findStoryById(deleteCharacterForm.storyId());

        ProjectEntityUtil.checkExistence(storyToDeleteCharacter);

        OwnershipChecker.checkOwnership(invoker, storyToDeleteCharacter);

        storyToDeleteCharacter.deleteCharacter(deleteCharacterForm.characterId());

        persistStory(storyToDeleteCharacter);

        return CRUDSuccessMessages.CHARACTER_DELETE_SUCCESS;
    }

    @Transactional
    public String changeCharacterImage(@Valid ChangeCharacterImageForm changeCharacterImageForm){
        User invoker = getInvoker();

        CurrentUserInfoUtil.checkExistence(invoker);

        Story storyToChangeCharacterImageUrl = projectRepository.findStoryById(changeCharacterImageForm.storyId());

        ProjectEntityUtil.checkExistence(storyToChangeCharacterImageUrl);

        OwnershipChecker.checkOwnership(invoker, storyToChangeCharacterImageUrl);

        storyToChangeCharacterImageUrl.changeCharacterImage(changeCharacterImageForm.characterId(), changeCharacterImageForm.newImageUrl());

        persistStory(storyToChangeCharacterImageUrl);

        return CRUDSuccessMessages.CHARACTER_IMAGE_UPLOAD_SUCCESS;
    }

//========================================================[ Landmark functions ]================================================================//

    @Transactional
    public String newLandmark(@Valid AddLandmarkForm addLandmarkForm){
        User invoker = getInvoker();

        CurrentUserInfoUtil.checkExistence(invoker);

        Story storyToAddLandmark = projectRepository.findStoryById(addLandmarkForm.storyId());

        ProjectEntityUtil.checkExistence(storyToAddLandmark);

        OwnershipChecker.checkOwnership(invoker, storyToAddLandmark);

        storyToAddLandmark.addLandmark(new Landmark(UUID.randomUUID().toString(), addLandmarkForm.landmarkName(), addLandmarkForm.description(), addLandmarkForm.imageUrl()));

        persistStory(storyToAddLandmark);

        return CRUDSuccessMessages.LANDMARK_CREATION_SUCCESS;
    }

    @Transactional
    public String editLandmark(@Valid EditLandmarkForm editLandmarkForm){
        User invoker = getInvoker();

        CurrentUserInfoUtil.checkExistence(invoker);

        Story storyToEditLandmark = projectRepository.findStoryById(editLandmarkForm.storyId());

        ProjectEntityUtil.checkExistence(storyToEditLandmark);

        OwnershipChecker.checkOwnership(invoker, storyToEditLandmark);

        storyToEditLandmark.editLandmark(editLandmarkForm.landmarkId(), editLandmarkForm.landmarkName(), editLandmarkForm.description());

        persistStory(storyToEditLandmark);

        return CRUDSuccessMessages.LANDMARK_EDIT_SUCCESS;
    }

    @Transactional
    public String deleteLandmark(@Valid DeleteLandmarkForm deleteLandmarkForm){
        User invoker = getInvoker();

        CurrentUserInfoUtil.checkExistence(invoker);

        Story storyToDeleteLandmark = projectRepository.findStoryById(deleteLandmarkForm.storyId());

        ProjectEntityUtil.checkExistence(storyToDeleteLandmark);

        OwnershipChecker.checkOwnership(invoker, storyToDeleteLandmark);

        storyToDeleteLandmark.deleteLandmark(deleteLandmarkForm.landmarkId());

        persistStory(storyToDeleteLandmark);

        return CRUDSuccessMessages.LANDMARK_DELETE_SUCCESS;
    }

    @Transactional
    public String changeLandmarkImage(@Valid ChangeLandmarkImageForm changeLandmarkImageForm){
        User invoker = getInvoker();

        CurrentUserInfoUtil.checkExistence(invoker);

        Story storyToChangeLandmarkImage = projectRepository.findStoryById(changeLandmarkImageForm.storyId());

        ProjectEntityUtil.checkExistence(storyToChangeLandmarkImage);

        OwnershipChecker.checkOwnership(invoker, storyToChangeLandmarkImage);

        storyToChangeLandmarkImage.changeLandmarkImage(changeLandmarkImageForm.landmarkId(), changeLandmarkImageForm.newImageUrl());

        persistStory(storyToChangeLandmarkImage);

        return CRUDSuccessMessages.LANDMARK_IMAGE_UPLOAD_SUCCESS;
    }

//==========================================[ Here lies the line for local abstractions ]================================================//

    //Get the User entity of the user who made the request (invoker).
    private User getInvoker(){
        return userRepository.findUserByUsernameOrEmail(CurrentUserInfoUtil.getCurrentUserPrincipalName());
    }

    //Create new or persist changes.
    private void persistStory(Story story){
        try{
            projectRepository.save(story);
        }
        catch(Exception e){
            throw new PersistenceErrorException(CRUDErrorMessages.PERSISTENCE_FAILED, e);
        }
    }
}
