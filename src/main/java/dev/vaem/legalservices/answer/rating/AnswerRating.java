package dev.vaem.legalservices.answer.rating;

import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.CassandraType.Name;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("answer_ratings")
public class AnswerRating {
    
    @Id
    @Column("answer_id")
    @CassandraType(type = Name.TIMEUUID)
    private UUID answerId;

    @CassandraType(type = Name.COUNTER)
    @ReadOnlyProperty
    private long rating;

}
