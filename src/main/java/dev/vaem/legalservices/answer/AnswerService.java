package dev.vaem.legalservices.answer;

import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service
public class AnswerService {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Answer> getByQuestionId(String questionId) {
        var answers = answerRepository.findByQuestionIdOrderByRatingDesc(questionId);
        return answers;
    }

    public void incrementRating(String answerId) {
        var query = new Query(Criteria.where("_id").is(answerId));
        var update = new Update().inc("rating", 1);
        mongoTemplate.updateFirst(
                query,
                update,
                Answer.class);
    }

    public void decrementRating(String answerId) {
        var query = new Query(Criteria.where("_id").is(answerId));
        var update = new Update().inc("rating", -1);
        mongoTemplate.updateFirst(
                query,
                update,
                Answer.class);
    }

    public void save(String userEmail, String questionId, Answer answer) {
        answer.setUserEmail(userEmail);
        answer.setQuestionId(questionId);
        answer.setDate(Instant.now());
        answer.setRating(0);
        answerRepository.save(answer);
    }

}
