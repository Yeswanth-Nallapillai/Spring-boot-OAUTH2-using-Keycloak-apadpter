package io.camunda.services.impl;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import io.camunda.operate.CamundaOperateClient;
import io.camunda.operate.dto.FlownodeInstance;
import io.camunda.operate.dto.ProcessDefinition;
import io.camunda.operate.dto.ProcessInstance;
import io.camunda.operate.dto.Variable;
import io.camunda.operate.exception.OperateException;
import io.camunda.operate.search.FlownodeInstanceFilter;
import io.camunda.operate.search.ProcessDefinitionFilter;
import io.camunda.operate.search.ProcessInstanceFilter;
import io.camunda.operate.search.SearchQuery;
import io.camunda.operate.search.Sort;
import io.camunda.operate.search.SortOrder;
import io.camunda.operate.search.VariableFilter;
import io.camunda.properties.KeycloakProperties;
import io.camunda.properties.ZeebeCredentials;
import io.camunda.services.OperateService;
import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.DeploymentEvent;
import io.camunda.zeebe.client.api.response.ProcessInstanceEvent;

@Service
@EnableCaching
public class OperateServiceImpl implements OperateService {

	private static final Logger LOG = LoggerFactory.getLogger(OperateServiceImpl.class);

	private final ZeebeCredentials credentials;

	private final KeycloakProperties keycloakProperties;

	private final ZeebeClient zeebeClient;

	OperateServiceImpl(ZeebeClient zeebeClient, ZeebeCredentials credentials, KeycloakProperties keycloakProperties) {
		this.zeebeClient = zeebeClient;
		this.credentials = credentials;
		this.keycloakProperties = keycloakProperties;
	}

	public DeploymentEvent deploy(MultipartFile bpmnFile) throws IOException {		
		return zeebeClient.newDeployResourceCommand()
				.addResourceBytes(bpmnFile.getBytes(), bpmnFile.getResource().getFilename()).send().join();		
	}

	private CamundaOperateClient getCamundaOperateClient() throws OperateException {
		return getCamundaOperateClient(credentials, keycloakProperties);
	}

	@Override
	public FlownodeInstance getFlownodeInstance(Long key) throws OperateException {
		return getCamundaOperateClient().getFlownodeInstance(key);
	}

	public List<ProcessDefinition> getProcessDefinitions() throws OperateException {
		ProcessDefinitionFilter processDefinitionFilter = new ProcessDefinitionFilter.Builder().build();
		SearchQuery procDefQuery = new SearchQuery.Builder().withFilter(processDefinitionFilter).withSize(1000)
				.withSort(new Sort("version", SortOrder.DESC)).build();

		return getCamundaOperateClient().searchProcessDefinitions(procDefQuery);
	}

	@Cacheable("processXmls")
	public String getProcessDefinitionXmlByKey(Long key) throws OperateException {
		LOG.info("Entering getProcessDefinitionXmlByKey for key: {} ", key);
		return getCamundaOperateClient().getProcessDefinitionXml(key);
	}

	public ProcessInstance getProcessInstance(Long key) throws OperateException {
		return getCamundaOperateClient().getProcessInstance(key);
	}

	public List<ProcessInstance> getProcessInstances() throws OperateException {
		ProcessInstanceFilter processInstanceFilter = new ProcessInstanceFilter.Builder().build();
		SearchQuery procDefQuery = new SearchQuery.Builder().withFilter(processInstanceFilter).withSize(1000)
				.withSort(new Sort("startDate", SortOrder.DESC)).build();
		return getCamundaOperateClient().searchProcessInstances(procDefQuery);
	}

	public Variable getVariable(Long key) throws OperateException {
		return getCamundaOperateClient().getVariable(key);
	}

	public void publishMessage(String messageName, String correlationKey, @RequestBody Map<String, Object> variables) {
		LOG.info("Publishing message `{}` with correlation key `{}` and variables: {}", messageName, correlationKey,
				variables);
		zeebeClient.newPublishMessageCommand().messageName(messageName).correlationKey(correlationKey)
				.variables(variables).send();
	}

	@Override
	public List<FlownodeInstance> searchFlownodeInstances(FlownodeInstanceFilter flownodeInstanceFilter)
			throws OperateException {
		SearchQuery query = new SearchQuery.Builder().withFilter(flownodeInstanceFilter).build();
		return getCamundaOperateClient().searchFlownodeInstances(query);
	}

	public List<ProcessDefinition> searchprocessDefinition(ProcessDefinitionFilter processDefinitionFilter)
			throws OperateException {
		SearchQuery query = new SearchQuery.Builder().withFilter(processDefinitionFilter).build();
		return getCamundaOperateClient().searchProcessDefinitions(query);
	}

	public List<ProcessInstance> searchProcessInstances(ProcessInstanceFilter processInstanceFilter)
			throws OperateException {
		SearchQuery query = new SearchQuery.Builder().withFilter(processInstanceFilter).build();
		return getCamundaOperateClient().searchProcessInstances(query);
	}

	public List<Variable> searchVariables(VariableFilter variableFilter) throws OperateException {
		SearchQuery query = new SearchQuery.Builder().withFilter(variableFilter).build();
		return getCamundaOperateClient().searchVariables(query);
	}

	public ProcessInstanceEvent startProcessInstance(String bpmnProcessId, Map<String, Object> variables) {
		LOG.info("Starting process {} with variables {}", bpmnProcessId, variables);
		return zeebeClient.newCreateInstanceCommand().bpmnProcessId(bpmnProcessId).latestVersion().variables(variables)
				.send().join();
	}

}
