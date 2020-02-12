package com.hp.aws.athena.repository;

import com.hp.aws.athena.AthenaProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.athena.AthenaClient;
import software.amazon.awssdk.services.athena.AthenaClientBuilder;
import software.amazon.awssdk.services.athena.model.QueryExecutionContext;
import software.amazon.awssdk.services.athena.model.ResultConfiguration;
import software.amazon.awssdk.services.athena.model.StartQueryExecutionRequest;

import javax.annotation.PostConstruct;

/**
 * @author pagarha
 * @date 17-01-2020
 */
@Component
public class AthenaClientFactory {
    @Autowired
    private AthenaProperties athenaProperties;

    private final AthenaClientBuilder builder = AthenaClient.builder();
    private QueryExecutionContext queryExecutionContext;
    private ResultConfiguration resultConfiguration;


    @PostConstruct
    public void setBuilder() {
        builder.region(Region.of(athenaProperties.getRegion()))
                .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(athenaProperties.getAccessKey(), athenaProperties.getSecretKey())));
        // The QueryExecutionContext allows us to set the Database.
        queryExecutionContext = QueryExecutionContext.builder().database(athenaProperties.getDatabase()).build();
        // The result configuration specifies where the results of the query should go in S3 and encryption options
        resultConfiguration = ResultConfiguration.builder().outputLocation(athenaProperties.getS3Bucket()).build();
    }

    public AthenaClient createClient() {
        return builder.build();
    }

    public StartQueryExecutionRequest.Builder buildStartQueryExecutionRequest() {
        return StartQueryExecutionRequest.builder()
                .queryExecutionContext(queryExecutionContext)
                .resultConfiguration(resultConfiguration);
    }

}
