package dev.vaem.legalservices.question;

import java.util.List;
import java.util.Set;

import org.springframework.data.mongodb.repository.MongoRepository;


public interface QuestionRepository extends MongoRepository<Question, String> {

    List<Question> findByTagsContaining(Set<String> tags);

    List<Question> findByUserId(String userId);

}
