package com.sKribble.api.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sKribble.api.database.entity.Project;
import com.sKribble.api.database.entity.User;
import com.sKribble.api.database.repository.ProjectRepository;
import com.sKribble.api.database.repository.UserRepository;
import com.sKribble.api.dto.input.common.DeleteProjectForm;
import com.sKribble.api.messages.successMessages.CRUDSuccessMessages;
import com.sKribble.api.utils.CurrentUserInfoUtil;
import com.sKribble.api.utils.OwnershipChecker;
import com.sKribble.api.utils.ProjectEntityUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SKribbleCommonService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    @Transactional
    public String deleteProject(DeleteProjectForm deleteProjectForm){
        User invoker = getInvoker();

        CurrentUserInfoUtil.checkExistence(invoker);

        Project projectToDelete = projectRepository.findById(deleteProjectForm.projectId()).orElse(null);

        ProjectEntityUtil.checkExistence(projectToDelete);

        OwnershipChecker.checkOwnership(invoker, projectToDelete);

        projectRepository.deleteById(deleteProjectForm.projectId());

        return CRUDSuccessMessages.PROJECT_DELETION_SUCCESS;
    }

//==========================================[ Here lies the line for local abstractions ]================================================//

    //Get the User entity of the user who made the request (invoker).
    private User getInvoker(){
        return userRepository.findUserByUsernameOrEmail(CurrentUserInfoUtil.getCurrentUserPrincipalName());
    }
}
