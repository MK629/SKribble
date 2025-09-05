package com.sKribble.api.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sKribble.api.database.entity.Project;
import com.sKribble.api.database.entity.User;
import com.sKribble.api.database.repository.ProjectRepository;
import com.sKribble.api.database.repository.UserRepository;
import com.sKribble.api.dto.input.common.ChangeOwnershipForm;
import com.sKribble.api.dto.input.common.DeleteProjectForm;
import com.sKribble.api.error.exceptions.CRUDExceptions.UserNotFoundException;
import com.sKribble.api.messages.errorMessages.AuthenticationErrorMessages;
import com.sKribble.api.messages.successMessages.CRUDSuccessMessages;
import com.sKribble.api.templates.SKribbleServiceTemplate;
import com.sKribble.api.utils.CurrentUserInfoUtil;
import com.sKribble.api.utils.OwnershipChecker;
import com.sKribble.api.utils.ProjectEntityUtil;
import com.sKribble.api.utils.ResponseEntityUtil;


@Service
public class SKribbleCommonService extends SKribbleServiceTemplate{

    //@Autowired is omitted because there's only one constructor.
    public SKribbleCommonService(ProjectRepository projectRepository, UserRepository userRepository) {
        super(projectRepository, userRepository);
    }

    @Transactional
    public ResponseEntity<String> changeOwnership(ChangeOwnershipForm changeOwnershipForm){
        User invoker = getInvoker();

        CurrentUserInfoUtil.checkExistence(invoker);

        User newOwner = userRepository.findByIdentification(changeOwnershipForm.newOwnerId());

        if(newOwner == null){
            throw new UserNotFoundException(AuthenticationErrorMessages.USER_NON_EXISTENT);
        }

        Project projectToTransferOwnership = projectRepository.findById(changeOwnershipForm.projectId()).orElse(null);

        ProjectEntityUtil.checkExistence(projectToTransferOwnership);

        OwnershipChecker.checkOwnership(invoker, projectToTransferOwnership);

        projectToTransferOwnership.changeOwnerShip(newOwner.getId());

        persistProject(projectToTransferOwnership);

        return ResponseEntityUtil.return200(CRUDSuccessMessages.PROJECT_OWNERSHIP_CHANGE_SUCCESS + newOwner.getUsername());
    }

    @Transactional
    public ResponseEntity<String> deleteProject(DeleteProjectForm deleteProjectForm){
        User invoker = getInvoker();

        CurrentUserInfoUtil.checkExistence(invoker);

        Project projectToDelete = projectRepository.findById(deleteProjectForm.projectId()).orElse(null);

        ProjectEntityUtil.checkExistence(projectToDelete);

        OwnershipChecker.checkOwnership(invoker, projectToDelete);

        projectRepository.deleteById(deleteProjectForm.projectId());

        return ResponseEntityUtil.return200(CRUDSuccessMessages.PROJECT_DELETION_SUCCESS);
    }
}
