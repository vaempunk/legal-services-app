package dev.vaem.legalservices.answer;

import java.time.Instant;

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
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document("answers")
public class Answer {

    @Id
    private String answerId;

    @Indexed
    private String questionId;

    private String userEmail;

    @NotBlank(message = "тело не может быть пустым")
    @Size(min = 3, max = 1000, message = "тело должно быть от 3 до 1000 символов")
    private String body;

    @Size(min = 0, max = 100, message = "TL;DR должно быть от 0 до 100 символов")
    private String tldr;

    private Integer rating;

    private Instant date;

    @Transient
    private String prettyDate;

}
