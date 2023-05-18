package dev.vaem.legalservices.user;

import java.time.Instant;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.ocpsoft.prettytime.PrettyTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService {

    private PrettyTime prettyTime = new PrettyTime(Locale.of("ru"));

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Authentication login(Authentication authentication) {
        var username = authentication.getName();
        var password = authentication.getCredentials().toString();
        var user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User %s not found".formatted(username)));
        if (!passwordEncoder.matches(password, user.getPassword())) {
            return null;
        }
        user.setLastLoginDate(Instant.now());
        userRepository.save(user);
        var userAccount = userMapper.userToAccount(user);
        return new UsernamePasswordAuthenticationToken(userAccount, null, userAccount.getAuthorities());
    }

    public User get(String userId) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        user.setPrettyCreatedDate(prettyTime.format(user.getCreatedDate()));
        user.setPrettyLastLoginDate(prettyTime.format(user.getLastLoginDate()));
        return user;
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public void save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Set.of("USER"));
        user.setCreatedDate(Instant.now());
        user.setLastLoginDate(null);
        userRepository.save(user);
    }

}
