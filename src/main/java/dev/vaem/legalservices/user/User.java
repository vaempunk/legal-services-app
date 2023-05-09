package dev.vaem.legalservices.user;

import java.time.Instant;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document("users")
public class User {

    @Id
    private String userId;

    @Indexed(unique = true)
    private String email;

    private String firstname;

    private String lastname;

    private Instant lastLoginDate;

    private Instant createdDate;

    private String password;

    private Set<String> roles;

    private boolean enabled;

}
