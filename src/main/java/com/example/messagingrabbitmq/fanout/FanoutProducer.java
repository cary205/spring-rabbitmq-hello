package com.example.messagingrabbitmq.fanout;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FanoutProducer {

    private static final Logger logger = LoggerFactory.getLogger(FanoutProducer.class);

	static final String fanoutExchangeName = "fanout-exchange";

	static final String queueName1 = "fanout1";

	static final String queueName2 = "fanout2";

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Bean(name = fanoutExchangeName)
    public FanoutExchange fanout() {
        return new FanoutExchange(fanoutExchangeName);
    }

	@Bean(name = queueName1)
	Queue queue1() {
		return new Queue(queueName1, true);
	}

	@Bean(name = queueName2)
	Queue queue2() {
		return new Queue(queueName2, true);
	}

	@Bean
	public Binding binding1() {
		return BindingBuilder.bind(queue1()).to(fanout());
	}

	@Bean
	public Binding binding2() {
		return BindingBuilder.bind(queue2()).to(fanout());
	}

	public void doWork() throws Exception {
		logger.debug("##### FanoutProducer #####");
		String msg = "";

		for(int i = 111; i < 1000; i += 111) {
			msg = i + " " + new Date();
			rabbitTemplate.convertAndSend(fanout().getName(), "", msg);
			logger.debug(" [FanoutProducer] Sent '" + msg + "'");
		}
	}

}
