package io.camunda.controllers;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import io.camunda.annotations.OpenAPI;
import io.camunda.annotations.ValidBPMNFile;
import io.camunda.constants.Constants;
import io.camunda.operate.dto.FlownodeInstance;
import io.camunda.operate.dto.ProcessDefinition;
import io.camunda.operate.dto.ProcessInstance;
import io.camunda.operate.dto.Variable;
import io.camunda.operate.exception.OperateException;
import io.camunda.operate.search.FlownodeInstanceFilter;
import io.camunda.operate.search.ProcessDefinitionFilter;
import io.camunda.operate.search.ProcessInstanceFilter;
import io.camunda.operate.search.VariableFilter;
import io.camunda.zeebe.client.api.response.DeploymentEvent;
import io.camunda.zeebe.client.api.response.ProcessInstanceEvent;

public interface ProcessController extends VersionController{

	@PostMapping(path = "/deploy", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	@OpenAPI(summary = "To Deploy BPMN file", name = Constants.DEPLOY)
	public DeploymentEvent deploy(@RequestParam("file") @ValidBPMNFile MultipartFile bpmnFile) throws IOException;

	@GetMapping("/getAllProcessDefinitions")
	@OpenAPI(summary = "Get all Process definitions", name = Constants.PROCESS_DEFINITIONS)
	public List<ProcessDefinition> getAllProcessDefinitions() throws OperateException;

	@GetMapping("/getFlowNodeInstanceByKey/{key}")
	@OpenAPI(summary = "Get Flow Node Instance By Key", name = Constants.FLOWNODE_INSTANCES)
	public FlownodeInstance getFlowNodeInstanceByKey(@PathVariable long key) throws OperateException;

	@GetMapping("getProcessDefinitionXml/{processDefinitionId}")
	@OpenAPI(summary = "Get Process definition XML ", name = Constants.PROCESS_DEFINITIONS)
	@ResponseBody
	public String getProcessDefinitionXml(@PathVariable String processDefinitionId)
			throws NumberFormatException, OperateException;

	@GetMapping("/getProcessInstanceByKey/{key}")
	@OpenAPI(summary = "Get Process Instance By Key", name = Constants.PROCESS_INSTANCES)
	public ProcessInstance getProcessInstanceByKey(@PathVariable long key) throws OperateException;

	@GetMapping("/getAllProcessInstances")
	@OpenAPI(summary = "Get all Process Instances", name = Constants.PROCESS_INSTANCES)
	public List<ProcessInstance> getProcessInstances() throws OperateException;

	@GetMapping("/getVariableByKey/{key}")
	@OpenAPI(summary = "Get Variable By Key", name = Constants.VARIABLES)
	public Variable getVariableByKey(@PathVariable long key) throws OperateException;

	@PostMapping("/message/{messageName}/{correlationKey}")
	@OpenAPI(summary = "Publish a message", name = "Messages")
	public void publishMessage(@PathVariable String messageName, @PathVariable String correlationKey,
			@RequestBody Map<String, Object> variables);

	@PostMapping("/search/flowNodeInstances")
	@OpenAPI(summary = "Search Flow Node Instances", name = Constants.SEARCH_QUERIES)
	public List<FlownodeInstance> searchFlowNodeInstances(@RequestBody FlownodeInstanceFilter flownodeInstanceFilter)
			throws OperateException;

	@PostMapping("/search/processDefinitionFilter")
	@OpenAPI(summary = "Search Process Definitions", name = Constants.SEARCH_QUERIES)
	public List<ProcessDefinition> searchProcessDefinitions(
			@RequestBody ProcessDefinitionFilter processDefinitionFilter) throws OperateException;

	@PostMapping("/search/processInstances")
	@OpenAPI(summary = "Search Process Instances", name = Constants.SEARCH_QUERIES)
	public List<ProcessInstance> searchProcessInstances(@RequestBody ProcessInstanceFilter processInstanceFilter)
			throws OperateException;

	@PostMapping("/search/Variables")
	@OpenAPI(summary = "Search Variables", name = Constants.SEARCH_QUERIES)
	public List<Variable> searchVariables(@RequestBody VariableFilter variableFilter) throws OperateException;

	@PostMapping("/startBy/{bpmnProcessId}")
	@OpenAPI(summary = "Start a process by BPMN Process Id", name = Constants.PROCESS_INSTANCES)
	public ProcessInstanceEvent startProcessInstance(@PathVariable String bpmnProcessId,
			@RequestBody Map<String, Object> variables);
}
