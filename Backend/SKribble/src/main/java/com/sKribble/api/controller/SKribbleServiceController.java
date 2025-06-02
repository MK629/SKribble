package com.sKribble.api.controller;

import java.util.List;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.sKribble.api.dto.input.story.AddChapterForm;
import com.sKribble.api.dto.input.story.AddCharacterForm;
import com.sKribble.api.dto.input.story.AddLandmarkForm;
import com.sKribble.api.dto.input.story.ChangeCharacterImageForm;
import com.sKribble.api.dto.input.story.ChangeLandmarkImageForm;
import com.sKribble.api.dto.input.story.ChangeStoryTitleForm;
import com.sKribble.api.dto.input.story.DeleteChapterForm;
import com.sKribble.api.dto.input.story.DeleteCharacterForm;
import com.sKribble.api.dto.input.story.DeleteLandmarkForm;
import com.sKribble.api.dto.input.story.EditChapterForm;
import com.sKribble.api.dto.input.story.EditCharacterForm;
import com.sKribble.api.dto.input.story.EditLandmarkForm;
import com.sKribble.api.dto.input.story.StoryTitleInput;
import com.sKribble.api.dto.output.story.StoryOutput;
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
    public String deleteChapter(@Argument("deleteChapterForm") @Valid DeleteChapterForm deleteChapterForm){
        return sKribbleStoryService.deleteChapter(deleteChapterForm);
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
    public String deleteCharacter(@Argument("deleteCharacterForm") @Valid DeleteCharacterForm deleteCharacterForm){
        return sKribbleStoryService.deleteCharacter(deleteCharacterForm);
    }

    @MutationMapping
    public String changeCharacterImage(@Argument("changeCharacterImageForm") @Valid ChangeCharacterImageForm changeCharacterImageForm){
        return sKribbleStoryService.changeCharacterImage(changeCharacterImageForm);
    }

    @MutationMapping
    public String newLandmark(@Argument("addLandmarkForm") @Valid AddLandmarkForm addLandmarkForm){
        return sKribbleStoryService.newLandmark(addLandmarkForm);
    }

    @MutationMapping
    public String editLandmark(@Argument("editLandmarkForm") @Valid EditLandmarkForm editLandmarkForm){
        return sKribbleStoryService.editLandmark(editLandmarkForm);
    }

    @MutationMapping
    public String deleteLandmark(@Argument("deleteLandmarkForm") @Valid DeleteLandmarkForm deleteLandmarkForm){
        return sKribbleStoryService.deleteLandmark(deleteLandmarkForm);
    }

    @MutationMapping
    public String changeLandmarkImage(@Argument("changeLandmarkImageForm") @Valid ChangeLandmarkImageForm changeLandmarkImageForm){
        return sKribbleStoryService.changeLandmarkImage(changeLandmarkImageForm);
    }
}
