package dev.vaem.legalservice.client;

import java.util.UUID;

import org.springframework.data.cassandra.repository.CassandraRepository;

public interface ClientRepository extends CassandraRepository<Client, UUID> {
}
