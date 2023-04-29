package dev.vaem.legalservices.answer;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import dev.vaem.legalservices.user.account.UserAccount;

@Controller
public class AnswerController {

    @Autowired
    private AnswerService answerService;

    @PatchMapping("/answers/{aId}/like")
    @ResponseBody
    public void incrementAnswerRating(@PathVariable("aId") UUID answerId) {
        answerService.incrementRating(answerId);
    }

    @PatchMapping("/answers/{aId}/dislike")
    @ResponseBody
    public void decrementAnswerRating(@PathVariable("aId") UUID answerId) {
        answerService.decrementRating(answerId);
    }

    @PostMapping("/questions/{qId}/answers")
    public String addAnswer(
            @PathVariable("qId") UUID questionId,
            @ModelAttribute Answer answer,
            @AuthenticationPrincipal UserAccount me) {
        answerService.save(me.getEmail(), questionId, answer);
        return "redirect:/questions/" + questionId;
    }

}
