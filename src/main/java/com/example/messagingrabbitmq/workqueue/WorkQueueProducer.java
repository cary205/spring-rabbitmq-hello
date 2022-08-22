package com.example.messagingrabbitmq.workqueue;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WorkQueueProducer {

    private static final Logger logger = LoggerFactory.getLogger(WorkQueueProducer.class);

	static final String queueName = "work-queue";

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Bean(name = queueName)
	Queue queue() {
		return new Queue(queueName, true);
	}

	public void doWork() throws Exception {
		logger.debug("##### WorkQueueProducer #####");
		String msg = "";

		for(int i = 111; i < 1000; i += 111) {
			msg = i + " " + new Date();
			rabbitTemplate.convertAndSend(queueName, msg);
			logger.debug(" [WorkQueueProducer] Sent '" + msg + "'");
		}
	}

}
