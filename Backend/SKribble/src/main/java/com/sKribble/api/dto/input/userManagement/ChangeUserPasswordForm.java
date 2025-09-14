package com.sKribble.api.dto.input.userManagement;

import com.sKribble.api.messages.errorMessages.InputErrorMessages;

import jakarta.validation.constraints.NotBlank;

public record ChangeUserPasswordForm(
    @NotBlank(message = InputErrorMessages.REQUIRES_CURRENT_PASSWORD)
    String currentPassword,
    @NotBlank(message = InputErrorMessages.REQUIRES_NEW_PASSWORD)
    String newPassword
){}
