package com.sKribble.api.service;

import org.springframework.stereotype.Service;

import com.sKribble.api.database.entity.Project;
import com.sKribble.api.database.entity.childEntities.Story;
import com.sKribble.api.database.entity.enums.ProjectTypes;
import com.sKribble.api.database.repository.ProjectRepository;
import com.sKribble.api.messages.successMessages.CRUDSuccessMessages;
import com.sKribble.api.utils.CurrentUserInfoUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SKribbleStoryService {

    private final ProjectRepository projectRepository;

    public String newStory(String name){

        Project newStory = new Story(name, ProjectTypes.Story, null, CurrentUserInfoUtil.getCurrentUserPrincipal());
        projectRepository.save(newStory);

        return CRUDSuccessMessages.STORY_CREATION_SUCCESS;
    }
}
