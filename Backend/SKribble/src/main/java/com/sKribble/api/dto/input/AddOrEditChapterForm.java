package com.sKribble.api.dto.input;

import com.sKribble.api.messages.errorMessages.InputErrorMessages;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AddOrEditChapterForm(
    @NotBlank(message = InputErrorMessages.REQUIRES_STORY_ID)
    String storyId,
    @NotNull(message = InputErrorMessages.REQUIRES_CHAPTER_NUMBER)
    Integer chapterNumber,
    @NotBlank(message = InputErrorMessages.REQUIRES_CHAPTER_NAME)
    String chapterName,
    String text
) {}
