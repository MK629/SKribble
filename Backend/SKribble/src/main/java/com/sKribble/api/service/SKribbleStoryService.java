package com.sKribble.api.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.sKribble.api.database.entity.Project;
import com.sKribble.api.database.entity.User;
import com.sKribble.api.database.entity.childEntities.Story;
import com.sKribble.api.database.entity.enums.ProjectTypes;
import com.sKribble.api.database.repository.ProjectRepository;
import com.sKribble.api.database.repository.UserRepository;
import com.sKribble.api.dto.output.StoryOutput;
import com.sKribble.api.error.exceptions.CRUDExceptions.IllogicalException;
import com.sKribble.api.messages.errorMessages.CRUDErrorMessages;
import com.sKribble.api.messages.successMessages.CRUDSuccessMessages;
import com.sKribble.api.utils.CurrentUserInfoUtil;
import com.sKribble.api.utils.DTOConverter;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SKribbleStoryService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    //Query
    public List<StoryOutput> findStoriesByTitle(String title){
        return projectRepository.findStoriesByTitle(title)
        .stream()
        .map((story) -> {return DTOConverter.getStoryOutput(story, userRepository.findByIdentification(story.getOwnerId()).getUsername());}).collect(Collectors.toList());
    }

    //Mutation
    public String newStory(String name){

        User currentUser = userRepository.findUserByUsernameOrEmail(CurrentUserInfoUtil.getCurrentUserPrincipalName());

        if (currentUser == null){
            throw new IllogicalException(CRUDErrorMessages.ILLOGICAL_ERROR);
        }

        Project newStory = new Story(name, ProjectTypes.Story, null, currentUser.getId());
        projectRepository.save(newStory);

        return CRUDSuccessMessages.STORY_CREATION_SUCCESS;
    }
}
