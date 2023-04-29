package dev.vaem.legalservices.answer.rating;

import java.util.UUID;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AnswerRatingRepository extends CassandraRepository<AnswerRating, UUID> {

    @Query("""
            UPDATE answer_ratings
            SET rating = rating + 1
            WHERE answerid = :answerId;
            """)
    void incrementRating(@Param("answerId") UUID answerId);

    @Query("""
            UPDATE answer_ratings
            SET rating = rating - 1
            WHERE answerid = :answerId;""")
    void decrementRating(@Param("answerId") UUID answerId);

}
