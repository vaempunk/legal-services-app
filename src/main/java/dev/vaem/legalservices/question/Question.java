package dev.vaem.legalservices.question;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.Frozen;
import org.springframework.data.cassandra.core.mapping.CassandraType.Name;
import org.springframework.data.cassandra.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table("questions")
public class Question {

    @Id
    @Column("question_id")
    @CassandraType(type = Name.TIMEUUID)
    private UUID questionId;

    @Column("user_email")
    private String userEmail;

    private String header;

    private String body;
    
    @Frozen
    private Set<String> tags;

    @Transient
    private Instant date;

}
