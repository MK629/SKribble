package com.sKribble.api.dto.input.story;

import com.sKribble.api.messages.errorMessages.InputErrorMessages;

import jakarta.validation.constraints.NotBlank;

public record ChangeStoryTitleForm (
    @NotBlank(message = InputErrorMessages.REQUIRES_STORY_ID)
    String storyId,
    @NotBlank(message = InputErrorMessages.REQUIRES_STORY_TITLE)
    String newTitle
) {}
