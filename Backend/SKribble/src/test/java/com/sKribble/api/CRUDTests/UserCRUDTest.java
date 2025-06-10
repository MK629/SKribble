package com.sKribble.api.CRUDTests;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;

import com.sKribble.api.constants.TestConstants;
import com.sKribble.api.database.entity.User;
import com.sKribble.api.database.repository.UserRepository;

@DataMongoTest
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
public class UserCRUDTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void userCreationTest(){
        User makeTestUser = new User(TestConstants.CREATION_TEST_USERNAME, TestConstants.CREATION_TEST_USERNAME + TestConstants.EMAIL_SUFFIX, TestConstants.TEST_DEFAULT_PASSWORD);
        userRepository.save(makeTestUser);
        User fetchTestUser = userRepository.findUserByUsernameOrEmail(TestConstants.CREATION_TEST_USERNAME);
        assertNotNull(fetchTestUser);
    }
    
    @AfterAll
    void cleanup(){
        userRepository.deleteAll();
    }
}
