package org.ccdak.practice.basicKafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafkaStreams;

@SpringBootApplication
@EnableKafkaStreams
public class BasicKafkaConsumer {

	public static void main(String[] args) {
		SpringApplication.run(BasicKafkaConsumer.class, args);
	}

}
