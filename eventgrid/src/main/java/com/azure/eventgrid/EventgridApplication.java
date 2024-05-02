package com.azure.eventgrid;

import com.azure.eventgrid.listner.EventGridListner;
import com.azure.eventgrid.listner.InternalQueueListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EventgridApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(EventgridApplication.class, args);
	}

	@Autowired
	private EventGridListner eventGridListner;

	@Autowired
	private InternalQueueListener internalQueueListener;

	@Override
	public void run(String... args) throws Exception {
	eventGridListner.receiveQueueMessage();
	internalQueueListener.receiveQueueMessage();
	}
}
