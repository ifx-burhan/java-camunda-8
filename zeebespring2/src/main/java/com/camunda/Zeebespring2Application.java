package com.camunda;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

import io.camunda.zeebe.client.ZeebeClient;

@SpringBootApplication
@ComponentScan(excludeFilters={@ComponentScan.Filter(type= FilterType.ASSIGNABLE_TYPE, value=CompileProcessWorker.class)})
public class Zeebespring2Application implements CommandLineRunner{
	
	private static final Logger LOG = LoggerFactory.getLogger(Zeebespring2Application.class);
	
	@Autowired
	private ZeebeClient zeebeClient;
	
	public static void main(String[] args) {
		SpringApplication.run(Zeebespring2Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		
		LOG.info("Running ... {}", zeebeClient.getConfiguration());
		
	}

}
