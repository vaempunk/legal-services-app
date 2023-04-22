package dev.vaem.legalservice.lawyer.bylangrating;

import org.springframework.data.cassandra.repository.MapIdCassandraRepository;

public interface LawyerByLanguageRatingRepository extends MapIdCassandraRepository<LawyerByLanguageRating> {
    
}
