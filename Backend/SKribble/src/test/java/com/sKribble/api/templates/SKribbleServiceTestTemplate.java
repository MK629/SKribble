package com.sKribble.api.templates;

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
import com.sKribble.api.dto.input.credentials.UserRegisterForm;
import com.sKribble.api.service.CredentialsService;

/**
 * <h4>Extend this class to write tests for services.</h4>
 * <br/>
 * <ul>
 * <li>Note: DO NOT use this class to test credential services as they are different in logic.</li>
 * </ul>
 */
@SpringBootTest
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SKribbleServiceTestTemplate {

    @Autowired
    protected ProjectRepository projectRepository;

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected CredentialsService credentialsService;

    @Autowired
    protected UserDetailsService userDetailsService;

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

    @AfterAll
    void cleanup(){
        projectRepository.deleteAll();
        userRepository.deleteAll();
    }

    /**
     * @param usernameOrEmail
     * 
     * <h4>This emulates an actual login by pushing an authenticated token into Spring's SecurityContextHolder.</h4>
     */
    protected void mockLogin(String usernameOrEmail){
        UserDetails user = userDetailsService.loadUserByUsername(usernameOrEmail);
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user.getUsername(), null, user.getAuthorities()));
    }
}
