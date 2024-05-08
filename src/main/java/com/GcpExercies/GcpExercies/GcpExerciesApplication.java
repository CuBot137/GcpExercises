package com.GcpExercies.GcpExercies;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class GcpExerciesApplication {

	public static void main(String[] args) {
		SpringApplication.run(GcpExerciesApplication.class, args);
	}

	@GetMapping("get")
	public String handleGetRequest() {
		return "Hello from Cloud Function! (Responding to a GET request)";
	}
	// test

}
