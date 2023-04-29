package dev.vaem.legalservices.question.bytags;

import java.util.List;

import org.springframework.data.cassandra.repository.MapIdCassandraRepository;

public interface QuestionByTagRepository extends MapIdCassandraRepository<QuestionByTag> {

    // @AllowFiltering
    List<QuestionByTag> findAllByTag(Iterable<String> tag);

}
