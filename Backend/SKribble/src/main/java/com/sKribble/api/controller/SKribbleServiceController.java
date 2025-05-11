package com.sKribble.api.controller;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

import com.sKribble.api.service.SKribbleStoryService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class SKribbleServiceController {

    private final SKribbleStoryService sKribbleStoryService;
	
    @MutationMapping
    public String newStory(@Argument("name") String name){
        return sKribbleStoryService.newStory(name);
    }
}
