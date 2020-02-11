package com.hp.aws.athena;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Value("${info.app.version}")
	private String version;

	@Bean
	public Docket productApi() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.hp.aws.athena.controller"))
				.paths(PathSelectors.any())
				.build().apiInfo(metaData());
	}

	private ApiInfo metaData() {
		return new ApiInfoBuilder().title("Athena REST API")
				.description("REST API for Athena operations").version(version)
				.contact(new Contact("Harshal Pagar", "https://github.com/harshalpagar", "harshalpagar7@gmail.com")).build();
	}
}