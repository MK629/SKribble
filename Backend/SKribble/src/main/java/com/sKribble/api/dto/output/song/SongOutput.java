package com.sKribble.api.dto.output.song;

import com.sKribble.api.database.entity.enums.ProjectTypes;
import com.sKribble.api.database.entity.enums.SongGenres;

public record SongOutput(
    String title,
    ProjectTypes type,
    SongGenres genre,
    String lyrics,
    String sheetMusicImageUrl,
    String owner
) {} 
