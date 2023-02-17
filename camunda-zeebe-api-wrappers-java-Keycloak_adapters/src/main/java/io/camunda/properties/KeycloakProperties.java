package io.camunda.properties;

import com.fasterxml.jackson.annotation.JsonProperty;

public class KeycloakProperties {
	
	boolean enabled;
	
	String realm;
	
	String resource;
	
	String authServerUrl;
		
	String credentialsSecret;

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getRealm() {
		return realm;
	}

	public void setRealm(String realm) {
		this.realm = realm;
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	@JsonProperty("auth-server-url")
	public String getAuthServerUrl() {
		return authServerUrl;
	}

	@JsonProperty("auth-server-url")
	public void setAuthServerUrl(String authServerUrl) {
		this.authServerUrl = authServerUrl;
	}

	@JsonProperty("credentials.secret")
	public String getCredentialsSecret() {
		return credentialsSecret;
	}

	@JsonProperty("credentials.secret")
	public void setCredentialsSecret(String credentialsSecret) {
		this.credentialsSecret = credentialsSecret;
	}

	@Override
	public String toString() {
		return "KeyClockProperties [enabled=" + enabled + ", realm=" + realm + ", resource=" + resource
				+ ", authServerUrl=" + authServerUrl + ", credentialsSecret=" + credentialsSecret + "]";
	}

}
