package dev.vaem.legalservices.configuration;

import java.util.Set;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;

import dev.vaem.legalservices.user.User;
import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackExecution;

@ChangeUnit(id = "admin-init", order = "1", author = "vaem")
public class DatabaseChangelog {

    // @Autowired
    // private MongoTemplate mongoTemplate;

    // @Autowired
    // private PasswordEncoder passwordEncoder;

    @Execution
    public void initAdmin(MongoTemplate mongoTemplate, PasswordEncoder passwordEncoder) {
        // save admin account
        var user = User.builder()
                .email("admin@legalservices")
                .password(passwordEncoder.encode("pass"))
                .roles(Set.of("user", "admin"))
                .build();
        mongoTemplate.insert(user, "users");
    }

    @RollbackExecution
    public void rollback() {

    }

}
