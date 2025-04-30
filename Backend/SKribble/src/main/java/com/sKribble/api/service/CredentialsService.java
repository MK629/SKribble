package com.sKribble.api.service;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sKribble.api.database.entity.User;
import com.sKribble.api.database.enums.UserRoles;
import com.sKribble.api.database.repository.UserRepository;
import com.sKribble.api.dto.input.UserRegisterForm;
import com.sKribble.api.error.exceptions.credentialsExceptions.UserRegstrationException;
import com.sKribble.api.messages.errorMessages.CRUDErrorMessages;
import com.sKribble.api.messages.successMessages.CRUDSuccessMessages;
import com.sKribble.api.utils.ResponseEntityUtil;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CredentialsService {
	
	private final UserRepository userRepo;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	
	public ResponseEntity<String> register(@Valid UserRegisterForm userRegisterForm) {
		
		try {
			User user = new User(userRegisterForm.username(), passwordEncoder.encode(userRegisterForm.password()));
			user.assignRole(UserRoles.User);
			userRepo.save(user);
		}
		catch(Exception e) {
			throw new UserRegstrationException(CRUDErrorMessages.REGISTER_FAILED, e);
		}
		
		return ResponseEntityUtil.return201(CRUDSuccessMessages.REGISTER_SUCCESS);
	}
}
