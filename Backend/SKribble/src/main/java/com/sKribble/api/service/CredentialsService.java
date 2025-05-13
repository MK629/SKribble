package com.sKribble.api.service;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sKribble.api.database.entity.User;
import com.sKribble.api.database.entity.enums.UserRoles;
import com.sKribble.api.database.repository.UserRepository;
import com.sKribble.api.dto.input.EMailLoginForm;
import com.sKribble.api.dto.input.UserRegisterForm;
import com.sKribble.api.dto.input.UsernameLoginForm;
import com.sKribble.api.dto.output.TokenCarrier;
import com.sKribble.api.error.exceptions.CRUDExceptions.UserRegstrationErrorException;
import com.sKribble.api.error.exceptions.credentialsExceptions.LoginErrorException;
import com.sKribble.api.messages.errorMessages.AuthenticationErrorMessages;
import com.sKribble.api.messages.errorMessages.CRUDErrorMessages;
import com.sKribble.api.messages.errorMessages.InputErrorMessages;
import com.sKribble.api.messages.successMessages.AuthenticationSuccessMessages;
import com.sKribble.api.messages.successMessages.CRUDSuccessMessages;
import com.sKribble.api.security.jwt.JwtUtil;
import com.sKribble.api.utils.DTOConverter;
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
	@Transactional
	public ResponseEntity<String> register(@Valid UserRegisterForm userRegisterForm) {
		
		try {
			User user = new User(userRegisterForm.username(), userRegisterForm.email(), passwordEncoder.encode(userRegisterForm.password()));
			user.assignRole(UserRoles.User);
			userRepo.save(user);
		}
		catch(DuplicateKeyException e) {
			
			String message = "";
			
			//Fix later
			if(e.getMessage().contains("username")) {
				message += InputErrorMessages.DUPLICATE_USERNAME;
			}
			
			if(e.getMessage().contains("email")) {
			    message += InputErrorMessages.DUPLICATE_EMAIL;
			}

			throw new UserRegstrationErrorException(CRUDErrorMessages.REGISTER_FAILED, new DuplicateKeyException(message));
		}
		catch(Exception e) {
			throw new UserRegstrationErrorException(CRUDErrorMessages.REGISTER_FAILED, e);
		}
		
		return ResponseEntityUtil.return201(CRUDSuccessMessages.REGISTER_SUCCESS);
	}
	
	//User name login
	public ResponseEntity<TokenCarrier> usernameLogin(@Valid UsernameLoginForm usernameLoginForm){
		
		try {
			Authentication authenticationStatus = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(usernameLoginForm.username(), usernameLoginForm.password()));
			
			if(authenticationStatus.isAuthenticated()) {
				String token = jwtUtil.generateJWE(authenticationStatus.getName());
				return ResponseEntityUtil.returnToken(DTOConverter.makeTokenCarrier(AuthenticationSuccessMessages.USERNAME_LOGIN_SUCCESS, token));
			}
			else {
				throw new LoginErrorException( AuthenticationErrorMessages.UNKNOWN_ERROR + " " + AuthenticationErrorMessages.TRY_AGAIN);
			}
		}
		catch (BadCredentialsException e) {
			throw new LoginErrorException(AuthenticationErrorMessages.LOGIN_FAILED + " " + AuthenticationErrorMessages.WRONG_USERNAME, e);
		}
		catch (Exception e) {
			throw new LoginErrorException(AuthenticationErrorMessages.LOGIN_FAILED + " " + AuthenticationErrorMessages.UNKNOWN_ERROR, e);
		}
	}
	
	//E-Mail login
	public ResponseEntity<TokenCarrier> emailLogin(@Valid EMailLoginForm eMailLoginForm){
		try {
			Authentication authenticationStatus = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(eMailLoginForm.email(), eMailLoginForm.password()));
			
			if(authenticationStatus.isAuthenticated()) {
				String token = jwtUtil.generateJWE(authenticationStatus.getName());
				return ResponseEntityUtil.returnToken(DTOConverter.makeTokenCarrier(AuthenticationSuccessMessages.EMAIL_LOGIN_SUCCESS, token));
			}
			else {
				throw new LoginErrorException( AuthenticationErrorMessages.UNKNOWN_ERROR + " " + AuthenticationErrorMessages.TRY_AGAIN);
			}
		}
		catch (BadCredentialsException e) {
			throw new LoginErrorException(AuthenticationErrorMessages.LOGIN_FAILED + " " + AuthenticationErrorMessages.WRONG_EMAIL, e);
		}
		catch (Exception e) {
			throw new LoginErrorException(AuthenticationErrorMessages.LOGIN_FAILED + " " + AuthenticationErrorMessages.UNKNOWN_ERROR, e);
		}
	}
}
