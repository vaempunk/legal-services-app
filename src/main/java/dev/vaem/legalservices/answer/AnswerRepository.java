package dev.vaem.legalservices.answer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AnswerRepository extends MongoRepository<Answer, String> {

    Page<Answer> findByQuestionIdOrderByRatingDescDateDesc(String questionId, Pageable pageable);

    @Query("""
            db.answers.updateOne(
                {"answerId": :answerId},
                {
                    $inc: {"rating": 1}
                }
            )
            """)
    void incrementRating(@Param("answerId") String answerId);

    @Query("""
            db.answers.updateOne(
                {"answerId": ?0},
                {
                    $inc: {"rating": -1}
                }
            )
            """)
    void decrementRating(@Param("answerId") String answerId);

}
