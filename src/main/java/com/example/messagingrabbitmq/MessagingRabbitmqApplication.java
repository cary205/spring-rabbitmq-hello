package com.example.messagingrabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.ConditionalRejectingErrorHandler;
import org.springframework.amqp.rabbit.listener.FatalExceptionStrategy;
import org.springframework.amqp.rabbit.retry.MessageRecoverer;
import org.springframework.amqp.rabbit.retry.RejectAndDontRequeueRecoverer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.util.ErrorHandler;

@SpringBootApplication
public class MessagingRabbitmqApplication {

	/* 
	@Bean
	SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
			MessageListenerAdapter listenerAdapter) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setQueueNames(queueName);
		container.setMessageListener(listenerAdapter);
		return container;
	}

	@Bean
	MessageListenerAdapter listenerAdapter(Receiver receiver) {
		return new MessageListenerAdapter(receiver, "receiveMessage");
	}
	*/

	public static void main(String[] args) throws InterruptedException {
		// SpringApplication.run(MessagingRabbitmqApplication.class, args).close();
		SpringApplication.run(MessagingRabbitmqApplication.class, args);
    }

	@Bean
    public MessageConverter converter(){
        return new Jackson2JsonMessageConverter();
    }

	@Bean
	public MessageRecoverer messageRecoverer() {
		return new MyRejectAndDontRequeueRecoverer();
	}

	public class MyRejectAndDontRequeueRecoverer extends RejectAndDontRequeueRecoverer {

		private final Logger logger = LoggerFactory.getLogger(getClass());

		@Override
		public void recover(Message message, Throwable cause) {
			logger.debug(">>> MyRejectAndDontRequeueRecoverer.recover()");
			super.recover(message, cause); // will throw AmqpRejectAndDontRequeueException, isFatal() won't run
		}
	}

	@Bean
	public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory,
			SimpleRabbitListenerContainerFactoryConfigurer configurer) {
		SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
		configurer.configure(factory, connectionFactory);
		factory.setErrorHandler(errorHandler()); // set ErrorHandler explicitly
		return factory;
	}

	@Bean
	public ErrorHandler errorHandler() {
		return new MyConditionalRejectingErrorHandler(new MyFatalExceptionStrategy());
	}

	public class MyConditionalRejectingErrorHandler extends ConditionalRejectingErrorHandler {

		private final Logger logger = LoggerFactory.getLogger(getClass());

		public MyConditionalRejectingErrorHandler(FatalExceptionStrategy exceptionStrategy) {
			super(exceptionStrategy);
		}

		@Override
		public void handleError(Throwable t) {
			logger.debug(">>> MyConditionalRejectingErrorHandler.handleError()");
			super.handleError(t); // ...or custom error handling
		}
	}

	public static class MyFatalExceptionStrategy extends ConditionalRejectingErrorHandler.DefaultExceptionStrategy {

		private final Logger logger = LoggerFactory.getLogger(getClass());

		@Override
		public boolean isFatal(Throwable t) {
			logger.debug(">>> MyFatalExceptionStrategy.isFatal()");
			// check Fatal Exception here...

			// if (t instanceof ListenerExecutionFailedException) {
			// 	ListenerExecutionFailedException lefe = (ListenerExecutionFailedException) t;
			// 	logger.error("Failed to process inbound message from queue "
			// 			+ lefe.getFailedMessage().getMessageProperties().getConsumerQueue()
			// 			+ "; failed message: " + lefe.getFailedMessage(), t);
			// }
			return super.isFatal(t);
		}

	}

}
