package com.sKribble.api.dto.input.userManagement;

import com.sKribble.api.messages.errorMessages.InputErrorMessages;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ChangeUserEmailForm(
    @NotBlank(message = InputErrorMessages.REQUIRES_NEW_EMAIL)
    @Email(message = InputErrorMessages.WRONG_EMAIL_FORMAT)
    String newUserEmail
){}
