package org.ccdak.practice.basicKafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@SpringBootApplication
@EnableScheduling
@EnableAsync
public class BasicKafkaConsumer {

	public static void main(String[] args) {
		SpringApplication.run(BasicKafkaConsumer.class, args);
	}

	@Bean(name = "jobTaskExecutor")
	public Executor jobTaskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(3); // Your concurrency setting
		executor.setMaxPoolSize(3);
		executor.setQueueCapacity(100); // Buffer for pending tasks
		executor.setThreadNamePrefix("JobWorker-");
		executor.initialize();
		return executor;
	}

}
