package com.sKribble.api.dto.input.story;

import com.sKribble.api.messages.errorMessages.InputErrorMessages;

import jakarta.validation.constraints.NotBlank;

public record AddLandmarkForm(
    @NotBlank(message = InputErrorMessages.REQUIRES_PROJECT_ID)
    String storyId,
    @NotBlank(message = InputErrorMessages.REQUIRES_LANDMARK_NAME)
    String landmarkName,
    String description,
    String imageUrl
){}
