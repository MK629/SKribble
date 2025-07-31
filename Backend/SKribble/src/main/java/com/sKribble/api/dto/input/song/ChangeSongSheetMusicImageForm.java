package com.sKribble.api.dto.input.song;

import com.sKribble.api.messages.errorMessages.InputErrorMessages;

import jakarta.validation.constraints.NotBlank;

public record ChangeSongSheetMusicImageForm(
    @NotBlank(message = InputErrorMessages.REQUIRES_PROJECT_ID)
    String songId,
    String sheetMusicImageUrl
) {}