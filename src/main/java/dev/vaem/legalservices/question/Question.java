package dev.vaem.legalservices.question;

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

    private String header;

    private String body;

    @Indexed
    private Set<String> tags;

    private Instant date;

}
