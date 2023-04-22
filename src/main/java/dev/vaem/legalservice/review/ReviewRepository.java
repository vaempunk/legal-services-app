package dev.vaem.legalservice.review;

import org.springframework.data.cassandra.repository.MapIdCassandraRepository;

public interface ReviewRepository extends MapIdCassandraRepository<Review> {
    
}
