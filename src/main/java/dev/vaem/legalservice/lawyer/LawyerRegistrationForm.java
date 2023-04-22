package dev.vaem.legalservice.lawyer;

import java.util.Set;

public record LawyerRegistrationForm(
        String email,
        String password,
        String firstname,
        String lastname,
        String middlename,
        Set<String> languages,
        String companyName,
        long yearsExperience,
        String education,
        String license
) {
    
}
