package org.ccdak.practice.basicKafka.messaging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ccdak.practice.basicKafka.persistance.entity.EJobQueue;
import org.ccdak.practice.basicKafka.persistance.repository.JobQueueRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class JobScheduler {

  private final JobQueueRepository jobQueueRepository;
  private final JobWorkerService jobWorkerService;

  // Run frequently because "processJobAsync" returns instantly now
  @Scheduled(fixedDelay = 5000)
  public void poller() {
    // Optional: Loop here to fetch multiple jobs if the thread pool has space
    // For simple implementations, just fetching one per tick is fine if delay is low
    log.info("Polling for job queues ..... ");
    jobQueueRepository
        .findNextEligibleJob()
        .ifPresent(
            job -> {
              log.info("Dispatching job: {}", job.getId());

              // Mark as PROCESSING *before* async handoff to prevent double-fetch
              // (Assuming your findNextEligibleJob uses SKIP LOCKED)
              job.setStatus(EJobQueue.JobStatus.PROCESSING);
              jobQueueRepository.save(job);

              // Hand off to the thread pool
              jobWorkerService.processJobAsync(job);
            });
  }
}
