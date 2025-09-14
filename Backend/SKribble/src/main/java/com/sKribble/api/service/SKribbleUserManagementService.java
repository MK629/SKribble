package com.sKribble.api.service;


import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sKribble.api.database.entity.User;
import com.sKribble.api.database.repository.ProjectRepository;
import com.sKribble.api.database.repository.UserRepository;
import com.sKribble.api.dto.input.userManagement.ChangeUserEmailForm;
import com.sKribble.api.dto.input.userManagement.ChangeUserPasswordForm;
import com.sKribble.api.dto.input.userManagement.ChangeUsernameForm;
import com.sKribble.api.error.exceptions.CRUDExceptions.PersistenceErrorException;
import com.sKribble.api.error.exceptions.CRUDExceptions.userManagement.UserManagementException;
import com.sKribble.api.messages.errorMessages.AuthenticationErrorMessages;
import com.sKribble.api.messages.errorMessages.CRUDErrorMessages;
import com.sKribble.api.messages.errorMessages.InputErrorMessages;
import com.sKribble.api.messages.successMessages.CRUDSuccessMessages;
import com.sKribble.api.templates.SKribbleServiceTemplate;
import com.sKribble.api.utils.CurrentUserInfoUtil;

@Service
public class SKribbleUserManagementService extends SKribbleServiceTemplate{

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    //@Autowired is omitted because there's only one constructor.
    public SKribbleUserManagementService(ProjectRepository projectRepository, UserRepository userRepository, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
        super(projectRepository, userRepository);
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
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
        User invoker = getInvoker();

        CurrentUserInfoUtil.checkExistence(invoker);

        try{
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(invoker.getUsername(), changeUserPasswordForm.currentPassword()));

            if(!authentication.isAuthenticated()){
                throw new UserManagementException(AuthenticationErrorMessages.WRONG_PASSWORD);
            }
        }
        catch (BadCredentialsException e) {
			throw new UserManagementException(AuthenticationErrorMessages.WRONG_PASSWORD);
		}

        invoker.changePassword(passwordEncoder.encode(changeUserPasswordForm.newPassword()));

        try{
            userRepository.save(invoker);
        }
        catch(Exception e){
            throw new PersistenceErrorException(CRUDErrorMessages.USERNAME_CHANGE_FAILED + " " + CRUDErrorMessages.PERSISTENCE_FAILED);
        }

        return CRUDSuccessMessages.PASSWORD_CHANGE_SUCCESS;
    }
}
