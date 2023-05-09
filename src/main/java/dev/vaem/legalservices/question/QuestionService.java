package dev.vaem.legalservices.question;

import java.time.Instant;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import dev.vaem.legalservices.user.UserAccount;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    public Question get(String questionId) {
        var question = questionRepository.findById(questionId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return question;
    }

    public List<Question> getByTag(Set<String> tags, int page) {
        var questions = (tags == null || tags.isEmpty())
                ? questionRepository.findAll()
                : questionRepository.findByTagsContaining(tags);
        return questions;
    }

    public List<Question> getByUser(String userId) {
        var questionsByUser = questionRepository.findByUserId(userId);
        return questionsByUser;
    }

    public String save(UserAccount me, Question question) {
        question.setUserEmail(me.getEmail());
        question.setUserId(me.getUserId());
        question.setDate(Instant.now());
        questionRepository.save(question);
        return question.getQuestionId();
    }

}
