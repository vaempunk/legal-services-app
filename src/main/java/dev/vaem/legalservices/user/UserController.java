package dev.vaem.legalservices.user;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import dev.vaem.legalservices.user.account.UserAccount;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users/me")
    public String getPersonalPage(@AuthenticationPrincipal UserAccount me) {
        return "redirect:/users/" + me.getUserId();
    }

    @GetMapping("/users/{uId}")
    public String getUserPage(@PathVariable("uId") UUID userId, Model model) {
        var user = userService.get(userId);
        model.addAttribute("user", user);
        return "user/user";
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "user/login";
    }

    @GetMapping("/registration")
    public String getRegistrationPage() {
        return "user/registration";
    }

    @PostMapping("/registration")
    public String register(@ModelAttribute UserAccount account) {
        userService.save(account);
        return "redirect:login";
    }

}
