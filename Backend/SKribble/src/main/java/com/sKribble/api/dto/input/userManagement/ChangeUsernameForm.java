package com.sKribble.api.dto.input.userManagement;

import com.sKribble.api.messages.errorMessages.InputErrorMessages;

import jakarta.validation.constraints.NotBlank;

public record ChangeUsernameForm(
    @NotBlank(message = InputErrorMessages.REQUIRES_NEW_USERNAME)
    String newUsername
){}
