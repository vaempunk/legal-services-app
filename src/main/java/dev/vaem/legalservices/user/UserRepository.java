package dev.vaem.legalservices.user;

import java.util.UUID;

import org.springframework.data.cassandra.repository.CassandraRepository;

public interface UserRepository extends CassandraRepository<User, UUID> {

}
