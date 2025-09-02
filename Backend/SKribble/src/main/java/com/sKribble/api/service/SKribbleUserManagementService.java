package com.sKribble.api.service;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.sKribble.api.database.entity.User;
import com.sKribble.api.database.repository.ProjectRepository;
import com.sKribble.api.database.repository.UserRepository;
import com.sKribble.api.dto.input.userManagement.ChangeUserEmailForm;
import com.sKribble.api.dto.input.userManagement.ChangeUserPasswordForm;
import com.sKribble.api.dto.input.userManagement.ChangeUsernameForm;
import com.sKribble.api.error.exceptions.CRUDExceptions.PersistenceErrorException;
import com.sKribble.api.error.exceptions.CRUDExceptions.userManagement.UserManagementException;
import com.sKribble.api.messages.errorMessages.CRUDErrorMessages;
import com.sKribble.api.messages.errorMessages.InputErrorMessages;
import com.sKribble.api.messages.successMessages.CRUDSuccessMessages;
import com.sKribble.api.templates.SKribbleServiceTemplate;
import com.sKribble.api.utils.CurrentUserInfoUtil;

@Service
public class SKribbleUserManagementService extends SKribbleServiceTemplate{

    //@Autowired is omitted because there's only one constructor.
    public SKribbleUserManagementService(ProjectRepository projectRepository, UserRepository userRepository) {
        super(projectRepository, userRepository);
    }

    public String changeUserName(ChangeUsernameForm changeUsernameForm){
        User invoker = getInvoker();

        CurrentUserInfoUtil.checkExistence(invoker);

        invoker.changeUsername(changeUsernameForm.newUsername());

        try{
            userRepository.save(invoker);
        }
        catch(DuplicateKeyException e){
            throw new UserManagementException(CRUDErrorMessages.USERNAME_CHANGE_FAILED + " " + InputErrorMessages.DUPLICATE_USERNAME, e);
        }
        catch(Exception e){
            throw new PersistenceErrorException(CRUDErrorMessages.USERNAME_CHANGE_FAILED + " " + CRUDErrorMessages.PERSISTENCE_FAILED, e);
        }

        return CRUDSuccessMessages.USERNAME_CHANGE_SUCCESS;
    }

    public String changeUserEmail(ChangeUserEmailForm changeUserEmailForm){
        User invoker = getInvoker();

        CurrentUserInfoUtil.checkExistence(invoker);

        invoker.changeEmail(changeUserEmailForm.newUserEmail());

        try{
            userRepository.save(invoker);
        }
        catch(DuplicateKeyException e){
            throw new UserManagementException(CRUDErrorMessages.USERNAME_CHANGE_FAILED + " " + InputErrorMessages.DUPLICATE_EMAIL, e);
        }
        catch(Exception e){
            throw new PersistenceErrorException(CRUDErrorMessages.USERNAME_CHANGE_FAILED + " " + CRUDErrorMessages.PERSISTENCE_FAILED, e);
        }

        return CRUDSuccessMessages.EMAIL_CHANGE_SUCCESS;
    }

    public String changeUserPassword(ChangeUserPasswordForm changeUserPasswordForm){
        return CRUDSuccessMessages.PASSWORD_CHANGE_SUCCESS;
    }
}
