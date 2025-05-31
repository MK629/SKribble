package com.sKribble.api.controller;

import java.util.List;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.sKribble.api.dto.input.EditChapterForm;
import com.sKribble.api.dto.input.EditCharacterForm;
import com.sKribble.api.dto.input.AddChapterForm;
import com.sKribble.api.dto.input.AddCharacterForm;
import com.sKribble.api.dto.input.ChangeCharacterImageForm;
import com.sKribble.api.dto.input.ChangeStoryTitleForm;
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
    public String changeStoryTitle(@Argument("changeStoryTitleForm") @Valid ChangeStoryTitleForm changeStoryTitleForm){
        return sKribbleStoryService.changeStoryTitle(changeStoryTitleForm);
    }

    @MutationMapping
    public String newChapter(@Argument("addChapterForm") @Valid AddChapterForm addChapterForm){
        return sKribbleStoryService.newChapter(addChapterForm);
    }

    @MutationMapping
    public String editChapter(@Argument("editChapterForm") @Valid EditChapterForm editChapterForm){
        return sKribbleStoryService.editChapter(editChapterForm);
    }

    @MutationMapping
    public String newCharacter(@Argument("addCharacterForm") @Valid AddCharacterForm addCharacterForm){
        return sKribbleStoryService.newCharacter(addCharacterForm);
    }

    @MutationMapping
    public String editCharacter(@Argument("editCharacterForm") @Valid EditCharacterForm editCharacterForm){
        return sKribbleStoryService.editCharacter(editCharacterForm);
    }

    @MutationMapping
    public String changeCharacterImage(@Argument("changeCharacterImageForm") @Valid ChangeCharacterImageForm changeCharacterImageForm){
        return sKribbleStoryService.changeCharacterImage(changeCharacterImageForm);
    }
}
