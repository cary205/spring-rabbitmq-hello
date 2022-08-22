package com.example.messagingrabbitmq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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

}
