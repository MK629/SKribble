package com.sKribble.api.dto.input.song;

import com.sKribble.api.database.entity.enums.SongGenres;
import com.sKribble.api.messages.errorMessages.InputErrorMessages;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ChangeSongGenreForm(
    @NotBlank(message = InputErrorMessages.REQUIRES_PROJECT_ID)
    String songId,
    @NotNull(message = InputErrorMessages.REQUIRES_SONG_GENRE)
    SongGenres newGenre
) {}
