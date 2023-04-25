package dev.vaem.legalservices.question.byuser;

import java.util.List;
import java.util.UUID;

import org.springframework.data.cassandra.repository.MapIdCassandraRepository;

public interface QuestionByUserRepository extends MapIdCassandraRepository<QuestionByUser> {

    List<QuestionByUser> findAllByUserId(UUID userId);

}
