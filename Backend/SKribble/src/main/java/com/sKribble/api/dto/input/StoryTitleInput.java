package com.sKribble.api.dto.input;

import com.sKribble.api.messages.errorMessages.InputErrorMessages;

import jakarta.validation.constraints.NotBlank;

public record StoryTitleInput(
    @NotBlank(message = InputErrorMessages.REQUIRES_STORY_TITLE)
    String title
) {}
