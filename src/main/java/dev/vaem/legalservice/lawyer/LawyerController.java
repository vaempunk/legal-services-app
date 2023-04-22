package dev.vaem.legalservice.lawyer;

import java.time.Instant;
import java.util.Collections;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import dev.vaem.legalservice.user.User;
import dev.vaem.legalservice.user.UserRepository;

@Controller
@RequestMapping("/lawyer")
public class LawyerController {
    
    @Autowired
    private LawyerRepository lawyerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping()
    public String getLawyerPage(@AuthenticationPrincipal User user, Model model) {
        var lawyer = lawyerRepository.findById(user.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        model.addAttribute("lawyer", lawyer);
        return "lawyer.html";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute LawyerRegistrationForm registrationForm) {
        var userId = UUID.randomUUID();

        var user = new User();
        user.setId(userId);
        user.setEmail(registrationForm.email());
        user.setPassword(passwordEncoder.encode(registrationForm.password()));
        user.setEnabled(true);
        user.setRoles(Collections.singleton("lawyer"));
        userRepository.save(user);

        var lawyer = new Lawyer();
        lawyer.setId(userId);
        lawyer.setEmail(registrationForm.email());
        lawyer.setFirstname(registrationForm.firstname());
        lawyer.setLastname(registrationForm.lastname());
        lawyer.setMiddlename(registrationForm.middlename());
        lawyer.setLanguages(registrationForm.languages());
        lawyer.setCreatedDate(Instant.now());
        lawyer.setLastLoginDate(null);
        lawyerRepository.save(lawyer);

        return "login.html";
    }

}
