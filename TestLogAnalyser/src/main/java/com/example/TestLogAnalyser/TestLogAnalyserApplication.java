package com.example.TestLogAnalyser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TestLogAnalyserApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestLogAnalyserApplication.class, args);
	}

}
