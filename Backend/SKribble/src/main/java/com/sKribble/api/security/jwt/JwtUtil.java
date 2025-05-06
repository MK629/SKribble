package com.sKribble.api.security.jwt;

import java.util.Date;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.sKribble.api.error.exceptions.credentialsExceptions.JwtTokenException;
import com.sKribble.api.messages.errorMessages.AuthenticationErrorMessages;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
	
	@Value("${SKribble.jwtTokenKey}")
	private String secretTokenKey;
	
	@Value("${SKribble.jwtTokenIssuer}")
	private String issuer;
	
	@Value("${SKribble.jwt.encryptionKey}")
	private String encryptionKey;
	
	private SecretKey getSigningKey() {
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretTokenKey)); //On raw strings, Decoders.BASE64.decode() turns them into a byte[]
	}
	
	private SecretKey getEncryptionKey() {
		return new SecretKeySpec(Decoders.BASE64.decode(encryptionKey), "AES"); //This is the only correct constructor to use for custom encryption keys.
	}
	
	private String decryptToJWS(String token) {
		byte[] jwsBytesFormat = (byte[]) Jwts.parser()
											.decryptWith(getEncryptionKey())
											.build()
											.parse(token)  //Parse a JWE. Used to be called as parseContentJwe().
											.getPayload(); //Returns a JWS from a decrypted JWE. Method name is misleading.
		
		return new String(jwsBytesFormat); //Had to do this because of a bullshit exception.
	}
	
	public String generateJWE(String reciever) {
		//Build JWS
		String signedToken = Jwts.builder()
								.claims()
									.issuer(issuer)
									.issuedAt(new Date())
									.subject(reciever)
									.expiration(null)
									.and()
								.signWith(getSigningKey()) //Hashing algorithm is applied automatically
								.compact();
		
		//Encrypt to JWE
		return Jwts.builder().content(signedToken).encryptWith(getEncryptionKey(), Jwts.ENC.A256GCM).compact(); //Encrypt the signed token.
	}
	
	public String extractUsername(String token) {
		return Jwts.parser()
				.verifyWith(getSigningKey())
				.build()
				.parseSignedClaims(decryptToJWS(token))
				.getPayload()
				.getSubject();
	}
	
	public Boolean validateJWE(String token) {
		try {
			Jwts.parser()
			.verifyWith(getSigningKey())
			.build()
			.parseSignedClaims(decryptToJWS(token));
		}
		catch(JwtException e) {
			throw new JwtTokenException(AuthenticationErrorMessages.CORRUPTED_TOKEN);
		}
		catch (Exception e) {
			throw new JwtTokenException(e.getMessage());
		}
		return true;
	}
}
