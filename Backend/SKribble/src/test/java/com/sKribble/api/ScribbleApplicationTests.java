package com.sKribble.api;


import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import com.sKribble.api.database.repository.ProjectRepository;
import com.sKribble.api.database.repository.UserRepository;
import com.sKribble.api.security.jwt.JwtUtil;
import com.sKribble.api.security.userDetails.CustomUserDetailsService;
import com.sKribble.api.service.CredentialsService;
import com.sKribble.api.service.SKribbleCommonService;
import com.sKribble.api.service.SKribbleSongService;
import com.sKribble.api.service.SKribbleStoryService;

@SpringBootTest
@ActiveProfiles("test")
class ScribbleApplicationTests {

	@Autowired
	private ProjectRepository projectRepository;
	@Autowired
    private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtUtil jwtUtil;
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	@Autowired
	private CredentialsService credentialsService;
	@Autowired
	private SKribbleCommonService sKribbleCommonService;
	@Autowired
    private SKribbleStoryService sKribbleStoryService;
	@Autowired
    private SKribbleSongService sKribbleSongService;
	

	@Test
	void contextLoads() {
		assertAll(() -> {
			assertNotNull(projectRepository);
			assertNotNull(userRepository);
			assertNotNull(passwordEncoder);
			assertNotNull(authenticationManager);
			assertNotNull(jwtUtil);
			assertNotNull(customUserDetailsService);
			assertNotNull(credentialsService);
			assertNotNull(sKribbleCommonService);
			assertNotNull(sKribbleStoryService);
			assertNotNull(sKribbleSongService);
		});
	}
}
