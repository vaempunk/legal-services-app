package dev.vaem.legalservices.question;

import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface QuestionRepository extends MongoRepository<Question, String> {

    Page<Question> findByTagsContaining(Set<String> tags, Pageable pageable);

    Page<Question> findByUserId(String userId, Pageable pageable);

}
