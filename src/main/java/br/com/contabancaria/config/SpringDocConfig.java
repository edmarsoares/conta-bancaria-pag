package br.com.contabancaria.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.In;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;

@Configuration
public class SpringDocConfig {
	
	@Bean
	public OpenAPI springShopOpenAPI() {
		return new OpenAPI()
				.schemaRequirement("conta Bancária", securityScheme())
				.info(new Info().title("conta Bancária")
				.description("API responsável por gerir uma conta")
				.version("0.0.1-SNAPSHOT")
				.license(new License()
							.name("Apache 2.0")
							.url("http://springdoc.org")));
				
	}
	
	SecurityScheme securityScheme() {
		return new SecurityScheme()
				.type(Type.APIKEY)
				.name("banco")
				.in(In.HEADER);
	}
}
