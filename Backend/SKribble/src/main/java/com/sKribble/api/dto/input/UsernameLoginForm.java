package com.sKribble.api.dto.input;

import com.sKribble.api.messages.errorMessages.InputErrorMessages;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public record UsernameLoginForm(
		@NotBlank(message = InputErrorMessages.REQUIRES_USERNAME)
		@NotEmpty(message = InputErrorMessages.REQUIRES_USERNAME)
		String username,
		@NotBlank(message = InputErrorMessages.REQUIRES_PASSWORD)
		@NotEmpty(message = InputErrorMessages.REQUIRES_PASSWORD)
		String password
		){}
