package com.sKribble.api.CRUDTests;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;


import com.sKribble.api.constants.UserTestConstants;
import com.sKribble.api.database.entity.User;
import com.sKribble.api.database.entity.enums.UserRoles;
import com.sKribble.api.database.repository.UserRepository;

@DataMongoTest
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserCRUDTests {

    @Autowired
    private UserRepository userRepository;

    //Can't autowire due to @DataMongoTest limiting beans
    private PasswordEncoder passwordEncoder;

    @BeforeAll
    void setup(){
        passwordEncoder = new BCryptPasswordEncoder();
    }

    @AfterEach
    void clean(){
        userRepository.deleteAll();
    }

    @Test
    @Order(1)
    void userCreationTest(){
        User testUser = testUserInstance();
        assertNotNull(userRepository.save(testUser));
    }

    @Test
    @Order(2)
    void duplicateNotAllowedTest(){
        User testUser = testUserInstance();
        User testUserSameUsername = new User(UserTestConstants.TEST_USERNAME, UserTestConstants.TEST_DIFFERENT_EMAIL, passwordEncoder.encode(UserTestConstants.TEST_DEFAULT_PASSWORD));
        User testUserSameEmail = new User(UserTestConstants.TEST_DIFFERENT_USERNAME, UserTestConstants.TEST_EMAIL, passwordEncoder.encode(UserTestConstants.TEST_DEFAULT_PASSWORD));

        userRepository.save(testUser);
        assertNotNull(userRepository.findByIdentification(testUser.getId()));

        assertThrows(DuplicateKeyException.class, () -> userRepository.save(testUserSameUsername));
        assertThrows(DuplicateKeyException.class, () -> userRepository.save(testUserSameEmail));
    }

    @Test
    @Order(3)
    void userFetchTest(){
        User testUser = testUserInstance();

        userRepository.save(testUser);

        assertAll(() -> {
            assertNotNull(userRepository.findByIdentification(testUser.getId()));
            assertNotNull(userRepository.findUserByUsernameOrEmail(UserTestConstants.TEST_USERNAME));
            assertNotNull(userRepository.findUserByUsernameOrEmail(UserTestConstants.TEST_EMAIL));
        });
    }

    @Test
    @Order(4)
    void userExpectedValuesTest(){
        User testUser = testUserInstance();
        testUser.assignRole(UserRoles.User);

        userRepository.save(testUser);

        User fetchedTestUser = userRepository.findByIdentification(testUser.getId());

        assertAll(() -> {
            assertEquals(UserTestConstants.TEST_USERNAME, fetchedTestUser.getUsername());
            assertEquals(UserTestConstants.TEST_EMAIL, fetchedTestUser.getEmail());
            assertTrue(passwordEncoder.matches(UserTestConstants.TEST_DEFAULT_PASSWORD, fetchedTestUser.getPassword()));
            assertTrue(fetchedTestUser.getRoles().contains(UserTestConstants.USER_ROLE));
        });
    }

    @Test
    @Order(5)
    void userDeleteTest(){
        User testUser = testUserInstance();

        userRepository.save(testUser);
        assertNotNull(userRepository.findByIdentification(testUser.getId()));

        userRepository.deleteById(testUser.getId());
        assertNull(userRepository.findByIdentification(testUser.getId()));
    }
    
    @AfterAll
    void cleanup(){
        userRepository.deleteAll();
    }

    private User testUserInstance(){
        return new User(UserTestConstants.TEST_USERNAME, UserTestConstants.TEST_EMAIL, passwordEncoder.encode(UserTestConstants.TEST_DEFAULT_PASSWORD));
    }
}
