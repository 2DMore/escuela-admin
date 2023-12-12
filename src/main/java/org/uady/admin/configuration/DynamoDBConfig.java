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
								"ASIAVNYK52H4Q37WQM5I",
								"82EA5DGpuM+BNbszhKlXaa+z2ptlaMSJHIyq//ty",
								"FwoGZXIvYXdzEM3//////////wEaDLsnvEXMdQVhRZzkJCLLAZdJInHo9xFMGdz+dWyQOvpHWMtFRggNK5m4Y0d5EyNlPMTFZKSP8ZmPxdFWf+32I/Q0+yKA+vmLDulo8InCPDP6EXyPhERlWF8nTfSqHHnzvzJ7RpCDMuG+/cxfYabfxdLvk63XEdCDonfd3BvnV/8F1SUlI3hdpvZBSbtMBgTL7MbaGAeOVR6fu3jgqTis1KcKCxA5Y5cjikFmh6FpJ3yf/d4f4f0y8+dDuxE3IFKsWXhsp7WFw9jcMrCLWeG+BAd3JcMWmV7r3Y+qKLr44qsGMi04XpmibuQyGKUSxDHQ7P4JDsEWjbyKQITCdvUo5C3eaRxsSqMA+K+bvTtZeHI=")))
						.build();//Editar con nuevas keys
    }
}
