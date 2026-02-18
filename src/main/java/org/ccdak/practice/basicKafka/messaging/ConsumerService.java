package org.ccdak.practice.basicKafka.messaging;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.ccdak.practice.basicKafka.persistance.entity.EJobQueue;
import org.ccdak.practice.basicKafka.persistance.repository.JobQueueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.concurrent.CompletableFuture;

import static java.time.temporal.ChronoUnit.MINUTES;

@Service
@Slf4j
public class ConsumerService {

  @Autowired JobQueueRepository jobQueueRepository;

  //    @KafkaListener(topics = "orders", groupId = "alice-group")
  //    public void consume(
  //        List<ConsumerRecord<String, String>> batch,
  //        Acknowledgment ack,
  //        Consumer<String, String> consumer) {
  //
  //      for (ConsumerRecord<String, String> record : batch) {
  //
  //        // consumer.commitAsync();
  //
  //        log.info("Going to consume record: {}, from batch", record);
  //        process(record);
  //      }
  //      ack.acknowledge();
  //    }

  //    @KafkaListener(topics = {"orders-1", "orders"}, groupId = "amol-group", concurrency = "1")
  //    public void consume2(
  //            List<ConsumerRecord<String, Payload>> batch,
  //            Acknowledgment ack,
  //            Consumer<String, Payload> consumer) {
  //
  //      for (ConsumerRecord<String, Payload> record : batch) {
  //
  //
  //        log.info("Going to consume record: {}, from batch", record);
  //        // process(record);
  //      }
  //      ack.acknowledge();
  //    }

  @KafkaListener(
      topics = {"orders-1"},
      groupId = "amol-group",
      concurrency = "3")
  public void consume2(ConsumerRecord<String, Payload> record) {

    CompletableFuture.runAsync(() -> process(record));
  }

  public void process(ConsumerRecord<String, Payload> record) {

//    ObjectMapper mapper = new ObjectMapper();
//    JsonNode jsonNode;
//    try {
//      jsonNode = mapper.readTree(record.value().toString());
//    } catch (JsonProcessingException e) {
//      throw new RuntimeException(e);
//    }

    // Mark the record as PENDING
    EJobQueue jobQueue =
        EJobQueue.builder()
            //.payload(jsonNode)
            .executionKey(record.key())
            .leaseUntil(Instant.now().plus(10, MINUTES))
            .createdAt(Instant.now())
            .status(EJobQueue.JobStatus.PENDING)
            .build();

    jobQueueRepository.save(jobQueue);
  }
}
