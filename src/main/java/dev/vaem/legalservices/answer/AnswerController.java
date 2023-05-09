package dev.vaem.legalservices.answer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import dev.vaem.legalservices.user.UserAccount;

@Controller
public class AnswerController {

    @Autowired
    private AnswerService answerService;

    @PostMapping("/questions/{qId}/answers/{aId}/like")
    public String incrementAnswerRating(@PathVariable("qId") String questionId, @PathVariable("aId") String answerId) {
        answerService.incrementRating(answerId);
        return "redirect:/questions/" + questionId;
    }

    @PostMapping("/questions/{qId}/answers/{aId}/dislike")
    public String decrementAnswerRating(@PathVariable("qId") String questionId, @PathVariable("aId") String answerId) {
        answerService.decrementRating(answerId);
        return "redirect:/questions/" + questionId;
    }

    @PostMapping("/questions/{qId}/answers")
    public String addAnswer(
            @PathVariable("qId") String questionId,
            @ModelAttribute Answer answer,
            @AuthenticationPrincipal UserAccount me) {
        answerService.save(me.getEmail(), questionId, answer);
        return "redirect:/questions/" + questionId;
    }

}
