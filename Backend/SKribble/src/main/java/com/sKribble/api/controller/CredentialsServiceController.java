package com.sKribble.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sKribble.api.dto.input.credentials.EMailLoginForm;
import com.sKribble.api.dto.input.credentials.UserRegisterForm;
import com.sKribble.api.dto.input.credentials.UsernameLoginForm;
import com.sKribble.api.dto.output.credentials.TokenCarrier;
import com.sKribble.api.service.CredentialsService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("${SKribble.auth.path}")
@RequiredArgsConstructor
public class CredentialsServiceController {
	
	private final CredentialsService credentialsService;
	
	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody @Valid UserRegisterForm userRegisterForm) {
		return credentialsService.register(userRegisterForm);
	}
	
	@PostMapping("/uLogin")
	public ResponseEntity<TokenCarrier> usernameLogin(@RequestBody @Valid UsernameLoginForm usernameLoginForm){
		return credentialsService.usernameLogin(usernameLoginForm);
	}
	
	@PostMapping("/eLogin")
	public ResponseEntity<TokenCarrier> emailLogin(@RequestBody @Valid EMailLoginForm eMailLoginForm){
		return credentialsService.emailLogin(eMailLoginForm);
	}
}
