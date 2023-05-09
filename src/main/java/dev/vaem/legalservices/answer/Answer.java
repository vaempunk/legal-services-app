package dev.vaem.legalservices.answer;

import java.time.Instant;

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
@Document("answers")
public class Answer {

    @Id
    private String answerId;

    @Indexed
    private String questionId;

    private String userEmail;

    private String body;

    private String tldr;

    private Integer rating;

    private Instant date;

}
