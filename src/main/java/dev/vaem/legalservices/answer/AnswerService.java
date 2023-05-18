package dev.vaem.legalservices.answer;

import java.time.Instant;
import java.util.Locale;

import org.ocpsoft.prettytime.PrettyTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service
public class AnswerService {

    private int pageSize = 10;

    private PrettyTime prettyTime = new PrettyTime(Locale.of("ru"));

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public Page<Answer> getByQuestionId(String questionId, int page) {
        var pageable = PageRequest.of(page, pageSize);
        var answers = answerRepository.findByQuestionIdOrderByRatingDescDateDesc(questionId, pageable);
        answers.forEach(a -> {
            a.setPrettyDate(prettyTime.format(a.getDate()));
        });
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
