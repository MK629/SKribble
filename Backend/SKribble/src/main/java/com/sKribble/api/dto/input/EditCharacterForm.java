package com.sKribble.api.dto.input;

import com.sKribble.api.messages.errorMessages.InputErrorMessages;

import jakarta.validation.constraints.NotBlank;

public record EditCharacterForm (
    @NotBlank(message = InputErrorMessages.REQUIRES_STORY_ID)
    String storyId,
    @NotBlank(message = InputErrorMessages.REQUIRES_CHARACTER_ID)
    String characterId,
    @NotBlank(message = InputErrorMessages.REQUIRES_CHARACTER_NAME)
    String name,
    String description
){}
