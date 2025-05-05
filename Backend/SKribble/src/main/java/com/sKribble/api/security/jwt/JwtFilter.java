package com.sKribble.api.security.jwt;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.sKribble.api.error.exceptions.credentialsExceptions.JwtTokenException;
import com.sKribble.api.messages.errorMessages.AuthenticationErrorMessages;
import com.sKribble.api.security.userDetails.CustomUserDetailsService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter{
	
	private final JwtUtil jwtUtil;
	private final CustomUserDetailsService customUserDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String authorizationHeader = request.getHeader("Authorization");
		
		if(authorizationHeader != null && !authorizationHeader.isBlank() && authorizationHeader.startsWith("Bearer ")) {
			String token = authorizationHeader.substring(7);
			
			Boolean isAuthenticated = jwtUtil.validateJWE(token);
			
			if(isAuthenticated) {
				UserDetails userDetails = customUserDetailsService.loadUserByUsername(jwtUtil.extractUsername(token));
				SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null, userDetails.getAuthorities()));
			}
			else{
				throw new JwtTokenException(AuthenticationErrorMessages.CORRUPTED_TOKEN);
			}
		}
		
		filterChain.doFilter(request, response);
	}
}
