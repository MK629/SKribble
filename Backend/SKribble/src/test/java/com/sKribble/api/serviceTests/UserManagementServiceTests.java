package com.sKribble.api.serviceTests;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.sKribble.api.constants.UserTestConstants;
import com.sKribble.api.dto.input.userManagement.ChangeUserEmailForm;
import com.sKribble.api.dto.input.userManagement.ChangeUserPasswordForm;
import com.sKribble.api.dto.input.userManagement.ChangeUsernameForm;
import com.sKribble.api.error.exceptions.CRUDExceptions.userManagement.UserManagementException;
import com.sKribble.api.messages.successMessages.CRUDSuccessMessages;
import com.sKribble.api.service.SKribbleUserManagementService;
import com.sKribble.api.templates.SKribbleServiceTestTemplate;

public class UserManagementServiceTests extends SKribbleServiceTestTemplate{

    @Autowired
    private SKribbleUserManagementService sKribbleUserManagementService;

    @Test
    @Order(1)
    void userNameChangeTest(){
        mockLogin(UserTestConstants.TEST_USERNAME);

        ChangeUsernameForm changeUsernameForm = new ChangeUsernameForm(UserTestConstants.TEST_USERNAME2);
        ChangeUsernameForm wrongChangeUsernameForm = new ChangeUsernameForm(UserTestConstants.TEST_DIFFERENT_USERNAME);

        assertThrows(UserManagementException.class, () -> sKribbleUserManagementService.changeUserName(wrongChangeUsernameForm));
        assertEquals(CRUDSuccessMessages.USERNAME_CHANGE_SUCCESS, sKribbleUserManagementService.changeUserName(changeUsernameForm));

        assertThrows(UsernameNotFoundException.class, () -> mockLogin(UserTestConstants.TEST_USERNAME));

        assertDoesNotThrow(() -> mockLogin(UserTestConstants.TEST_USERNAME2));
        assertDoesNotThrow(() -> mockLogin(UserTestConstants.TEST_EMAIL));
    }

    @Test
    @Order(2)
    void userEmailChangeTest(){
        mockLogin(UserTestConstants.TEST_DIFFERENT_USERNAME);

        ChangeUserEmailForm changeUserEmailForm = new ChangeUserEmailForm(UserTestConstants.TEST_EMAIL2);
        ChangeUserEmailForm wrongChangeEmailForm = new ChangeUserEmailForm(UserTestConstants.TEST_EMAIL);

        assertThrows(UserManagementException.class, () -> sKribbleUserManagementService.changeUserEmail(wrongChangeEmailForm));
        assertEquals(CRUDSuccessMessages.EMAIL_CHANGE_SUCCESS, sKribbleUserManagementService.changeUserEmail(changeUserEmailForm));

        assertThrows(UsernameNotFoundException.class, () -> mockLogin(UserTestConstants.TEST_DIFFERENT_EMAIL));

        assertDoesNotThrow(() -> mockLogin(UserTestConstants.TEST_EMAIL2));
        assertDoesNotThrow(() -> mockLogin(UserTestConstants.TEST_DIFFERENT_USERNAME));
    }

    @Test
    @Order(3)
    void userPasswordChangeTest(){
        mockLogin(UserTestConstants.TEST_DIFFERENT_USERNAME);

        ChangeUserPasswordForm changeUserPasswordForm = new ChangeUserPasswordForm(UserTestConstants.TEST_DEFAULT_PASSWORD, UserTestConstants.TEST_OTHER_PASSWORD);
        ChangeUserPasswordForm wrongChangeUserPasswordForm = new ChangeUserPasswordForm("Oh! Look at me. I'm a dumb user who forgot my password.", UserTestConstants.TEST_OTHER_PASSWORD);

        assertThrows(UserManagementException.class, () -> sKribbleUserManagementService.changeUserPassword(wrongChangeUserPasswordForm));
        assertEquals(CRUDSuccessMessages.PASSWORD_CHANGE_SUCCESS, sKribbleUserManagementService.changeUserPassword(changeUserPasswordForm));
        //Need to add more checks. Do later.
    }

}
