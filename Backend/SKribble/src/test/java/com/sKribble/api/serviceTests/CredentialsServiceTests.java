package com.sKribble.api.serviceTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.AfterAll;
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
import com.sKribble.api.dto.input.user.UserRegisterForm;
import com.sKribble.api.error.exceptions.CRUDExceptions.UserRegstrationErrorException;
import com.sKribble.api.messages.successMessages.CRUDSuccessMessages;
import com.sKribble.api.service.CredentialsService;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CredentialsServiceTests {

    @Autowired
    CredentialsService credentialsService;

    @Autowired
    UserRepository userRepository;

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
        //Register again with the same username.
        assertThrows(UserRegstrationErrorException.class, () -> credentialsService.register(userRegisterForm3));
    }

    @Test
    @Order(2)
    void usernameLoginTest(){
        
    }

    @Test
    @Order(3)
    void emailLoginTest(){

    }


    @AfterAll
    void cleanup(){
        userRepository.deleteAll();
    }
}
