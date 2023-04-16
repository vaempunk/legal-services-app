package dev.vaem.legalservice.model;

import java.time.OffsetDateTime;

public record User(
    String username,
    String password,
    String email,
    OffsetDateTime dateCreated
) {
}
