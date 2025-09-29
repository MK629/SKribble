package com.sKribble.api.dto.output.song;

import java.util.List;

public record SongListOutput(
    List<SongOutput> songList,
    Boolean hasNext
){}
