package com.all.azureservices;

//import com.all.azureservices.consumer.EventHubsEventsConsumer;
import com.all.azureservices.consumer.EventHubsEventsConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AzureServicesApplication implements CommandLineRunner {

	@Autowired
	private EventHubsEventsConsumer eventsConsumer;


	public static void main(String[] args) {
		SpringApplication.run(AzureServicesApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		eventsConsumer.consumeEvents();
	}
}
