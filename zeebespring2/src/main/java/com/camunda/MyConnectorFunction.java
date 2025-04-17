package com.camunda;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.camunda.connector.api.annotation.OutboundConnector;
import io.camunda.connector.api.outbound.OutboundConnectorContext;
import io.camunda.connector.api.outbound.OutboundConnectorFunction;

@OutboundConnector(
		name = "template-connector",
	    inputVariables = {"input1", "input2"},
	    type = "io.camunda:concatenation-api:1"
	)
public class MyConnectorFunction implements OutboundConnectorFunction {

	private static final Logger LOGGER = LoggerFactory.getLogger(MyConnectorFunction.class);
	
	@Override
	public Object execute(OutboundConnectorContext context) throws Exception {
		// TODO Auto-generated method stub
		
		MyConnectorRequest connectorRequest = context.bindVariables(MyConnectorRequest.class);
		
		
		LOGGER.info("Outbound {} {}", connectorRequest.input1(), connectorRequest.input2());
		
		return new MyConnectorResult("Done");
	}

}
