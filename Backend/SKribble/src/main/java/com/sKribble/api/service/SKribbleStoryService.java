package com.sKribble.api.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sKribble.api.database.entity.User;
import com.sKribble.api.database.entity.childEntities.Story;
import com.sKribble.api.database.entity.constants.DefaultContents;
import com.sKribble.api.database.entity.entityFields.Chapter;
import com.sKribble.api.database.entity.entityFields.StoryCharacter;
import com.sKribble.api.database.entity.enums.ProjectTypes;
import com.sKribble.api.database.repository.ProjectRepository;
import com.sKribble.api.database.repository.UserRepository;
import com.sKribble.api.dto.input.AddOrEditChapterForm;
import com.sKribble.api.dto.input.AddOrEditCharacterForm;
import com.sKribble.api.dto.input.StoryTitleInput;
import com.sKribble.api.dto.output.StoryOutput;
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

    //Query
    public List<StoryOutput> findStoriesByTitle(@Valid StoryTitleInput storyTitleInput){
        return projectRepository.findStoriesByTitle(storyTitleInput.title())
        .stream()
        .map((story) -> {
            User owner = userRepository.findByIdentification(story.getOwnerId());
            return DTOConverter.getStoryOutput(story, owner != null ? owner.getUsername() : DefaultContents.DELETED_USER);
        })
        .collect(Collectors.toList());
    }

    //Mutation
    @Transactional
    public String newStory(@Valid StoryTitleInput storyTitleInput){
        User invoker = getInvoker();

        CurrentUserInfoUtil.checkExistence(invoker); //Throws an exception.

        Story newStory = new Story(storyTitleInput.title(), ProjectTypes.Story, null, null, invoker.getId());

        persistStory(newStory); //Throws an exception.

        return CRUDSuccessMessages.STORY_CREATION_SUCCESS;
    }

    //Mutation
    @Transactional
    public String newChapter(@Valid AddOrEditChapterForm addChapterForm){
        User invoker = getInvoker();

        CurrentUserInfoUtil.checkExistence(invoker); //Throws an Exception

        Story storyToAddChapter = projectRepository.findStoryById(addChapterForm.storyId());

        ProjectEntityUtil.checkExistence(storyToAddChapter); //Throws an Exception

        OwnershipChecker.checkOwnership(invoker, storyToAddChapter); //Throws an exception.

        storyToAddChapter.addChapter(new Chapter(addChapterForm.chapterNumber(), addChapterForm.chapterName(), addChapterForm.text())); //Throws an exception

        persistStory(storyToAddChapter); //Throws an exception.

        return CRUDSuccessMessages.CHAPTER_ADD_SUCCESS;
    }

    //Mutation
    @Transactional
    public String editChapter(@Valid AddOrEditChapterForm editChapterForm){
        User invoker = getInvoker();

        CurrentUserInfoUtil.checkExistence(invoker); //Throws an Exception

        Story storyToEditChapter = projectRepository.findStoryById(editChapterForm.storyId());

        ProjectEntityUtil.checkExistence(storyToEditChapter); //Throws an Exception

        OwnershipChecker.checkOwnership(invoker, storyToEditChapter); //Throws an Exception

        storyToEditChapter.editChapter(editChapterForm.chapterNumber(), editChapterForm.chapterName(), editChapterForm.text());

        persistStory(storyToEditChapter); //Throws an exception.

        return CRUDSuccessMessages.CHAPTER_EDIT_SUCCESS;
    }

    @Transactional
    public String newCharacter(@Valid AddOrEditCharacterForm addCharacterForm){
        User invoker = getInvoker();

        CurrentUserInfoUtil.checkExistence(invoker); //Throws an Exception

        Story storyToAddCharacter = projectRepository.findStoryById(addCharacterForm.storyId());

        ProjectEntityUtil.checkExistence(storyToAddCharacter); //Throws an Exception

        OwnershipChecker.checkOwnership(invoker, storyToAddCharacter); //Throws an Exception

        storyToAddCharacter.addCharacter(new StoryCharacter(addCharacterForm.name(), addCharacterForm.description()));

        persistStory(storyToAddCharacter); //Throws an Exception

        return CRUDSuccessMessages.CHARACTER_CREATION_SUCCESS;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //Get the User entity of the user who made the request.
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
