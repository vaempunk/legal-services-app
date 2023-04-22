package dev.vaem.legalservice;

import java.time.Instant;
import java.util.Collections;
import java.util.UUID;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import dev.vaem.legalservice.client.Client;
import dev.vaem.legalservice.client.ClientRepository;
import dev.vaem.legalservice.user.User;
import dev.vaem.legalservice.user.UserRepository;

@SpringBootApplication
public class LegalAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(LegalAppApplication.class, args);
	}

	@Bean
	public CommandLineRunner initDb(UserRepository userRepository, ClientRepository clientRepository, PasswordEncoder passwordEncoder) {
		var userId = UUID.randomUUID();
		var user = new User();
		user.setId(userId);
		user.setEmail("a@b.c");
		user.setPassword(passwordEncoder.encode("abc"));
		user.setEnabled(true);
		user.setRoles(Collections.singleton("client"));

		var client = new Client();
		client.setId(userId);
		client.setCreatedDate(Instant.now());
		client.setEmail("a@b.c");
		client.setFirstname("vaem");
		client.setLanguages(Collections.singleton("RU"));
		client.setLastLoginDate(Instant.now());
		client.setLastname("idiyatov");
		client.setMiddlename("ildarych");

		return (args) -> {
			userRepository.save(user);
			clientRepository.save(client);
		};
	}

}
