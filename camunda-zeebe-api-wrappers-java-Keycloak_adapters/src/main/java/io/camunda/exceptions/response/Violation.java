package io.camunda.exceptions.response;

public class Violation {

	private final String message;

	public Violation(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

}