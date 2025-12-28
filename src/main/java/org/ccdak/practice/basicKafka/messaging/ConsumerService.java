package org.ccdak.practice.basicKafka.messaging;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ConsumerService {

//  @KafkaListener(topics = "orders", groupId = "alice-group")
//  public void consume(
//      List<ConsumerRecord<String, String>> batch,
//      Acknowledgment ack,
//      Consumer<String, String> consumer) {
//
//    for (ConsumerRecord<String, String> record : batch) {
//
//      // consumer.commitAsync();
//
//      log.info("Going to consume record: {}, from batch", record);
//      process(record);
//    }
//    ack.acknowledge();
//  }

  @KafkaListener(topics = {"orders-2", "orders"}, groupId = "alice-group")
  public void consume2(
          List<ConsumerRecord<String, Payload>> batch,
          Acknowledgment ack,
          Consumer<String, Payload> consumer) {

    for (ConsumerRecord<String, Payload> record : batch) {

      // consumer.commitAsync();

      log.info("Going to consume record: {}, from batch", record);
      // process(record);
    }
    ack.acknowledge();
  }

  public void process(ConsumerRecord<String, String> record) {
    log.info("Processing record: {}", record.value());
  }
}
