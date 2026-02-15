package org.ccdak.practice.basicKafka.messaging;

import com.google.common.base.Function;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.ValueMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ConsumerService {

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
      concurrency = "1")
  public void consume2(ConsumerRecord<String, Payload> record) {

    process(record);
  }

  public void process(ConsumerRecord<String, Payload> record) {

    Thread.sleep();

    log.info("Processing record: \n {} \n {}", record.key(), record.value());
  }
}
