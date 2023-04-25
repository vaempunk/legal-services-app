package dev.vaem.legalservices.question;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import com.datastax.oss.driver.api.core.uuid.Uuids;

import dev.vaem.legalservices.question.bytags.QuestionByTag;
import dev.vaem.legalservices.question.bytags.QuestionByTagRepository;
import dev.vaem.legalservices.question.byuser.QuestionByUser;
import dev.vaem.legalservices.question.byuser.QuestionByUserRepository;
import dev.vaem.legalservices.user.account.UserAccount;

// TODO: pagination
@Controller
public class QuestionController {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private QuestionByUserRepository questionByUserRepository;

    @Autowired
    private QuestionByTagRepository questionByTagsRepository;

    @GetMapping("/questions/{qId}")
    public String getQuestion(@PathVariable("qId") UUID questionId, Model model) {
        var question = questionRepository.findById(questionId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        question.setDate(Instant.ofEpochMilli(Uuids.unixTimestamp(question.getQuestionId())));
        model.addAttribute("q", question);
        return "question/question";
    }

    @GetMapping("/questions")
    public String getQuestions(@RequestParam(name = "tag") Optional<String> tag, Model model) {
        if (tag.isPresent()) {
            var questions = questionByTagsRepository.findByTagsContaining(tag.get());
            questions.forEach(q -> q.setDate(Instant.ofEpochMilli(Uuids.unixTimestamp(q.getQuestionId()))));
            model.addAttribute("questions", questions);
        } else {
            var questions = questionByTagsRepository.findAll();
            questions.forEach(q -> q.setDate(Instant.ofEpochMilli(Uuids.unixTimestamp(q.getQuestionId()))));
            model.addAttribute("questions", questions);
        }
        return "question/questions";
    }

    @GetMapping("/users/{uId}/questions")
    public String getQuestionsByUser(@PathVariable("uId") UUID userId, Model model) {
        var questionsByUser = questionByUserRepository.findAllByUserId(userId);
        questionsByUser.forEach(q -> q.setDate(Instant.ofEpochMilli(Uuids.unixTimestamp(q.getQuestionId()))));
        model.addAttribute("questions", questionsByUser);
        return "question/questions-by-user";
    }

    @GetMapping("/users/me/questions")
    public String getMyQuestions(@AuthenticationPrincipal UserAccount me) {
        return "redirect:/users/%s/questions".formatted(me.getUserId().toString());
    }

    @PostMapping("/questions")
    public String addQuestion(@AuthenticationPrincipal UserAccount me, @ModelAttribute Question question) {
        question.setUserEmail(me.getEmail());
        question.setQuestionId(Uuids.timeBased());
        questionRepository.save(question);

        var questionByUser = QuestionByUser.builder()
                .userId(me.getUserId())
                .questionId(question.getQuestionId())
                .header(question.getHeader())
                .tags(question.getTags())
                .build();
        questionByUserRepository.save(questionByUser);

        var questionByTags = QuestionByTag.builder()
                .tags(question.getTags())
                .questionId(question.getQuestionId())
                .userEmail(question.getUserEmail())
                .header(question.getHeader())
                .build();
        questionByTagsRepository.save(questionByTags);

        return "redirect:questions/" + question.getQuestionId();
    }

}
