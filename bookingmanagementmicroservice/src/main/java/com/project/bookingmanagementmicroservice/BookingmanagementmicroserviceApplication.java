package com.project.bookingmanagementmicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@SpringBootApplication
@EnableSwagger2
@EnableMongoRepositories
@EnableEurekaClient
public class BookingmanagementmicroserviceApplication {

	@Bean
	@LoadBalanced
	public RestTemplate getRestTemplate()
	{
		return new RestTemplate();
	}
	public static void main(String[] args) {
		SpringApplication.run(BookingmanagementmicroserviceApplication.class, args);
	}

	@Bean
	public Docket swaggerConfiguration()
	{
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.paths(PathSelectors.ant("/booking/**"))
				.apis(RequestHandlerSelectors.basePackage("com.capgeminiproject"))
				.build()
				.apiInfo(details());
	}
	private ApiInfo details()
	{
		return new ApiInfo(
				"Booking Management Service API",
				"API's for Booking Management Service",
				"1.0",
				"Passengers  usage for  booking of train details only",
				new Contact("Rachitha","https://www.istockphoto.com/photos/steam-train","rach@gmail.com"),
				"Booking Management Service API License",
				"https://www.istockphoto.com/photos/steam-train",
				Collections.emptyList()

		);
	}
}
