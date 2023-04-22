package dev.vaem.legalservice.user;

import java.util.Optional;

import org.springframework.data.cassandra.repository.CassandraRepository;

public interface UserRepository extends CassandraRepository<User, String> {

    Optional<User> findByEmail(String email);

}
