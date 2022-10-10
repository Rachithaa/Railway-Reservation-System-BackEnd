package com.project.trainmanagementmicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;


@SpringBootApplication
@EnableMongoRepositories
@EnableEurekaClient
@EnableSwagger2
public class TrainmanagementmicroserviceApplication {

	public static void main(String[] args) {

		SpringApplication.run(TrainmanagementmicroserviceApplication.class, args);
	}
	@Bean
	public Docket swaggerConfiguration()
	{
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.paths(PathSelectors.ant("/trains/**"))
				.apis(RequestHandlerSelectors.basePackage("com.project"))
				.build()
				.apiInfo(details());
	}
	private ApiInfo details()
	{
		return new ApiInfo(
				"Train Management Service API",
				"API's for train Management Service",
				"1.0",
				"Admin  usage for train details only",
				new Contact("Rachitha","https://www.istockphoto.com/photos/steam-train","rach@gmail.com"),
				"Train Management Service API License",
				"https://www.istockphoto.com/photos/steam-train",
				Collections.emptyList()

		);
	}


}
