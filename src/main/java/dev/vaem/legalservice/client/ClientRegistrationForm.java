package dev.vaem.legalservice.client;

import java.util.Set;

public record ClientRegistrationForm(
        String email,
        String password,
        String firstname,
        String lastname,
        String middlename,
        Set<String> languages) {
}
