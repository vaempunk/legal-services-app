package dev.vaem.legalservices.question;

import java.util.Collections;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import dev.vaem.legalservices.answer.AnswerService;
import dev.vaem.legalservices.user.account.UserAccount;

@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private AnswerService answerService;

    @GetMapping("/questions/{qId}")
    public String getQuestion(@PathVariable("qId") UUID questionId, Model model) {
        var question = questionService.get(questionId);
        model.addAttribute("question", question);

        var answers = answerService.getByQuestionId(questionId);
        model.addAttribute("answers", answers);

        return "question/question";
    }

    @GetMapping("/questions")
    public String getQuestions(@RequestParam(name = "tag", required = false) Set<String> tags, Model model) {
        var questions = questionService.getByTag(tags);
        model.addAttribute("questions", questions);
        return "question/questions";
    }

    @GetMapping("/users/{uId}/questions")
    public String getQuestionsByUser(@PathVariable("uId") UUID userId, Model model) {
        var questionsByUser = questionService.getByUser(userId);
        model.addAttribute("questions", questionsByUser);
        return "question/questions-by-user";
    }

    @GetMapping("/users/me/questions")
    public String getMyQuestions(@AuthenticationPrincipal UserAccount me) {
        return "redirect:/users/%s/questions".formatted(me.getUserId().toString());
    }

    @PostMapping("/questions")
    public String addQuestion(@AuthenticationPrincipal UserAccount me, @ModelAttribute Question question) {
        var questionId = questionService.save(me, question);
        return "redirect:questions/" + questionId;
    }

}
