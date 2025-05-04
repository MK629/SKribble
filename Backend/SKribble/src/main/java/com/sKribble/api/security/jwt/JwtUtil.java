package com.sKribble.api.security.jwt;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
	
	@Value("${SKribble.jwtToken}")
	private String secretToken;
	
	@Value("${SKribble.jwtTokenIssuer}")
	private String issuer;
	
	private SecretKey tokenToKey() {
		return Keys.hmacShaKeyFor(secretToken.getBytes());
	}
	
	public String generateToken(String reciever) {
		return Jwts.builder()
				.claims()
					.issuer(issuer)
					.issuedAt(new Date())
					.subject(reciever)
				.and()
				.signWith(tokenToKey()) //Algorithm is applied automatically
				.compact();
	}
	
	public Boolean validateToken(String token) {
		
		return null;
	}
}
