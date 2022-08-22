package com.example.messagingrabbitmq.fanout;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class FanoutConsumer {

    private static final Logger logger = LoggerFactory.getLogger(FanoutConsumer.class);

	@RabbitListener(queues = FanoutProducer.queueName1)
    public void receive1(String in) throws Exception {
        logger.debug(" [FanoutConsumer1] Received '" + in + "'");
		Thread.sleep(1000);
    }

    @RabbitListener(queues = FanoutProducer.queueName2)
    public void receive2(String in) throws Exception {
        logger.debug(" [FanoutConsumer2] Received '" + in + "'");
		Thread.sleep(2000);
    }

}
