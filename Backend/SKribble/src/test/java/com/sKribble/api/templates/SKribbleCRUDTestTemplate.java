package com.sKribble.api.templates;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;

import com.sKribble.api.database.repository.ProjectRepository;

/**
 * <h4>Extend this class to write CRUD tests for the 'Project' entity or its children entities.</h4>
 * <br/>
 * <ul>
 * <li>Note: DO NOT use this to test the 'User' entity as credential logic is different from business ones.</li>
 * </ul>
 */
@DataMongoTest
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public abstract class SKribbleCRUDTestTemplate {

    @Autowired
    protected ProjectRepository projectRepository;

    @AfterEach
    void clean(){
        projectRepository.deleteAll();
    }

    @AfterAll
    void cleanup(){
        projectRepository.deleteAll();
    }
}
