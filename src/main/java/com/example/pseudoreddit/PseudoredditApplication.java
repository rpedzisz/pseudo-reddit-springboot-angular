package com.example.pseudoreddit;

import com.example.pseudoreddit.config.SwaggerConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@Import(SwaggerConfiguration.class)
public class PseudoredditApplication {

	public static void main(String[] args) {
		SpringApplication.run(PseudoredditApplication.class, args);
	}

}
