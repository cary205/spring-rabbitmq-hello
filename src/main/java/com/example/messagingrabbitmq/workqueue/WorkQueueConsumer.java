package com.example.messagingrabbitmq.workqueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class WorkQueueConsumer {

    private static final Logger logger = LoggerFactory.getLogger(WorkQueueConsumer.class);

	@RabbitListener(queues = WorkQueueProducer.queueName)
    public void receive1(String in) throws Exception {
        logger.debug(" [WorkQueueConsumer1] Received '" + in + "'");
		Thread.sleep(1000);
    }

    @RabbitListener(queues = WorkQueueProducer.queueName)
    public void receive2(String in) throws Exception {
        logger.debug(" [WorkQueueConsumer2] Received '" + in + "'");
		Thread.sleep(2000);
    }

}
