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
import com.sKribble.api.dto.input.user.EMailLoginForm;
import com.sKribble.api.dto.input.user.UserRegisterForm;
import com.sKribble.api.dto.input.user.UsernameLoginForm;
import com.sKribble.api.dto.output.user.TokenCarrier;
import com.sKribble.api.error.exceptions.CRUDExceptions.PersistenceErrorException;
import com.sKribble.api.error.exceptions.CRUDExceptions.UserRegstrationErrorException;
import com.sKribble.api.error.exceptions.credentialsExceptions.LoginErrorException;
import com.sKribble.api.error.exceptions.enumExceptions.UnknownEnumException;
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
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class CredentialsService {
	
	private final UserRepository userRepo;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	private final JwtUtil jwtUtil;

	@Transactional
	public ResponseEntity<String> register(@Valid UserRegisterForm userRegisterForm) {
		User user = new User(userRegisterForm.username(), userRegisterForm.email(), passwordEncoder.encode(userRegisterForm.password()));
		user.assignRole(UserRoles.User);
		
		try {
			userRepo.save(user);
		}
		catch(DuplicateKeyException e) {
			String message = "";
			
			//Fix later. Looks kinda stupid.
			if(e.getMessage().contains("username")) {
				message += InputErrorMessages.DUPLICATE_USERNAME;
			}
			
			if(e.getMessage().contains("email")) {
			    message += InputErrorMessages.DUPLICATE_EMAIL;
			}

			throw new UserRegstrationErrorException(message);
		}
		catch(UnknownEnumException e){
			log.error(e.getMessage());
			throw new UserRegstrationErrorException(CRUDErrorMessages.REGISTER_FAILED);
		}
		catch(Exception e) {
			throw new PersistenceErrorException(CRUDErrorMessages.REGISTER_FAILED, e);
		}
		
		return ResponseEntityUtil.return201(CRUDSuccessMessages.REGISTER_SUCCESS);
	}
	
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
			throw new LoginErrorException(AuthenticationErrorMessages.LOGIN_FAILED + " " + AuthenticationErrorMessages.WRONG_USERNAME);
		}
		catch (Exception e) {
			log.error(e.getMessage());
			throw new LoginErrorException(AuthenticationErrorMessages.LOGIN_FAILED + " " + AuthenticationErrorMessages.UNKNOWN_ERROR, e);
		}
	}
	
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
			throw new LoginErrorException(AuthenticationErrorMessages.LOGIN_FAILED + " " + AuthenticationErrorMessages.WRONG_EMAIL);
		}
		catch (Exception e) {
			log.error(e.getMessage());
			throw new LoginErrorException(AuthenticationErrorMessages.LOGIN_FAILED + " " + AuthenticationErrorMessages.UNKNOWN_ERROR, e);
		}
	}
}
