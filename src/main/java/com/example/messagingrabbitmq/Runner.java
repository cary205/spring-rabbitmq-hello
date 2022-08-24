package com.example.messagingrabbitmq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.messagingrabbitmq.fanout.FanoutProducer;
import com.example.messagingrabbitmq.routing.RoutingProducer;
import com.example.messagingrabbitmq.simplejson.SimpleJsonProducer;
import com.example.messagingrabbitmq.workqueue.WorkQueueProducer;

@Component
public class Runner implements CommandLineRunner {

	@Autowired
	private SimpleJsonProducer simpleJsonProducer;
	
	@Autowired
	private WorkQueueProducer workQueueProducer;

	@Autowired
	private FanoutProducer fanoutProducer;

	@Autowired
	private RoutingProducer routingProducer;

	@Override
	public void run(String... args) throws Exception {
		simpleJsonProducer.doWork();
		workQueueProducer.doWork();
		fanoutProducer.doWork();
		routingProducer.doWork();
	}

}
