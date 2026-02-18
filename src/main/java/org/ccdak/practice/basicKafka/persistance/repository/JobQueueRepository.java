package org.ccdak.practice.basicKafka.persistance.repository;

import org.ccdak.practice.basicKafka.persistance.entity.EJobQueue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JobQueueRepository extends JpaRepository<EJobQueue, Integer> {

    @Query(value = """
        SELECT * FROM job_queue j
        WHERE j.status = 'PENDING'
        AND NOT EXISTS (
            SELECT 1 FROM job_queue active
            WHERE active.execution_key = j.execution_key
            AND active.status = 'PROCESSING'
            AND active.lease_until > NOW() -- Only count valid active jobs
        )
        ORDER BY j.created_at ASC
        LIMIT 1
        FOR UPDATE SKIP LOCKED -- Allows multiple threads to pick different keys concurrently
    """, nativeQuery = true)
    Optional<EJobQueue> findNextEligibleJob();
}
