package com.sKribble.api.dto.input;

import com.sKribble.api.messages.errorMessages.InputErrorMessages;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public record EMailLoginForm(
		@NotBlank(message = InputErrorMessages.REQUIRES_EMAIL)
		@NotEmpty(message = InputErrorMessages.REQUIRES_EMAIL)
		@Email(message = InputErrorMessages.WRONG_EMAIL_FORMAT)
		String email,
		@NotBlank(message = InputErrorMessages.REQUIRES_PASSWORD)
		@NotEmpty(message = InputErrorMessages.REQUIRES_PASSWORD)
		String password
		){}
