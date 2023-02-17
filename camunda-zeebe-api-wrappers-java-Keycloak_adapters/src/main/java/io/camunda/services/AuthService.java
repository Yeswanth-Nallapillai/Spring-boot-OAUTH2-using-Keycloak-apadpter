package io.camunda.services;

import io.camunda.operate.CamundaOperateClient;
import io.camunda.operate.exception.OperateException;
import io.camunda.properties.KeycloakProperties;
import io.camunda.properties.ZeebeCredentials;
import io.camunda.tasklist.CamundaTaskListClient;
import io.camunda.tasklist.exception.TaskListException;

public interface AuthService {

	public default CamundaOperateClient getCamundaOperateClient(ZeebeCredentials credentials,
			KeycloakProperties keycloak) throws OperateException {
		
		//TODO: For now hardcoding the value  
		// boolean keycloakEnabled = keycloak.isEnabled() ;
		final boolean keycloakEnabled = false ;
		
		if (keycloakEnabled ) {
			io.camunda.operate.auth.SelfManagedAuthentication la = new io.camunda.operate.auth.SelfManagedAuthentication()
					.clientId(credentials.getClientId()).clientSecret(credentials.getClientSecret())
					.keycloakUrl(keycloak.getAuthServerUrl());
			return new CamundaOperateClient.Builder()
					.operateUrl(
							"https://" + credentials.getRegion() + ".operate.camunda.io/" + credentials.getClusterId())
					.authentication(la).build();
		} else {
			io.camunda.operate.auth.SaasAuthentication sa = new io.camunda.operate.auth.SaasAuthentication(
					credentials.getClientId(), credentials.getClientSecret());
			return new CamundaOperateClient.Builder()
					.operateUrl(
							"https://" + credentials.getRegion() + ".operate.camunda.io/" + credentials.getClusterId())
					.authentication(sa).build();
		}

	}

	public default CamundaTaskListClient getCamundaTaskListClient(ZeebeCredentials credentials,
			KeycloakProperties keycloak) throws TaskListException {
		//TODO: For now hardcoding the value		
		final boolean keycloakEnabled = false ;
		if (keycloakEnabled) {
			io.camunda.tasklist.auth.SelfManagedAuthentication sma = new io.camunda.tasklist.auth.SelfManagedAuthentication()
					.clientId(credentials.getClientId()).clientSecret(credentials.getClientSecret())
					.keycloakUrl(keycloak.getAuthServerUrl());
			return new CamundaTaskListClient.Builder().shouldReturnVariables()
					.taskListUrl(
							"https://" + credentials.getRegion() + ".tasklist.camunda.io/" + credentials.getClusterId())
					.authentication(sma).build();
		} else {
			io.camunda.tasklist.auth.SaasAuthentication sa = new io.camunda.tasklist.auth.SaasAuthentication(
					credentials.getClientId(), credentials.getClientSecret());
			return new CamundaTaskListClient.Builder().shouldReturnVariables()
					.taskListUrl(
							"https://" + credentials.getRegion() + ".tasklist.camunda.io/" + credentials.getClusterId())
					.authentication(sa).build();

		}
	}
}
