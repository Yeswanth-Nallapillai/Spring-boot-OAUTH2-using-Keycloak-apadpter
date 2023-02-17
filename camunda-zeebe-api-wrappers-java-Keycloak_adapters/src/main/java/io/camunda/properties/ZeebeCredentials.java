package io.camunda.properties;

public class ZeebeCredentials {

	private String clientId;

	private String clientSecret;

	private String clusterId;

	private String region;

	public String getClientId() {
		return clientId;
	}

	public String getClientSecret() {
		return clientSecret;
	}

	public String getClusterId() {
		return clusterId;
	}

	public String getRegion() {
		return region;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

	public void setClusterId(String clusterId) {
		this.clusterId = clusterId;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	@Override
	public String toString() {
		return "ZeebeCredentials [region=" + region + ", clusterId=" + clusterId + ", clientId=" + clientId
				+ ", clientSecret=" + clientSecret + "]";
	}

}
