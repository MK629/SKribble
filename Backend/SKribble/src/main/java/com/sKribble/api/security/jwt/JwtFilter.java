package com.sKribble.api.security.jwt;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
import lombok.extern.log4j.Log4j2;

@Component
@RequiredArgsConstructor
@Log4j2
public class JwtFilter extends OncePerRequestFilter{
	
	private final JwtUtil jwtUtil;
	private final CustomUserDetailsService customUserDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			String authorizationHeader = request.getHeader("Authorization");
			
			if(authorizationHeader != null && !authorizationHeader.isBlank() && authorizationHeader.startsWith("Bearer ")) {
				
				String token = authorizationHeader.substring(7);
				
				if(jwtUtil.validateJWE(token)) {
					UserDetails userDetails = customUserDetailsService.loadUserByUsername(jwtUtil.extractUsername(token));
					SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null, userDetails.getAuthorities()));
				}
			}
			
			filterChain.doFilter(request, response);
		}
		catch(JwtTokenException e) {
			response.setStatus(401);
			response.getWriter().write(e.getMessage());
			log.error(e.getMessage());
		}
		catch(UsernameNotFoundException e){
			response.setStatus(401);
			response.getWriter().write(AuthenticationErrorMessages.UNKNOWN_ERROR + " " + AuthenticationErrorMessages.TRY_AGAIN);
			log.error(e.getMessage());
		}
		catch(Exception e) {
			response.setStatus(401);
			response.getWriter().write(e.getMessage());
			log.error(e.getMessage());
		}
	}
}
