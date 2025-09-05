package com.sKribble.api.dto.input.common;

import com.sKribble.api.messages.errorMessages.InputErrorMessages;

import jakarta.validation.constraints.NotBlank;

public record ChangeOwnershipForm(
    @NotBlank(message = InputErrorMessages.REQUIRES_PROJECT_ID)
    String projectId,
    @NotBlank(message = InputErrorMessages.REQUIRES_NEW_OWNER_ID)
    String newOwnerId
) {}
