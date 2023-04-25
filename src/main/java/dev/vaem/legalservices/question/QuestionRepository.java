package dev.vaem.legalservices.question;

import java.util.UUID;

import org.springframework.data.cassandra.repository.CassandraRepository;

public interface QuestionRepository extends CassandraRepository<Question, UUID> {
}
