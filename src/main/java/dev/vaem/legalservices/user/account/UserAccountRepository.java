package dev.vaem.legalservices.user.account;

import java.util.Optional;

import org.springframework.data.cassandra.repository.CassandraRepository;

public interface UserAccountRepository extends CassandraRepository<UserAccount, String> {

    Optional<UserAccount> findByEmail(String email);

}
