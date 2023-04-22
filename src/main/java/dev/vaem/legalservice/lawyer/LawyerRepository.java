package dev.vaem.legalservice.lawyer;

import java.util.UUID;

import org.springframework.data.cassandra.repository.CassandraRepository;

public interface LawyerRepository extends CassandraRepository<Lawyer, UUID> {
    
}
