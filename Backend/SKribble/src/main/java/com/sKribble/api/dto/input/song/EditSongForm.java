package com.sKribble.api.dto.input.song;

import com.sKribble.api.messages.errorMessages.InputErrorMessages;

import jakarta.validation.constraints.NotBlank;

public record EditSongForm(
    @NotBlank(message = InputErrorMessages.REQUIRES_PROJECT_ID)
    String songId,
    @NotBlank(message = InputErrorMessages.REQUIRES_SONG_TITLE)
    String title,
    String lyrics,
    String sheetMusicImageUrl
){}
