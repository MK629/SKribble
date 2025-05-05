package com.sKribble.api.database.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mongodb.lang.NonNull;
import com.sKribble.api.database.enums.UserRoles;
import com.sKribble.api.error.exceptions.enumExceptions.UnknownEnumException;
import com.sKribble.api.messages.errorMessages.EnumErrorMessages;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "users")
@Getter
@Setter
@NoArgsConstructor
public class User {
	
	@Id
	@NonNull
	private String id;
	
	@NonNull
	@Indexed(unique = true)
	private String username;
	
	@NonNull
	@Indexed(unique = true)
	private String email;
	
	@NonNull
	private String password;
	
	@NonNull
	private List<String> roles = new ArrayList<String>();
	
	public User(String username, String email, String password) {
		this.id = UUID.randomUUID().toString();
		this.username = username;
		this.email = email;
		this.password = password;
	}
	
	public void assignRole(UserRoles userRole) {
		
		String prefix = "ROLE_";
		
		switch(userRole) {
			case Developer: roles.add(prefix + "DEVELOPER"); break;
			case User: roles.add(prefix + "USER"); break;
			default: throw new UnknownEnumException(EnumErrorMessages.UNKNOWN);
		}
	}
}
