package dev.vaem.legalservice.client;

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
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/me")
    public String getClientPage(@AuthenticationPrincipal User user, Model model) {
        var client = clientRepository.findById(user.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        model.addAttribute("client", client);
        return "client.html";
    }
    
    @PostMapping(value="/register")
    public String registerClient(@ModelAttribute ClientRegistrationForm registrationForm) {
        var userId = UUID.randomUUID();

        var user = new User();
        user.setId(userId);
        user.setEmail(registrationForm.email());
        user.setPassword(passwordEncoder.encode(registrationForm.password()));
        user.setRoles(Collections.singleton("client"));
        user.setEnabled(true);
        userRepository.save(user);

        var client = new Client();
        client.setId(userId);
        client.setEmail(registrationForm.email());
        client.setFirstname(registrationForm.firstname());
        client.setLastname(registrationForm.lastname());
        client.setMiddlename(registrationForm.middlename());
        client.setLanguages(registrationForm.languages());
        client.setCreatedDate(Instant.now());
        clientRepository.save(client);

        return "login.html";
    }
    

}
