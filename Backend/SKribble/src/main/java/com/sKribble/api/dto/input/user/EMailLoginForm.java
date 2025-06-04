package com.sKribble.api.dto.input.user;

import com.sKribble.api.messages.errorMessages.InputErrorMessages;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record EMailLoginForm(
	@NotBlank(message = InputErrorMessages.REQUIRES_EMAIL)
	@Email(message = InputErrorMessages.WRONG_EMAIL_FORMAT)
	String email,
	@NotBlank(message = InputErrorMessages.REQUIRES_PASSWORD)
	String password
){}
