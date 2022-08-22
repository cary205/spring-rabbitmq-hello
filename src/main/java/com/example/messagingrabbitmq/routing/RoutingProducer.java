package com.example.messagingrabbitmq.routing;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RoutingProducer {

    private static final Logger logger = LoggerFactory.getLogger(RoutingProducer.class);

	static final String directExchangeName = "direct-exchange";

	static final String queueName1 = "direct1";

	static final String queueName2 = "direct2";

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Bean(name = directExchangeName)
    public DirectExchange direct() {
        return new DirectExchange(directExchangeName);
    }

	@Bean(name = queueName1)
	Queue queue1() {
		return new Queue(queueName1, true);
	}

	@Bean(name = queueName2)
	Queue queue2() {
		return new Queue(queueName2, true);
	}

	@Bean(name = "direct-apple")
	public Binding binding1() {
		return BindingBuilder.bind(queue1()).to(direct()).with("apple");
	}

	@Bean(name = "direct-orange")
	public Binding binding2() {
		return BindingBuilder.bind(queue2()).to(direct()).with("orange");
	}

	@Bean(name = "direct-banana")
	public Binding binding3() {
		return BindingBuilder.bind(queue1()).to(direct()).with("banana"); // banana = 1
	}

	public void doWork() throws Exception {
		logger.debug("##### RoutingProducer #####");
		String msg = "";

		for(int i = 111; i < 400; i += 111) {
			msg = i + " " + new Date();
			rabbitTemplate.convertAndSend(direct().getName(), "apple", "(apple) " + msg);
			logger.debug(" [RoutingProducer] Sent '" + msg + "'");

			rabbitTemplate.convertAndSend(direct().getName(), "orange", "(orange) " + msg);
			logger.debug(" [RoutingProducer] Sent '" + msg + "'");

			rabbitTemplate.convertAndSend(direct().getName(), "banana", "(banana) " + msg);
			logger.debug(" [RoutingProducer] Sent '" + msg + "'");
		}
	}

}
