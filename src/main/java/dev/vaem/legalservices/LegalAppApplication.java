package dev.vaem.legalservices;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import dev.vaem.legalservices.user.UserController;
import dev.vaem.legalservices.user.account.UserAccount;

@SpringBootApplication
public class LegalAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(LegalAppApplication.class, args);
	}

	@Bean
	public CommandLineRunner initDb(
			UserController userController) {
		var user = new UserAccount();
		user.setEmail("a@b.c");
		user.setPassword("abc");
		return (args) -> {
			userController.register(user);
		};
	}

}
