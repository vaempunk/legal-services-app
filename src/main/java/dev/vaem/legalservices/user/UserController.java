package dev.vaem.legalservices.user;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ResponseStatusException;

import com.datastax.oss.driver.api.core.uuid.Uuids;

import dev.vaem.legalservices.user.account.UserAccount;
import dev.vaem.legalservices.user.account.UserAccountRepository;

@Controller
public class UserController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserAccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users/me")
    public String getPersonalPage(@AuthenticationPrincipal UserAccount me) {
        return "redirect:/users/" + me.getUserId();
    }

    @GetMapping("/users/{uId}")
    public String getUserPage(@PathVariable("uId") UUID userId, Model model) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        user.setCreatedDate(Instant.ofEpochMilli(Uuids.unixTimestamp(userId)));
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
        account.setUserId(Uuids.timeBased());
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        account.setRoles(Set.of("USER"));
        account.setEnabled(true);
        accountRepository.save(account);

        var user = User.builder()
                .userId(account.getUserId())
                .email(account.getEmail())
                .firstname("")
                .lastname("")
                .build();
        userRepository.save(user);

        return "redirect:login";
    }

}
