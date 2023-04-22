package dev.vaem.legalservice.review;

import java.time.Instant;
import java.util.UUID;

import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

import lombok.Data;

@Data
public class Review {
    
    @PrimaryKeyColumn(name = "lawyer_id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private UUID lawyerId;

    @PrimaryKeyColumn(ordinal = 1, type = PrimaryKeyType.CLUSTERED, ordering = Ordering.DESCENDING)
    private Instant date;

    private byte rate;

    private String header;

    private String body;

    private String advantages;

    private String disadvantages;

}
