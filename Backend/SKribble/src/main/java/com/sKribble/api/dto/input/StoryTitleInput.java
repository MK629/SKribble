package com.sKribble.api.dto.input;

import com.sKribble.api.messages.errorMessages.InputErrorMessages;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public record StoryTitleInput(
    @NotBlank(message = InputErrorMessages.REQUIRES_STORY_TITLE)
    @NotEmpty(message = InputErrorMessages.REQUIRES_STORY_TITLE)
    String title
) {}
