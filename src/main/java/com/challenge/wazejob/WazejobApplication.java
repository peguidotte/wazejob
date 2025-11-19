package com.challenge.wazejob;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class WazejobApplication {

	public static void main(String[] args) {
		SpringApplication.run(WazejobApplication.class, args);
	}

}
