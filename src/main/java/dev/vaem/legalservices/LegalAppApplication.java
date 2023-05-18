package dev.vaem.legalservices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.mongock.runner.springboot.EnableMongock;

@SpringBootApplication
@EnableMongock
public class LegalAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(LegalAppApplication.class, args);
	}

}
