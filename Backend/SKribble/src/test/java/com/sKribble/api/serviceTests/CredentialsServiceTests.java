package com.sKribble.api.serviceTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import com.sKribble.api.constants.UserTestConstants;
import com.sKribble.api.database.repository.UserRepository;
import com.sKribble.api.dto.input.credentials.EMailLoginForm;
import com.sKribble.api.dto.input.credentials.UserRegisterForm;
import com.sKribble.api.dto.input.credentials.UsernameLoginForm;
import com.sKribble.api.dto.output.credentials.TokenCarrier;
import com.sKribble.api.error.exceptions.CRUDExceptions.UserRegstrationErrorException;
import com.sKribble.api.error.exceptions.credentialsExceptions.LoginErrorException;
import com.sKribble.api.messages.successMessages.CRUDSuccessMessages;
import com.sKribble.api.service.CredentialsService;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CredentialsServiceTests {

    @Autowired
    private CredentialsService credentialsService;

    @Autowired
    private UserRepository userRepository;

    @AfterEach
    void clean(){
        userRepository.deleteAll();
    }

    @Test
    @Order(1)
    void registerTest(){
        UserRegisterForm userRegisterForm1 = new UserRegisterForm(UserTestConstants.TEST_USERNAME, UserTestConstants.TEST_EMAIL, UserTestConstants.TEST_DEFAULT_PASSWORD);
        UserRegisterForm userRegisterForm2 = new UserRegisterForm(UserTestConstants.TEST_USERNAME, UserTestConstants.TEST_DIFFERENT_EMAIL, UserTestConstants.TEST_DEFAULT_PASSWORD);
        UserRegisterForm userRegisterForm3 = new UserRegisterForm(UserTestConstants.TEST_DIFFERENT_USERNAME, UserTestConstants.TEST_EMAIL, UserTestConstants.TEST_DEFAULT_PASSWORD);

        //First time register
        ResponseEntity<String> registerResponse = credentialsService.register(userRegisterForm1);

        assertEquals(HttpStatus.CREATED, registerResponse.getStatusCode());
        assertEquals(CRUDSuccessMessages.REGISTER_SUCCESS, registerResponse.getBody());

        //Register again with the same username.
        assertThrows(UserRegstrationErrorException.class, () -> credentialsService.register(userRegisterForm2));
        //Register again with the same email.
        assertThrows(UserRegstrationErrorException.class, () -> credentialsService.register(userRegisterForm3));
    }

    @Test
    @Order(2)
    void usernameLoginTest(){
        UserRegisterForm userRegisterForm = new UserRegisterForm(UserTestConstants.TEST_USERNAME, UserTestConstants.TEST_EMAIL, UserTestConstants.TEST_DEFAULT_PASSWORD);
        credentialsService.register(userRegisterForm);

        UsernameLoginForm usernameLoginForm = new UsernameLoginForm(UserTestConstants.TEST_USERNAME, UserTestConstants.TEST_DEFAULT_PASSWORD);
        UsernameLoginForm wrongUsernameLoginForm = new UsernameLoginForm(UserTestConstants.TEST_DIFFERENT_USERNAME, UserTestConstants.TEST_DEFAULT_PASSWORD);
        UsernameLoginForm usernameLoginFormWrongPassword = new UsernameLoginForm(UserTestConstants.TEST_USERNAME, "I'm a hacker trying to hack this account.");

        ResponseEntity<TokenCarrier> usernameLoginResponse = credentialsService.usernameLogin(usernameLoginForm);
        
        assertEquals(HttpStatus.OK, usernameLoginResponse.getStatusCode());
        assertNotNull(usernameLoginResponse.getBody());

        assertThrows(LoginErrorException.class, () -> credentialsService.usernameLogin(wrongUsernameLoginForm));
        assertThrows(LoginErrorException.class, () -> credentialsService.usernameLogin(usernameLoginFormWrongPassword));
    }

    @Test
    @Order(3)
    void emailLoginTest(){
        UserRegisterForm userRegisterForm = new UserRegisterForm(UserTestConstants.TEST_USERNAME, UserTestConstants.TEST_EMAIL, UserTestConstants.TEST_DEFAULT_PASSWORD);
        credentialsService.register(userRegisterForm);

        EMailLoginForm emailLoginForm = new EMailLoginForm(UserTestConstants.TEST_EMAIL, UserTestConstants.TEST_DEFAULT_PASSWORD);
        EMailLoginForm wrongEmailLoginForm = new EMailLoginForm(UserTestConstants.TEST_DIFFERENT_EMAIL, UserTestConstants.TEST_DEFAULT_PASSWORD);
        EMailLoginForm emailLoginFormWrongPassword = new EMailLoginForm(UserTestConstants.TEST_EMAIL, "I'm a hacker trying to hack this account.");

        ResponseEntity<TokenCarrier> usernameLoginResponse = credentialsService.emailLogin(emailLoginForm);
        
        assertEquals(HttpStatus.OK, usernameLoginResponse.getStatusCode());
        assertNotNull(usernameLoginResponse.getBody());

        assertThrows(LoginErrorException.class, () -> credentialsService.emailLogin(wrongEmailLoginForm));
        assertThrows(LoginErrorException.class, () -> credentialsService.emailLogin(emailLoginFormWrongPassword));
    }

    @AfterAll
    void cleanup(){
        userRepository.deleteAll();
    }
}
