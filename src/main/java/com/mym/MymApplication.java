package com.mym;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class MymApplication {

	public static void main(String[] args) {
		SpringApplication.run(MymApplication.class, args);
	}

}
