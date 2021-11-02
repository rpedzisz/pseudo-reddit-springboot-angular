package com.example.pseudoreddit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class PseudoredditApplication {

	public static void main(String[] args) {
		SpringApplication.run(PseudoredditApplication.class, args);
	}

}
