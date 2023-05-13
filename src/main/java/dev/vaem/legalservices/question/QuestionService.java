package dev.vaem.legalservices.question;

import java.time.Instant;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import dev.vaem.legalservices.user.UserAccount;

@Service
public class QuestionService {

    private int pageSize = 10;

    @Autowired
    private QuestionRepository questionRepository;

    public Question get(String questionId) {
        var question = questionRepository.findById(questionId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return question;
    }

    public Page<Question> getByTag(Set<String> tags, int page) {
        var pageable = PageRequest.of(page, pageSize);
        var questions = (tags == null || tags.isEmpty())
                ? questionRepository.findAll(pageable)
                : questionRepository.findByTagsContaining(tags, pageable);
        return questions;
    }

    public Page<Question> getByUser(String userId, int page) {
        var pageable = PageRequest.of(page, pageSize);
        var questionsByUser = questionRepository.findByUserId(userId, pageable);
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
