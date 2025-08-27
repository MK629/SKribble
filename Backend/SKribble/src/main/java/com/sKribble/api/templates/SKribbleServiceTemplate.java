package com.sKribble.api.templates;

import com.sKribble.api.database.entity.Project;
import com.sKribble.api.database.entity.User;
import com.sKribble.api.database.repository.ProjectRepository;
import com.sKribble.api.database.repository.UserRepository;
import com.sKribble.api.error.exceptions.CRUDExceptions.PersistenceErrorException;
import com.sKribble.api.messages.errorMessages.CRUDErrorMessages;
import com.sKribble.api.utils.CurrentUserInfoUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

/**
 * <h4>Extend this class to write business logic for services.</h4>
 * <br/>
 * <ul>
 * <li>Note: DO NOT use this class for credential services.</li>
 * </ul>
 */
@RequiredArgsConstructor
@Log4j2
public class SKribbleServiceTemplate {

    protected final ProjectRepository projectRepository;
    protected final UserRepository userRepository;

    /**
     * Get the User entity of the user who made the request (invoker).
     */
    protected User getInvoker(){
        return userRepository.findUserByUsernameOrEmail(CurrentUserInfoUtil.getCurrentUserPrincipalName());
    }

    /**
     * Persist changes.
     */
    protected void persistProject(Project project){
        try{
            projectRepository.save(project);
        }
        catch(Exception e){
            throw new PersistenceErrorException(CRUDErrorMessages.PERSISTENCE_FAILED, e);
        }
    }
}
