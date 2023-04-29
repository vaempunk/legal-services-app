package dev.vaem.legalservices.answer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.cassandra.repository.MapIdCassandraRepository;

public interface AnswerRepository extends MapIdCassandraRepository<Answer> {

    Optional<Answer> findByAnswerId(UUID answerId);

    // @AllowFiltering
    List<Answer> findByQuestionIdOrderByAnswerIdDesc(UUID questionId);

}
