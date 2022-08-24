package com.example.messagingrabbitmq.simplejson;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SimpleJsonProducer {

    private static final Logger logger = LoggerFactory.getLogger(SimpleJsonProducer.class);

	static final String queueName = "simple-json";

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Bean(name = queueName)
	Queue queue() {
		return new Queue(queueName, true);
	}

	public void doWork() throws Exception {
		logger.debug("##### SimpleJsonProducer #####");
		SampleMessage sm = new SampleMessage();

		for(int i = 111; i < 1000; i += 111) {
			sm = new SampleMessage();
			sm.setId(i);
			sm.setMessage("JOJOHOHO " + new Date());
			rabbitTemplate.convertAndSend(queueName, sm);
			logger.debug(" [SimpleJsonProducer] Sent '" + sm + "'");
		}
	}

}
