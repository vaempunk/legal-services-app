package dev.vaem.legalservices.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users/me")
    public String getPersonalPage(@AuthenticationPrincipal UserAccount me) {
        return "redirect:/users/" + me.getUserId();
    }

    @GetMapping("/users/{uId}")
    public String getUserPage(
            @PathVariable("uId") String userId,
            @CookieValue(name = "ANSWERS", defaultValue = "0") String answers,
            Model model) {
        var user = userService.get(userId);
        model.addAttribute("user", user);
        model.addAttribute("answers", answers);
        return "user/user";
    }

    @GetMapping("/admin")
    public String getUsers(Model model) {
        var users = userService.getAll();
        model.addAttribute("users", users);
        return "user/admin-panel";
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "user/login";
    }

    @GetMapping("/registration")
    public String getRegistrationPage(Model model) {
        model.addAttribute("user", new User());
        return "user/registration";
    }

    @PostMapping("/users")
    public String register(@ModelAttribute @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "user/registration";
        }
        userService.save(user);
        return "redirect:login";
    }

}
