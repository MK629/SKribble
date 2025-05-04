package com.sKribble.api.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
public class TestController {
	
	@Value("${SKribble.jwtToken}")
	String token;
	
	@QueryMapping
	public String greet() {
		return token;
	}
}
