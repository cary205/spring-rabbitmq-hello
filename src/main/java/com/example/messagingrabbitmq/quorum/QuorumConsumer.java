package com.example.messagingrabbitmq.quorum;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.example.messagingrabbitmq.SampleMessage;

@Component
public class QuorumConsumer {

    private static final Logger logger = LoggerFactory.getLogger(QuorumConsumer.class);

	@RabbitListener(queues = QuorumProducer.queueName)
    public void receive1(SampleMessage in) throws Exception {
        logger.debug(" [QuorumConsumer] Received '" + in + "'");
		Thread.sleep(1000);
    }

}
