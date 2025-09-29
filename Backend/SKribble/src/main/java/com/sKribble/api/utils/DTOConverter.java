package com.sKribble.api.utils;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;

import com.sKribble.api.database.entity.Project;
import com.sKribble.api.database.entity.childEntities.Song;
import com.sKribble.api.database.entity.childEntities.Story;
import com.sKribble.api.dto.output.common.ProjectListOutput;
import com.sKribble.api.dto.output.common.ProjectOutput;
import com.sKribble.api.dto.output.credentials.TokenCarrier;
import com.sKribble.api.dto.output.song.SongListOutput;
import com.sKribble.api.dto.output.song.SongOutput;
import com.sKribble.api.dto.output.story.StoryListOutput;
import com.sKribble.api.dto.output.story.StoryOutput;
import com.sKribble.api.error.exceptions.CRUDExceptions.IllogicalNullException;

/**
 * A util class that takes data from the entites extracted from the DAOs and converts them into DTOs.
 */
public class DTOConverter {

    public static ProjectListOutput getProjectListOutput(Page<Project> projectList, String owner){
        return new ProjectListOutput(
            projectList.stream().map((p) -> {return getDynamicProjectOutput(p, owner);}).collect(Collectors.toList()), 
            projectList.hasNext()
        );
    }

    public static ProjectOutput getDynamicProjectOutput(Project project, String owner){
        if(project instanceof Story){
            return getStoryOutput((Story) project, owner);
        }
        else if(project instanceof Song){
            return getSongOutput((Song) project, owner);
        }
        else{
            throw new IllogicalNullException("Non-existent type for project entity.");
        }
    }

    public static StoryListOutput getStoryListOutput(List<StoryOutput> storyList, Boolean hasNext){
        return new StoryListOutput(storyList, hasNext);
    }

    public static StoryOutput getStoryOutput(Story story, String owner){
        return new StoryOutput(story.getId(), story.getTitle(), story.getType(), story.getChaptersAsList(), story.getCharactersAsList(), story.getLandmarksAsList(), owner);
    }

    public static SongListOutput getSongListOutput(List<SongOutput> songList, Boolean hasNext){
        return new SongListOutput(songList, hasNext);
    }

    public static SongOutput getSongOutput(Song song, String owner){
        return new SongOutput(song.getId(), song.getTitle(), song.getType(), song.getGenre(), song.getLyrics(), song.getSheetMusicImageUrl(), owner);
    }

    public static TokenCarrier makeTokenCarrier(String message, String token){
        return new TokenCarrier(message, token);
    }
}
