package com.sKribble.api.controller;

import java.util.List;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.sKribble.api.dto.input.AddOrEditChapterForm;
import com.sKribble.api.dto.input.AddOrEditCharacterForm;
import com.sKribble.api.dto.input.StoryTitleInput;
import com.sKribble.api.dto.output.StoryOutput;
import com.sKribble.api.service.SKribbleStoryService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class SKribbleServiceController {

    private final SKribbleStoryService sKribbleStoryService;
	
    @QueryMapping
    public List<StoryOutput> findStoriesByTitle(@Argument("storyTitleInput") @Valid StoryTitleInput storyTitleInput){
        return sKribbleStoryService.findStoriesByTitle(storyTitleInput);
    }

    @MutationMapping
    public String newStory(@Argument("storyTitleInput") @Valid StoryTitleInput storyTitleInput){
        return sKribbleStoryService.newStory(storyTitleInput);
    }

    @MutationMapping
    public String newChapter(@Argument("addChapterForm") @Valid AddOrEditChapterForm addChapterForm){
        return sKribbleStoryService.newChapter(addChapterForm);
    }

    @MutationMapping
    public String editChapter(@Argument("editChapterForm") @Valid AddOrEditChapterForm editChapterForm){
        return sKribbleStoryService.editChapter(editChapterForm);
    }

    @MutationMapping
    public String newCharacter(@Argument("addCharacterForm") @Valid AddOrEditCharacterForm addCharacterForm){
        return sKribbleStoryService.newCharacter(addCharacterForm);
    }
}
