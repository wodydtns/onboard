package com.superboard.onbrd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class OnbrdApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnbrdApplication.class, args);
	}
	
}
