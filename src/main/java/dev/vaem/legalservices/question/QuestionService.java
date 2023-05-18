package dev.vaem.legalservices.question;

import java.time.Instant;
import java.util.Locale;
import java.util.Set;

import org.ocpsoft.prettytime.PrettyTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import dev.vaem.legalservices.user.UserAccount;

@Service
public class QuestionService {

    private int pageSize = 10;

    private PrettyTime prettyTime = new PrettyTime(Locale.of("ru"));

    @Autowired
    private QuestionRepository questionRepository;

    public Question get(String questionId) {
        var question = questionRepository.findById(questionId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        question.setPrettyDate(prettyTime.format(question.getDate()));
        return question;
    }

    @Cacheable("questions")
    public Page<Question> getByTag(Set<String> tags, int page) {
        var pageable = PageRequest.of(page, pageSize, Sort.by("date").descending());
        var questions = (tags == null || tags.isEmpty())
                ? questionRepository.findAll(pageable)
                : questionRepository.findByTagsContaining(tags, pageable);
        questions.forEach(q -> {
            if (q.getBody().length() > 100) {
                q.setBody(q.getBody().substring(0, 100) + "...");
            }
            q.setPrettyDate(prettyTime.format(q.getDate()));
        });
        return questions;
    }

    public Page<Question> getByUser(String userId, int page) {
        var pageable = PageRequest.of(page, pageSize, Sort.by("date").descending());
        var questionsByUser = questionRepository.findByUserId(userId, pageable);
        questionsByUser.forEach(q -> {
            if (q.getBody().length() > 100) {
                q.setBody(q.getBody().substring(0, 100) + "...");
            }
            q.setPrettyDate(prettyTime.format(q.getDate()));
        });
        return questionsByUser;
    }

    public String save(UserAccount me, Question question) {
        question.setUserEmail(me.getEmail());
        question.setUserId(me.getUserId());
        question.setDate(Instant.now());
        question.setTags(Set.of(question.getTextTags().split(",")));
        questionRepository.save(question);
        return question.getQuestionId();
    }

}
