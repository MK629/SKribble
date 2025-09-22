package com.sKribble.api.dto.output.song;

import com.sKribble.api.database.entity.enums.ProjectTypes;
import com.sKribble.api.database.entity.enums.SongGenres;
import com.sKribble.api.dto.output.common.ProjectOutput;

public record SongOutput(
    String id,
    String title,
    ProjectTypes type,
    SongGenres genre,
    String lyrics,
    String sheetMusicImageUrl,
    String owner
) implements ProjectOutput {} 
