package com.example.messagingrabbitmq.routing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RoutingConsumer {

    private static final Logger logger = LoggerFactory.getLogger(RoutingConsumer.class);

	@RabbitListener(queues = RoutingProducer.queueName1)
    public void receive1(String in) throws Exception {
        logger.debug(" [RoutingConsumer1] Received '" + in + "'");
		Thread.sleep(1000);
    }

    @RabbitListener(queues = RoutingProducer.queueName2)
    public void receive2(String in) throws Exception {
        logger.debug(" [RoutingConsumer2] Received '" + in + "'");
		Thread.sleep(2000);
    }

}
