package com.sKribble.api.serviceTests;

import org.springframework.beans.factory.annotation.Autowired;

import com.sKribble.api.service.SKribbleStoryService;
import com.sKribble.api.templates.SKribbleServiceTestTemplate;

public class StoryServiceTests extends SKribbleServiceTestTemplate{

    @Autowired
    private SKribbleStoryService sKribbleStoryService;

    
}
