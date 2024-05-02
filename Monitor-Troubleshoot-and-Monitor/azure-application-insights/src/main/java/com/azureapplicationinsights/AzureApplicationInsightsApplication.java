package com.azureapplicationinsights;

//import com.microsoft.applicationinsights.attach.ApplicationInsights;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AzureApplicationInsightsApplication {

	public static void main(String[] args) {
		//ApplicationInsights.attach();
		SpringApplication.run(AzureApplicationInsightsApplication.class, args);
	}

}
