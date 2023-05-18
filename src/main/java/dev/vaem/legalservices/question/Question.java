package dev.vaem.legalservices.question;

import java.time.Instant;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document("questions")
public class Question {

    @Id
    private String questionId;

    @Indexed
    private String userId;

    private String userEmail;

    @NotBlank(message = "заголовок не может быть пустым")
    @Size(min = 3, max = 100, message = "заголовок должен быть от 3 до 100 символов")
    private String header;

    @NotBlank(message = "тело не может быть пустым")
    @Size(min = 3, max = 1000, message = "тело должно быть от 3 до 1000 символов")
    private String body;

    @Indexed
    private Set<String> tags;

    private Instant date;

    @Transient
    private String prettyDate;

    @Transient
    @Size(min = 3, max = 100, message = "теги должны быть от 3 до 100 символов")
    private String textTags;

}
