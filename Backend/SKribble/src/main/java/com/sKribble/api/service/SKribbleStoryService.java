package com.sKribble.api.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sKribble.api.database.entity.Project;
import com.sKribble.api.database.entity.User;
import com.sKribble.api.database.entity.childEntities.Story;
import com.sKribble.api.database.entity.entityFields.Chapter;
import com.sKribble.api.database.entity.enums.ProjectTypes;
import com.sKribble.api.database.repository.ProjectRepository;
import com.sKribble.api.database.repository.UserRepository;
import com.sKribble.api.dto.input.AddChapterForm;
import com.sKribble.api.dto.input.StoryTitleInput;
import com.sKribble.api.dto.output.StoryOutput;
import com.sKribble.api.error.exceptions.CRUDExceptions.IllogicalNullException;
import com.sKribble.api.messages.errorMessages.CRUDErrorMessages;
import com.sKribble.api.messages.successMessages.CRUDSuccessMessages;
import com.sKribble.api.utils.CurrentUserInfoUtil;
import com.sKribble.api.utils.DTOConverter;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SKribbleStoryService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    //Query
    public List<StoryOutput> findStoriesByTitle(@Valid StoryTitleInput storyTitleInput){
        return projectRepository.findStoriesByTitle(storyTitleInput.title())
        .stream()
        .map((story) -> {return DTOConverter.getStoryOutput(story, userRepository.findByIdentification(story.getOwnerId()).getUsername());}).collect(Collectors.toList());
    }

    //Mutation
    @Transactional
    public String newStory(@Valid StoryTitleInput storyTitleInput){

        User currentUser = userRepository.findUserByUsernameOrEmail(CurrentUserInfoUtil.getCurrentUserPrincipalName());

        if (currentUser == null){
            throw new IllogicalNullException(CRUDErrorMessages.ILLOGICAL_NULL_ERROR);
        }

        Project newStory = new Story(storyTitleInput.title(), ProjectTypes.Story, null, null, currentUser.getId());

        try{
            projectRepository.save(newStory);
        }
        catch(Exception e){
            throw new IllogicalNullException(CRUDErrorMessages.ILLOGICAL_NULL_ERROR);
        }

        return CRUDSuccessMessages.STORY_CREATION_SUCCESS;
    }

    //Mutation
    @Transactional
    public String addChapter(@Valid AddChapterForm addChapterForm){

        Story storyToAddChapter = projectRepository.findStoryByIdentification(addChapterForm.storyId());
        storyToAddChapter.addChapter(new Chapter(addChapterForm.chapterNumber(), addChapterForm.chapterName(), addChapterForm.text()));

        try{
            projectRepository.save(storyToAddChapter);
        }
        catch(Exception e){
            throw new IllogicalNullException(CRUDErrorMessages.ILLOGICAL_NULL_ERROR);
        }

        return CRUDSuccessMessages.CHAPTER_ADD_SUCCESS;
    }
}
