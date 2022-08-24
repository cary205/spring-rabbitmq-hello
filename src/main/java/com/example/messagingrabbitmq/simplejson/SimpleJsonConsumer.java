package com.example.messagingrabbitmq.simplejson;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class SimpleJsonConsumer {

    private static final Logger logger = LoggerFactory.getLogger(SimpleJsonConsumer.class);

	@RabbitListener(queues = SimpleJsonProducer.queueName)
    public void receive1(SampleMessage in) throws Exception {
        logger.debug(" [SimpleJsonConsumer] Received '" + in + "'");
		Thread.sleep(1000);
    }

}
