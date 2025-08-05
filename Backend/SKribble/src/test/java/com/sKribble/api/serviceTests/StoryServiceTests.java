package com.sKribble.api.serviceTests;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.ActiveProfiles;

import com.sKribble.api.constants.UserTestConstants;
import com.sKribble.api.database.repository.ProjectRepository;
import com.sKribble.api.database.repository.UserRepository;
import com.sKribble.api.dto.input.user.UserRegisterForm;
import com.sKribble.api.service.CredentialsService;
import com.sKribble.api.service.SKribbleStoryService;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StoryServiceTests {

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    SKribbleStoryService sKribbleStoryService;

    @Autowired
    CredentialsService credentialsService;

    @Autowired
    UserDetailsService userDetailsService;

    @BeforeAll
    void registerUsers(){
        UserRegisterForm userRegisterForm1 = new UserRegisterForm(UserTestConstants.TEST_USERNAME, UserTestConstants.TEST_EMAIL, UserTestConstants.TEST_DEFAULT_PASSWORD);
        UserRegisterForm userRegisterForm2 = new UserRegisterForm(UserTestConstants.TEST_DIFFERENT_USERNAME, UserTestConstants.TEST_DIFFERENT_EMAIL, UserTestConstants.TEST_DEFAULT_PASSWORD);

        credentialsService.register(userRegisterForm1);
        credentialsService.register(userRegisterForm2);
    }

    @AfterEach
    void clean(){
        projectRepository.deleteAll();
    }

    //Write tests here

    @AfterAll
    void cleanup(){
        projectRepository.deleteAll();
        userRepository.deleteAll();
    }

    void mockLogin(String usernameOrEmail){
        UserDetails user = userDetailsService.loadUserByUsername(usernameOrEmail);
        //Normally, this is used after the actual authentication logic. 
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user.getUsername(), null, user.getAuthorities()));
    }
}
