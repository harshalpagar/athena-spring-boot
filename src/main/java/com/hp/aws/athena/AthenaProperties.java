package com.hp.aws.athena;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 *  athena configuration properties
 *
 */
@Component
@ConfigurationProperties("aws.athena")
@Data
public class AthenaProperties {

	private String region;

	private String accessKey;

	private String secretKey;

	private String database;

	private String s3Bucket;

	private String table;
}
