package com.sKribble.api.dto.input.user;

import com.sKribble.api.messages.errorMessages.InputErrorMessages;

import jakarta.validation.constraints.NotBlank;

public record UsernameLoginForm(
		@NotBlank(message = InputErrorMessages.REQUIRES_USERNAME)
		String username,
		@NotBlank(message = InputErrorMessages.REQUIRES_PASSWORD)
		String password
		){}
