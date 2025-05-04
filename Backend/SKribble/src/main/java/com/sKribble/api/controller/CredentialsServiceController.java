package com.sKribble.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sKribble.api.dto.input.UserRegisterForm;
import com.sKribble.api.dto.input.UsernameLoginForm;
import com.sKribble.api.service.CredentialsService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/credentials")
@RequiredArgsConstructor
public class CredentialsServiceController {
	
	private final CredentialsService credentialsService;
	
	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody @Valid UserRegisterForm userRegisterForm) {
		return credentialsService.register(userRegisterForm);
	}
	
	@PostMapping("/uLogin")
	public ResponseEntity<String> usernameLogin(@RequestBody @Valid UsernameLoginForm usernameLoginForm){
		return credentialsService.usernameLogin(usernameLoginForm);
	}
}
