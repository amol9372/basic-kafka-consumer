package org.ccdak.practice.basicKafka.persistance.entity;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(
    name = "job_queue",
    indexes = {@Index(name = "idx_pending_jobs", columnList = "status, execution_key")})
public class EJobQueue {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  @Column(name = "execution_key", nullable = false)
  String executionKey;

  /**
   * Maps PostgreSQL JSONB directly to Jackson JsonNode. Requires Hibernate 6+. For Spring Boot 2 /
   * Hibernate 5, you would need 'hypersistence-utils' or a custom Converter.
   */
  @JdbcTypeCode(SqlTypes.JSON)
  @Column(name = "payload", columnDefinition = "jsonb")
  JsonNode payload;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  JobStatus status;

  @Column(name = "lease_until")
  Instant leaseUntil;

  /**
   * Using 'updatable = false' ensures the DB default (NOW()) or the @PrePersist handles this,
   * protecting the audit trail.
   */
  @Column(name = "created_at", nullable = false, updatable = false)
  @Builder.Default
  Instant createdAt = Instant.now();

  public enum JobStatus {
    PENDING,
    PROCESSING,
    COMPLETED,
    FAILED
  }
}
