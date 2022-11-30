package com.example.messagingrabbitmq.quorum;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.messagingrabbitmq.SampleMessage;

@Configuration
public class QuorumProducer {

    private static final Logger logger = LoggerFactory.getLogger(QuorumProducer.class);

	static final String queueName = "q.quorum-demo";

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Bean(name = queueName)
	Queue queue() {
		return new Queue(queueName, true);
	}

	@Bean(name = queueName)
	Queue quorumQueue() {
		Map<String, Object> arguments = new HashMap<>();
		arguments.put("x-queue-type", "quorum");
		// arguments.put("x-quorum-initial-group-size", 9);
		// return new Queue(queueName, true, false, false, Map.of("x-queue-type", "quorum"));
		return new Queue(queueName, true, false, false, arguments);
	}
	
	public void doWork() throws Exception {
		logger.debug("##### QuorumProducer #####");
		SampleMessage sm = new SampleMessage();

		for(int i = 111; i < 1000; i += 111) {
			sm = new SampleMessage();
			sm.setId(i);
			sm.setMessage("QuorumQuorum " + new Date());
			rabbitTemplate.convertAndSend(queueName, sm);
			logger.debug(" [QuorumProducer] Sent '" + sm + "'");
		}
	}

}
