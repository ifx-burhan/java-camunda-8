package com.camunda;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import io.camunda.zeebe.spring.client.annotation.JobWorker;
import io.camunda.zeebe.spring.client.annotation.Variable;

@Component
public class CompileProcessWorker {

	private final static Logger LOG = LoggerFactory.getLogger(CompileProcessWorker.class);
	@JobWorker(type = "compileProcess")
	public Map<String, String> compileProcess(@Variable(name = "data1") String data1) {
		LOG.info("Input Data: {}", data1);
		return Map.of("done", "done!");
	}	
	
}
