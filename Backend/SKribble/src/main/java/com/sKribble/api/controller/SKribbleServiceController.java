package com.sKribble.api.controller;

import java.util.List;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.sKribble.api.dto.input.song.ChangeSongGenreForm;
import com.sKribble.api.dto.input.song.ChangeSongSheetMusicImageForm;
import com.sKribble.api.dto.input.song.EditSongForm;
import com.sKribble.api.dto.input.song.NewSongForm;
import com.sKribble.api.dto.input.song.SongTitleInput;
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
import com.sKribble.api.dto.input.story.NewStoryForm;
import com.sKribble.api.dto.input.story.StoryTitleInput;
import com.sKribble.api.dto.input.userManagement.ChangeUserEmailForm;
import com.sKribble.api.dto.input.userManagement.ChangeUserPasswordForm;
import com.sKribble.api.dto.input.userManagement.ChangeUsernameForm;
import com.sKribble.api.dto.output.song.SongOutput;
import com.sKribble.api.dto.output.story.StoryOutput;
import com.sKribble.api.service.SKribbleSongService;
import com.sKribble.api.service.SKribbleStoryService;
import com.sKribble.api.service.SKribbleUserManagementService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class SKribbleServiceController {

    private final SKribbleStoryService sKribbleStoryService;
    private final SKribbleSongService sKribbleSongService;
    private final SKribbleUserManagementService sKribbleUserManagementService;

    //=======================================================[ User Management ]=======================================================

    @QueryMapping
    public String changeUserName(@Argument("changeUsernameForm") @Valid ChangeUsernameForm changeUsernameForm){
        return sKribbleUserManagementService.changeUserName(changeUsernameForm);
    }

    @QueryMapping
    public String changeUserEmail(@Argument("changeUserEmailForm") @Valid ChangeUserEmailForm changeUserEmailForm){
        return sKribbleUserManagementService.changeUserEmail(changeUserEmailForm);
    }

    //Later
    @QueryMapping
    public String changeUserPassword(@Valid ChangeUserPasswordForm changeUserPasswordForm){
        return null;
    }


    //=======================================================[ Story ]=======================================================
    
    @QueryMapping
    public List<StoryOutput> findStoriesByTitle(@Argument("storyTitleInput") @Valid StoryTitleInput storyTitleInput){
        return sKribbleStoryService.findStoriesByTitle(storyTitleInput);
    }

    @MutationMapping
    public String newStory(@Argument("newStoryForm") @Valid NewStoryForm newStoryForm){
        return sKribbleStoryService.newStory(newStoryForm);
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

    //=======================================================[ Song ]======================================================= 

    @QueryMapping
    public List<SongOutput> findSongsByTitle(@Argument("songTitleInput") @Valid SongTitleInput songTitleInput){
        return sKribbleSongService.findSongsByTitle(songTitleInput);
    }

    @MutationMapping
    public String newSong(@Argument("newSongForm") @Valid NewSongForm newSongForm){
        return sKribbleSongService.newSong(newSongForm);
    }

    @MutationMapping
    public String editSong(@Argument("editSongForm") @Valid EditSongForm editSongForm){
        return sKribbleSongService.editSong(editSongForm);
    }

    @MutationMapping
    public String changeSongSheetMusicImage(@Argument("changeSongSheetMusicImageForm") @Valid ChangeSongSheetMusicImageForm changeSongSheetMusicImageForm){
        return sKribbleSongService.changeSongSheetMusicImage(changeSongSheetMusicImageForm);
    }

    @MutationMapping
    public String changeSongGenre(@Argument("changeSongGenreForm") @Valid ChangeSongGenreForm changeSongGenreForm){
        return sKribbleSongService.changeSongGenre(changeSongGenreForm);
    }
}
