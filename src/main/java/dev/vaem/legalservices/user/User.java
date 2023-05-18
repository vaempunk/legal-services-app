package dev.vaem.legalservices.user;

import java.time.Instant;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
    @Email(message = "электронная почта должна иметь правильный формат")
    @NotBlank(message = "электронная почта не может быть пустой")
    private String email;

    @NotBlank(message = "имя не может быть пустым")
    @Size(min = 2, max = 35, message = "имя должно быть от 2 до 35 символов")
    private String firstname;
    @NotBlank(message = "имя не может быть пустым")
    @Size(min = 2, max = 35, message = "фамилия должна быть от 2 до 35 символов")
    private String lastname;

    private Instant lastLoginDate;
    private Instant createdDate;

    @Pattern(regexp = "^((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[^a-zA-Z0-9]).{8,})$", message = "пароль должен содержать цифру, букву (строчную и заглавную), символ и быть минимум 8 символов")
    private String password;

    private Set<String> roles;

    private String prettyLastLoginDate;
    private String prettyCreatedDate;

}
