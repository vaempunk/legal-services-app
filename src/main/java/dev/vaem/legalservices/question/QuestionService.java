package dev.vaem.legalservices.question;

import java.time.Instant;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.mapping.BasicMapId;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.datastax.oss.driver.api.core.uuid.Uuids;

import dev.vaem.legalservices.question.bytags.QuestionByTag;
import dev.vaem.legalservices.question.bytags.QuestionByTagRepository;
import dev.vaem.legalservices.question.byuser.QuestionByUser;
import dev.vaem.legalservices.question.byuser.QuestionByUserRepository;
import dev.vaem.legalservices.user.account.UserAccount;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private QuestionByUserRepository questionByUserRepository;

    @Autowired
    private QuestionByTagRepository questionByTagsRepository;

    @Autowired
    private QuestionMapper questionMapper;

    public Question get(UUID questionId) {
        var question = questionRepository.findById(questionId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        question.setDate(Instant.ofEpochMilli(Uuids.unixTimestamp(question.getQuestionId())));
        return question;
    }

    public Collection<QuestionByTag> getByTag(Set<String> tags) {
        var questions = (tags == null || tags.isEmpty())
                ? questionByTagsRepository.findAll()
                : questionByTagsRepository.findAllById(tags.stream().map(tag -> BasicMapId.id("tag", tag)).toList());
        Map<UUID, QuestionByTag> questionsDistinct = new LinkedHashMap<>();
        for (var question : questions) {
            var distinctQuestion = questionsDistinct.get(question.getQuestionId());
            if (distinctQuestion == null) {
                question.setTags(new HashSet<>());
                question.getTags().add(question.getTag());
                questionsDistinct.put(question.getQuestionId(), question);
            } else {
                distinctQuestion.getTags().add(question.getTag());
            }
        }
        var result = questionsDistinct.values();
        result.forEach(q -> q.setDate(Instant.ofEpochMilli(Uuids.unixTimestamp(q.getQuestionId()))));
        return result;
    }

    public List<QuestionByUser> getByUser(UUID userId) {
        var questionsByUser = questionByUserRepository.findAllByUserId(userId);
        questionsByUser.forEach(q -> q.setDate(Instant.ofEpochMilli(Uuids.unixTimestamp(q.getQuestionId()))));
        return questionsByUser;
    }

    public UUID save(UserAccount me, Question question) {
        question.setUserEmail(me.getEmail());
        question.setQuestionId(Uuids.timeBased());
        questionRepository.save(question);

        var questionByUser = questionMapper.toQuestionByUser(question);
        questionByUser.setUserId(me.getUserId());
        questionByUserRepository.save(questionByUser);

        var questionsByTag = questionMapper.toQuestionsByTag(question);
        questionByTagsRepository.saveAll(questionsByTag);

        return question.getQuestionId();
    }

}
