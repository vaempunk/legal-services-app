package dev.vaem.legalservice.client;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.Table;

import lombok.Data;

@Data
@Table("clients")
public class Client {
    
    @Id
    private UUID id;

    private String email;

    private String firstname;

    private String lastname;

    private String middlename;

    private Set<String> languages;

    @Column("created_date")
    private Instant createdDate;

    @Column("last_login_date")
    private Instant lastLoginDate;

}
