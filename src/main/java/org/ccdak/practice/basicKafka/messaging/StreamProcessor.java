package org.ccdak.practice.basicKafka.messaging;

import org.apache.kafka.streams.kstream.KStream;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class StreamProcessor {

//  @Bean
//  public Consumer<KStream<String, Payload>> processOrder() {
//    return input ->
//        input
//            .filter((key, payload) -> payload.getCity().equals("New York"))
//            .mapValues(payload -> "Processed High Value: " + payload.getMessage())
//            .foreach((key, value) -> System.out.println(value));
//  }
}
