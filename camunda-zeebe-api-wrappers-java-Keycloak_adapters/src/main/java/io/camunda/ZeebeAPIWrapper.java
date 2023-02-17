package io.camunda;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.camunda.zeebe.spring.client.EnableZeebeClient;

@SpringBootApplication
@EnableZeebeClient
public class ZeebeAPIWrapper {
	public static void main(String[] args) {
		SpringApplication.run(ZeebeAPIWrapper.class, args);
	}

	ZeebeAPIWrapper() {
		super();
	}
}
