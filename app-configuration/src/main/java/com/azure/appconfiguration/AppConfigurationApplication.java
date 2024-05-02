package com.azure.appconfiguration;

import com.azure.appconfiguration.config.MessageConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(MessageConfig.class)
public class AppConfigurationApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppConfigurationApplication.class, args);
	}

}
