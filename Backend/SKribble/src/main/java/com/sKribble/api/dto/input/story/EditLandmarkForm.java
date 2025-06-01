package com.sKribble.api.dto.input.story;

import com.sKribble.api.messages.errorMessages.InputErrorMessages;

import jakarta.validation.constraints.NotBlank;

public record EditLandmarkForm(
    @NotBlank(message = InputErrorMessages.REQUIRES_STORY_ID)
    String storyId,
    @NotBlank(message = InputErrorMessages.REQUIRES_LANDMARK_ID)
    String landmarkId,
    @NotBlank(message = InputErrorMessages.REQUIRES_LANDMARK_NAME)
    String landmarkName,
    String description
) {}
