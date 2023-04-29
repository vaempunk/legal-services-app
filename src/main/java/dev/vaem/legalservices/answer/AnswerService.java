package dev.vaem.legalservices.answer;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.datastax.oss.driver.api.core.uuid.Uuids;

import dev.vaem.legalservices.answer.rating.AnswerRating;
import dev.vaem.legalservices.answer.rating.AnswerRatingRepository;

@Service
public class AnswerService {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private AnswerRatingRepository answerRatingRepository;

    public List<Answer> getByQuestionId(UUID questionId) {
        var answers = answerRepository.findByQuestionIdOrderByAnswerIdDesc(questionId);
        var answerRatings = answerRatingRepository.findAllById(answers.stream().map(Answer::getAnswerId).toList())
                .stream()
                .collect(Collectors.toMap(AnswerRating::getAnswerId, AnswerRating::getRating));
        answers.forEach(answer -> {
            answer.setDate(Instant.ofEpochMilli(Uuids.unixTimestamp(answer.getAnswerId())));
            answer.setRating(answerRatings.get(answer.getAnswerId()));
        });
        return answers;
    }

    public void incrementRating(UUID answerId) {
        answerRatingRepository.incrementRating(answerId);
    }

    public void decrementRating(UUID answerId) {
        answerRatingRepository.decrementRating(answerId);
    }

    public void save(String userEmail, UUID questionId, Answer answer) {
        answer.setAnswerId(Uuids.timeBased());
        answer.setUserEmail(userEmail);
        answer.setQuestionId(questionId);
        answerRepository.save(answer);
    }

}
