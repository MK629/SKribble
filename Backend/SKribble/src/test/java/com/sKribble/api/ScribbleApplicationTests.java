package com.sKribble.api;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class ScribbleApplicationTests {

	@Test
	void contextLoads() {
		String hello = "hello";
		assertNotNull(hello);
	}
}
