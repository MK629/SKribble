package com.sKribble.api.security.userDetails;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sKribble.api.database.entity.User;
import com.sKribble.api.database.repository.UserRepository;
import com.sKribble.api.messages.errorMessages.AuthenticationErrorMessages;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService{

	private final UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
		User loadedUser = userRepository.findUserByUsernameOrEmail(usernameOrEmail);
		if(loadedUser != null) {
			return new CustomUserDetails(loadedUser);
		}
		else {
			throw new UsernameNotFoundException(AuthenticationErrorMessages.USER_NON_EXISTENT);
		}
	}

}
