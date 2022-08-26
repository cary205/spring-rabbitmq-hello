package com.example.messagingrabbitmq.errorhandle;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.messagingrabbitmq.SampleMessage;

@Configuration
public class ErrorHandleProducer {

    private static final Logger logger = LoggerFactory.getLogger(ErrorHandleProducer.class);

	static final String queueName = "error-handle";

	static final String deadLetterQueueName = queueName + "-dlq";

	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Bean(name = queueName)
	Queue queue() {
		return QueueBuilder.nonDurable(queueName).autoDelete()
		.deadLetterExchange("") // The empty string denotes the default or nameless exchange: messages are routed to the queue with the name specified by routingKey
		.deadLetterRoutingKey(deadLetterQueueName)
		.build();
	}

	@Bean(name = deadLetterQueueName)
	Queue deadLetterQueue() {
		return new Queue(deadLetterQueueName, false, false, true);
	}

	public void doWork() throws Exception {
		logger.debug("##### ErrorHandleProducer #####");
		SampleMessage sm = new SampleMessage();

		sm = new SampleMessage();
		sm.setId(111);
		sm.setMessage("JOJOHOHO " + new Date());
		rabbitTemplate.convertAndSend(queueName, sm, m -> {
			return new Message("some bad json".getBytes(), m.getMessageProperties());
		});
		logger.debug(" [ErrorHandleProducer] Sent '" + sm + "'");
	}

}
