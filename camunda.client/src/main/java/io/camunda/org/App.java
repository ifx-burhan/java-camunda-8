package io.camunda.org;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import io.camunda.client.CamundaClient;
import io.camunda.client.CredentialsProvider;
import io.camunda.client.impl.oauth.OAuthCredentialsProviderBuilder;

/**
 * Hello world!
 */
public class App {
	
	//Zeebe Client Credentials
	private static String ZEEBE_REST_ADDRESS; 
	private static String ZEEBE_GRPC_ADDRESS; 
	private static String ZEEBE_CLIENT_ID; 
	private static String ZEEBE_CLIENT_SECRET; 
	private static String ZEEBE_AUTHORIZATION_SERVER_URL; 
	private static String ZEEBE_TOKEN_AUDIENCE; 
	
    public static void main(String[] args) {
    		
    	loadCredentials();
    	
    	System.out.println(ZEEBE_REST_ADDRESS);
    	
    	final CredentialsProvider credentialsProvider = new OAuthCredentialsProviderBuilder()
		    	.authorizationServerUrl(ZEEBE_AUTHORIZATION_SERVER_URL)
		        .audience(ZEEBE_TOKEN_AUDIENCE)
		        .clientId(ZEEBE_CLIENT_ID)
		        .clientSecret(ZEEBE_CLIENT_SECRET)
		        .build();
    	
    	
    	/**
    	 * https://github.com/camunda/camunda/blob/main/clients/java/src/test/java/io/camunda/client/CamundaClientTest.java
    	 * search: newCloudClientBuilder
    	 */
    	try (final CamundaClient client =
		        CamundaClient
		        	.newClientBuilder()
		        	.grpcAddress(URI.create(ZEEBE_GRPC_ADDRESS))
		        	.restAddress(URI.create(ZEEBE_REST_ADDRESS))
		        	.credentialsProvider(credentialsProvider)
		            .build()) {
			
			// Request the Cluster Topology
			System.out.println("Connected to: " + client.newTopologyRequest().send().join() + "\n" + client.getConfiguration().toString());
			

			// Define some variables
			final Map<String, Object> variables = new HashMap<String, Object>();
	    	variables.put("reference", "C8_12345");
	    	variables.put("amount", Double.valueOf(999.00));
	    	variables.put("cardNumber", "1234567812345678");
			
			// Create a process 
			client.newCreateInstanceCommand()
	        .bpmnProcessId("Process_Simple")
	        .latestVersion()
	        .variables(variables)
	        .send()
	        .join();
			
			
		} catch (Exception e) {
		    e.printStackTrace();
		}
    	
    }
    
    
    private static Properties getCredentials() {
    	Properties properties = new Properties();
    	// location: src/main/resources/config.properties
    	try (InputStream input = App.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                System.out.println("Sorry, unable to find config.properties");
                return null;
            }
            // Load the properties file
            properties.load(input);

        } catch (IOException ex) {
            ex.printStackTrace();
        } 
        
    	return properties;  
    }
    
    private static void loadCredentials() {
    	
    	Properties properties = getCredentials();
    	
    	ZEEBE_REST_ADDRESS = properties.getProperty("ZEEBE_REST_ADDRESS");    
    	ZEEBE_GRPC_ADDRESS = properties.getProperty("ZEEBE_GRPC_ADDRESS");
    	ZEEBE_CLIENT_ID = properties.getProperty("ZEEBE_CLIENT_ID");
    	ZEEBE_CLIENT_SECRET = properties.getProperty("ZEEBE_CLIENT_SECRET");
    	ZEEBE_AUTHORIZATION_SERVER_URL = properties.getProperty("ZEEBE_AUTHORIZATION_SERVER_URL");
    	ZEEBE_TOKEN_AUDIENCE = properties.getProperty("ZEEBE_TOKEN_AUDIENCE");
    	
    }
}
