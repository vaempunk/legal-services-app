package dev.vaem.legalservices.user;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.datastax.oss.driver.api.core.uuid.Uuids;

import dev.vaem.legalservices.user.account.UserAccount;
import dev.vaem.legalservices.user.account.UserAccountRepository;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserAccountRepository accountRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User get(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public void save(UserAccount account) {
        account.setUserId(Uuids.timeBased());
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        account.setRoles(Set.of("USER"));
        account.setEnabled(true);
        accountRepository.save(account);

        var user = userMapper.accountToUser(account);
        user.setCreatedDate(Instant.now());
        user.setFirstname("");
        user.setLastname("");
        user.setLastLoginDate(null);
        userRepository.save(user);
    }

}
