package com.sc.soulsync;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class SoulsyncApplication {

	public static void main(String[] args) {
		SpringApplication.run(SoulsyncApplication.class, args);
	}

}
