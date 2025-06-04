package com.sKribble.api.dto.input.song;

import com.sKribble.api.database.entity.enums.SongGenres;
import com.sKribble.api.messages.errorMessages.InputErrorMessages;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record NewSongForm(
    @NotBlank(message = InputErrorMessages.REQUIRES_SONG_TITLE)
    String title,
    @NotNull(message = InputErrorMessages.REQUIRES_SONG_GENRE)
    SongGenres genre,
    String lyrics,
    String sheetMusicImageUrl
) {}
