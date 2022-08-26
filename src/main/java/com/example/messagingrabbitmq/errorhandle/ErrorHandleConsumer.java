package com.example.messagingrabbitmq.errorhandle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.example.messagingrabbitmq.SampleMessage;

@Component
public class ErrorHandleConsumer {

    private static final Logger logger = LoggerFactory.getLogger(ErrorHandleConsumer.class);

	@RabbitListener(queues = ErrorHandleProducer.queueName)
    public void receive1(SampleMessage in) throws Exception {
        logger.debug(" [ErrorHandleConsumer] Received '" + in + "'");
		Thread.sleep(1000);
    }

    @RabbitListener(queues = ErrorHandleProducer.deadLetterQueueName)
    public void failedMsgReceive(Message message) {
        logger.debug(" [ErrorHandleConsumer] Received failed message: {}", message.toString());
    }

}
