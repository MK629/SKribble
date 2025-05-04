package com.sKribble.api.service;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sKribble.api.database.entity.User;
import com.sKribble.api.database.enums.UserRoles;
import com.sKribble.api.database.repository.UserRepository;
import com.sKribble.api.dto.input.EMailLoginForm;
import com.sKribble.api.dto.input.UserRegisterForm;
import com.sKribble.api.dto.input.UsernameLoginForm;
import com.sKribble.api.error.exceptions.credentialsExceptions.LoginErrorException;
import com.sKribble.api.error.exceptions.credentialsExceptions.UserRegstrationErrorException;
import com.sKribble.api.messages.errorMessages.AuthenticationErrorMessages;
import com.sKribble.api.messages.errorMessages.CRUDErrorMessages;
import com.sKribble.api.messages.errorMessages.InputErrorMessages;
import com.sKribble.api.messages.successMessages.CRUDSuccessMessages;
import com.sKribble.api.security.jwt.JwtUtil;
import com.sKribble.api.utils.ResponseEntityUtil;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CredentialsService {
	
	private final UserRepository userRepo;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	private final JwtUtil jwtUtil;
	
	//Register a new user.
	public ResponseEntity<String> register(@Valid UserRegisterForm userRegisterForm) {
		
		try {
			User user = new User(userRegisterForm.username(), userRegisterForm.email(), passwordEncoder.encode(userRegisterForm.password()));
			user.assignRole(UserRoles.User);
			userRepo.save(user);
		}
		catch(DuplicateKeyException e) {
			
			String message = "";
			
			if(e.getMessage().contains("username")) {
				message += InputErrorMessages.DUPLICATE_USERNAME;
			}
			
			if(e.getMessage().contains("email")) {
			    message += InputErrorMessages.DUPLICATE_EMAIL;
			}
			
			throw new UserRegstrationErrorException(CRUDErrorMessages.REGISTER_FAILED, new RuntimeException(message));
		}
		catch(Exception e) {
			throw new UserRegstrationErrorException(CRUDErrorMessages.REGISTER_FAILED, e);
		}
		
		return ResponseEntityUtil.return201(CRUDSuccessMessages.REGISTER_SUCCESS);
	}
	
	//User name login
	public ResponseEntity<String> usernameLogin(@Valid UsernameLoginForm usernameLoginForm){
		
		try {
			Authentication authenticationStatus = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(usernameLoginForm.username(), usernameLoginForm.password()));
			
			if(authenticationStatus.isAuthenticated()) {
				return ResponseEntityUtil.return200(jwtUtil.generateToken(usernameLoginForm.username()));
			}
			else {
				throw new LoginErrorException(AuthenticationErrorMessages.LOGIN_FAILED + " " + AuthenticationErrorMessages.TRY_AGAIN);
			}
		} 
		catch (BadCredentialsException e) {
			throw new LoginErrorException(AuthenticationErrorMessages.LOGIN_FAILED + " " + AuthenticationErrorMessages.WRONG_USERNAME);
		}
		catch (AuthenticationException e) {
			throw new LoginErrorException(AuthenticationErrorMessages.LOGIN_FAILED + " " + AuthenticationErrorMessages.UNKNOWN_ERROR);
		}
	}
	
	//E-Mail login
	public ResponseEntity<String> emailLogin(@Valid EMailLoginForm eMailLoginForm){
		return null;
	}
	
}
