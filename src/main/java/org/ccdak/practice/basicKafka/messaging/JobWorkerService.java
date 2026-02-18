package org.ccdak.practice.basicKafka.messaging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ccdak.practice.basicKafka.persistance.entity.EJobQueue;
import org.ccdak.practice.basicKafka.persistance.repository.JobQueueRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class JobWorkerService {

  private final JobQueueRepository jobQueueRepository;

  @Async("jobTaskExecutor") // <--- This runs in a separate thread
  public void processJobAsync(EJobQueue job) {
    try {
      log.info("Processing job {} on thread {}", job.getId(), Thread.currentThread().getName());

      // 1. Update status to PROCESSING (if not done by poller)
      // Note: Better to do this in the poller transaction if possible to lock it fast
      // updateStatus(job, EJobQueue.JobStatus.PROCESSING);

      // 2. Run actual logic
      actualJob(job);

      // 3. Complete
      updateStatus(job, EJobQueue.JobStatus.COMPLETED);

    } catch (Exception e) {
      log.error("Job failed", e);
      updateStatus(job, EJobQueue.JobStatus.FAILED);
    }
  }

  private void actualJob(EJobQueue jobQueue) {
    log.info(
        "Processing record ..... \n {} \n {}", jobQueue.getExecutionKey(), jobQueue.getPayload());
    try {

      Thread.sleep(20000);
    } catch (InterruptedException e) {
      log.error(e.getMessage());
    } finally {
      log.info("****** Finished ******** {}", jobQueue.getExecutionKey());
    }
  }

  private void updateStatus(EJobQueue job, EJobQueue.JobStatus status) {
    job.setStatus(status);
    jobQueueRepository.save(job);
  }
}
