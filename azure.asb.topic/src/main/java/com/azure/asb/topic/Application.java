package com.azure.asb.topic;

import com.azure.asb.topic.listner.TopicListner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application implements CommandLineRunner {

//	@Autowired
//	private TopicListner topicListner;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
		//topicListner.receiveSubscriptionMessage();
	}


}
