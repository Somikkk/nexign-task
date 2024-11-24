package com.microservice.nexign.olis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class OlisApplication {

	public static void main(String[] args) {
		SpringApplication.run(OlisApplication.class, args);
	}

}
