package com.sKribble.api.dto.input.story;

import com.sKribble.api.messages.errorMessages.InputErrorMessages;

import jakarta.validation.constraints.NotBlank;

public record ChangeCharacterImageForm (
    @NotBlank(message = InputErrorMessages.REQUIRES_PROJECT_ID)
    String storyId,
    @NotBlank(message = InputErrorMessages.REQUIRES_CHARACTER_ID)
    String characterId,
    @NotBlank(message = InputErrorMessages.REQURIRES_CHARACTER_IMAGE_URL)
    String newImageUrl
) {}
