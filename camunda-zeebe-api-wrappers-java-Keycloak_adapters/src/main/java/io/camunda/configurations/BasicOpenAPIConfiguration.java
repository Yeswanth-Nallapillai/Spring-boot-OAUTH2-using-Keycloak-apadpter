package io.camunda.configurations;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
@OpenAPIDefinition
@ConditionalOnProperty(name = "keycloak.enabled", havingValue = "false", matchIfMissing = true)
public class BasicOpenAPIConfiguration {

	@Bean
	public OpenAPI swaggerOpenAPI() {
		return new OpenAPI()
					.components(new Components()
							.addSecuritySchemes("authorization",				
									new SecurityScheme()
										.type(SecurityScheme.Type.HTTP)
										.scheme("basic")));
	}

}
