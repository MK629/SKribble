package com.author.api.controller;

import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
public class TestController {
	
	@QueryMapping
	public String greet() {
		return "Hello World.";
	}
}
