package com.sKribble.api.controller;

import java.util.List;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.sKribble.api.dto.output.StoryOutput;
import com.sKribble.api.service.SKribbleStoryService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class SKribbleServiceController {

    private final SKribbleStoryService sKribbleStoryService;
	
    @QueryMapping
    public List<StoryOutput> findStoriesByTitle(@Argument("title") String title){
        return sKribbleStoryService.findStoriesByTitle(title);
    }

    @MutationMapping
    public String newStory(@Argument("title") String title){
        return sKribbleStoryService.newStory(title);
    }
}
