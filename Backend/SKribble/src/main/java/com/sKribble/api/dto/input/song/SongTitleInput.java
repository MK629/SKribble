package com.sKribble.api.dto.input.song;

import com.sKribble.api.messages.errorMessages.InputErrorMessages;

import jakarta.validation.constraints.NotBlank;

public record SongTitleInput(
    @NotBlank(message = InputErrorMessages.REQUIRES_SONG_TITLE)
    String title
) {}
