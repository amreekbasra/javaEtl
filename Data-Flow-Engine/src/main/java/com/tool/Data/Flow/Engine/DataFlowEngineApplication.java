package com.tool.Data.Flow.Engine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.tool.Data.Flow.Engine.model.ApplicationProperties;

@SpringBootApplication
@EnableConfigurationProperties(ApplicationProperties.class) 
public class DataFlowEngineApplication {

	public static void main(String[] args) {
		System.out.println("Data Flow Engine Started");
		SpringApplication.run(DataFlowEngineApplication.class, args);
	}

}
// Enable the use of @ConfigurationProperties