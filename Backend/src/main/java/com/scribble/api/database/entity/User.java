package com.author.api.database.entity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.author.api.database.enums.UserRoles;
import com.author.api.error.errorMessages.EnumErrorMessages;
import com.author.api.error.exceptions.enumExceptions.UnknownEnumException;
import com.mongodb.lang.NonNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document
@Getter
@Setter
@NoArgsConstructor
public class User {
	
	@Id
	@NonNull
	private String id;
	
	@NonNull
	private String username;
	
	@NonNull
	private String password;
	
	@NonNull
	private List<String> roles = new ArrayList<String>();
	
	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	public void assignRole(UserRoles userRole) {
		
		String prefix = "ROLE_";
		
		switch(userRole) {
			case Developer: roles.add(prefix + "DEVELOPER"); break;
			case Admin: roles.add(prefix + "ADMIN"); break;
			case User: roles.add(prefix + "USER"); break;
			default: throw new UnknownEnumException(EnumErrorMessages.UNKNOWN); 
		}
	}
}
