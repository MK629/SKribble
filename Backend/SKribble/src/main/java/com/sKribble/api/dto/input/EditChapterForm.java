package com.sKribble.api.dto.input;

import com.sKribble.api.messages.errorMessages.InputErrorMessages;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EditChapterForm(
    @NotBlank(message = InputErrorMessages.REQUIRES_STORY_ID)
    String storyId,
    @NotNull(message = InputErrorMessages.REQUIRES_CHAPTER_NUMBER)
    Integer chapterNumber,
    String chapterName,
    String text
) {}
