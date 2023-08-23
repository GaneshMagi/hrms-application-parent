package com.rbts.hrms.multitenant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@SpringBootApplication
@EnableAspectJAutoProxy
@EnableWebMvc
public class MultitenantApplication {
	@Bean
	public FlywayMigrationStrategy flywayMigrationStrategy() {
		return flyway -> {
			// do nothing
		};
	}


	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.example.Multitenant.Controller"))
				.build();
	}

//	protected void addResourceHandlers(ResourceHandlerRegistry registry) {
//		registry.addResourceHandler("swagger-ui.html")
//				.addResourceLocations("classpath:/META-INF/resources/");
//
//		registry.addResourceHandler("/webjars/**")
//				.addResourceLocations("classpath:/META-INF/resources/webjars/");
//	}
//
//	private ApiInfo apiInfo() {
//		return new ApiInfoBuilder().title("Auth Microservice")
//				.description("Interface between services and UI")
//
//				.contact(new Contact("Test", "", "test@test.com"))
//				.license("License").licenseUrl("License Url").version("1.0").build();
//	}
	public static void main(String[] args) {


		SpringApplication.run(MultitenantApplication.class, args);


	}

}
