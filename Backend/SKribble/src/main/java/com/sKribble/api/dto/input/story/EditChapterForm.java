package com.sKribble.api.dto.input.story;

import com.sKribble.api.messages.errorMessages.InputErrorMessages;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EditChapterForm(
    @NotBlank(message = InputErrorMessages.REQUIRES_STORY_ID)
    String storyId,
    @NotNull(message = InputErrorMessages.REQUIRES_CHAPTER_NUMBER)
    Integer chapterNumber,
    @NotBlank(message = InputErrorMessages.REQUIRES_CHAPTER_NAME)
    String chapterName,
    String text
) {}
