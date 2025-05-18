package com.sKribble.api.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sKribble.api.database.entity.Project;
import com.sKribble.api.database.entity.User;
import com.sKribble.api.database.entity.childEntities.Story;
import com.sKribble.api.database.entity.constants.DefaultContents;
import com.sKribble.api.database.entity.entityFields.Chapter;
import com.sKribble.api.database.entity.enums.ProjectTypes;
import com.sKribble.api.database.repository.ProjectRepository;
import com.sKribble.api.database.repository.UserRepository;
import com.sKribble.api.dto.input.AddChapterForm;
import com.sKribble.api.dto.input.StoryTitleInput;
import com.sKribble.api.dto.output.StoryOutput;
import com.sKribble.api.error.exceptions.CRUDExceptions.AssetNotOwnedException;
import com.sKribble.api.error.exceptions.CRUDExceptions.DuplicateChapterException;
import com.sKribble.api.error.exceptions.CRUDExceptions.IllogicalNullException;
import com.sKribble.api.error.exceptions.CRUDExceptions.PersistenceErrorException;
import com.sKribble.api.messages.errorMessages.CRUDErrorMessages;
import com.sKribble.api.messages.successMessages.CRUDSuccessMessages;
import com.sKribble.api.utils.CurrentUserInfoUtil;
import com.sKribble.api.utils.DTOConverter;
import com.sKribble.api.utils.OwnershipChecker;

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
            return DTOConverter.getStoryOutput(story, owner != null? owner.getUsername() : DefaultContents.DELETED_USER);
        }).collect(Collectors.toList());
    }

    //Mutation
    @Transactional
    public String newStory(@Valid StoryTitleInput storyTitleInput){

        User invoker = getInvoker();

        try{
            CurrentUserInfoUtil.checkExistence(invoker); //Throws an exception.
        }
        catch(IllogicalNullException e){
            throw e;
        }

        Project newStory = new Story(storyTitleInput.title(), ProjectTypes.Story, null, null, invoker.getId());

        try{
            projectRepository.save(newStory);
        }
        catch(Exception e){
            throw new PersistenceErrorException(CRUDErrorMessages.PERSISTENCE_FAILED, e);
        }

        return CRUDSuccessMessages.STORY_CREATION_SUCCESS;
    }

    //Mutation
    @Transactional
    public String newChapter(@Valid AddChapterForm addChapterForm){

        User invoker = getInvoker();

        Story storyToAddChapter = projectRepository.findStoryByIdentification(addChapterForm.storyId());

        try{
            OwnershipChecker.checkOwnership(invoker, storyToAddChapter); //Throws an exception.
        }
        catch(IllogicalNullException e){
            throw e;
        }
        catch(AssetNotOwnedException e){
            throw e;
        }

        storyToAddChapter.addChapter(new Chapter(addChapterForm.chapterNumber(), addChapterForm.chapterName(), addChapterForm.text()));

        try{
            projectRepository.save(storyToAddChapter);
        }
        catch(DuplicateChapterException e){
            throw e;
        }
        catch(Exception e){
            throw new PersistenceErrorException(CRUDErrorMessages.PERSISTENCE_FAILED, e);
        }

        return CRUDSuccessMessages.CHAPTER_ADD_SUCCESS;
    }

    private User getInvoker(){
        return userRepository.findUserByUsernameOrEmail(CurrentUserInfoUtil.getCurrentUserPrincipalName());
    }
}
