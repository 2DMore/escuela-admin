package org.uady.admin.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

@Configuration
public class DynamoDBConfig {
    @Bean
    public DynamoDBMapper dynamoDBMapper() {
        return new DynamoDBMapper(buildAmazonDynamoDB());
    }

    private AmazonDynamoDB buildAmazonDynamoDB() {
        return AmazonDynamoDBClientBuilder
                .standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("dynamodb.us-east-1.amazonaws.com", "us-east-1"))
				.withCredentials(new AWSStaticCredentialsProvider(
						new BasicSessionCredentials(
								"ASIAVNYK52H4ZERFNAOQ",
								"Q+p1TE4PIyDOv5h1huLmg1o1pwPk5S9GKQZOOwh1",
								"FwoGZXIvYXdzENL//////////wEaDAKuzlLD4IXisXM9QSLLAQJx4cnN/3GCvMcee7FF0OnCQ2XV3SVbQ1A9zAiKyhSVaXOzvFWzdFrbgXh2M2AE7+PdHecciuMXLLbm5hLCrv+219zLEk+yct2kGKjhk/3hxGp1yStqQ2bY11juAW36WPc0aSxnuvK1S1dFhLZa9SCiVRKAiVr+vyGIPDZJ9Ym07SBl7PWLz4ow/0o/+k9Ss6KoQcbcqNwgAnbWizA0pbSF4CWbXVFQzWvItw853uTPCxk2IQvURGUJxvR3cC/xCmxEBmcGgGVJFCMPKJ6B5KsGMi1UUpz3itVOxqLfWMHr8uFm8PQZoE/aFv73DHMXlVqFnrHFF6k1B9P383tZu38=")))
						.build();//Editar con nuevas keys
    }
}
