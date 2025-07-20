package com.ironbank.bankingsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling  // Enable scheduled tasks (such as applying interest)
@EntityScan(basePackages = "com.ironbank.bankingsystem.model")
@EnableJpaRepositories(basePackages = "com.ironbank.bankingsystem.repository")
public class IronBankApplication {

	public static void main(String[] args) {
		SpringApplication.run(IronBankApplication.class, args);
	}
}