package io.camunda.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
public class PropertiesConfiguration {

	@Bean
	@ConfigurationProperties(prefix = "")
	public CUsers users() {
		return new CUsers();
	}

	@Bean
	@ConfigurationProperties(prefix = "zeebe.client.cloud")
	public ZeebeCredentials zeebeCredentials() {
		return new ZeebeCredentials();
	}
	
	@Bean
	@ConfigurationProperties(prefix = "keycloak")
	public KeycloakProperties loadKeyClockProperties() {
		return new KeycloakProperties();
	}
}
