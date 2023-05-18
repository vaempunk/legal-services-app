package dev.vaem.legalservices.answer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import dev.vaem.legalservices.user.UserAccount;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

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
            @ModelAttribute @Valid Answer answer,
            @AuthenticationPrincipal UserAccount me,
            @CookieValue(name = "ANSWERS", defaultValue = "0") Integer answers,
            HttpServletResponse response) {
        answerService.save(me.getEmail(), questionId, answer);
        incrementAnswerCookie(answers, response);
        return "redirect:/questions/" + questionId;
    }

    void incrementAnswerCookie(int answers, HttpServletResponse response) {
        var cookie = new Cookie("ANSWERS", String.valueOf(answers + 1));
        cookie.setPath("/");
        response.addCookie(cookie);
    }

}
