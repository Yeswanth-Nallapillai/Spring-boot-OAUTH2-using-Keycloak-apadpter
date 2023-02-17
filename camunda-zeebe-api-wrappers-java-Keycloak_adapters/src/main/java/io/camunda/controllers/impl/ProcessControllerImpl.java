package io.camunda.controllers.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.camunda.controllers.ProcessController;
import io.camunda.operate.dto.FlownodeInstance;
import io.camunda.operate.dto.ProcessDefinition;
import io.camunda.operate.dto.ProcessInstance;
import io.camunda.operate.dto.Variable;
import io.camunda.operate.exception.OperateException;
import io.camunda.operate.search.FlownodeInstanceFilter;
import io.camunda.operate.search.ProcessDefinitionFilter;
import io.camunda.operate.search.ProcessInstanceFilter;
import io.camunda.operate.search.VariableFilter;
import io.camunda.services.BpmnService;
import io.camunda.services.OperateService;
import io.camunda.zeebe.client.api.response.DeploymentEvent;
import io.camunda.zeebe.client.api.response.ProcessInstanceEvent;

@Validated
@RestController
public class ProcessControllerImpl implements ProcessController {

	@Autowired
	private BpmnService bpmnService;

	private final OperateService operateService;

	public ProcessControllerImpl(OperateService operateService) {
		this.operateService = operateService;
	}

	public DeploymentEvent deploy(MultipartFile bpmnFile) throws IOException {
		return operateService.deploy(bpmnFile);
	}

	public List<ProcessDefinition> getAllProcessDefinitions() throws OperateException {
		Set<String> present = new HashSet<>();
		List<ProcessDefinition> result = new ArrayList<>();
		List<ProcessDefinition> processDefs = operateService.getProcessDefinitions();
		if (processDefs != null) {
			for (ProcessDefinition def : processDefs) {
				if (!present.contains(def.getBpmnProcessId())) {
					result.add(def);
					present.add(def.getBpmnProcessId());
				}
			}
		}
		return result;
	}

	public FlownodeInstance getFlowNodeInstanceByKey(long key) throws OperateException {
		return operateService.getFlownodeInstance(key);
	}

	public String getProcessDefinitionXml(String processDefinitionId) throws NumberFormatException, OperateException {
		return bpmnService.getProcessDefinitionXml(processDefinitionId);
	}

	public ProcessInstance getProcessInstanceByKey(long key) throws OperateException {
		return operateService.getProcessInstance(key);
	}

	public List<ProcessInstance> getProcessInstances() throws OperateException {
		return operateService.getProcessInstances();
	}

	public Variable getVariableByKey(long key) throws OperateException {
		return operateService.getVariable(key);
	}

	public void publishMessage(String messageName, String correlationKey, @RequestBody Map<String, Object> variables) {
		operateService.publishMessage(messageName, correlationKey, variables);
	}

	public List<FlownodeInstance> searchFlowNodeInstances(FlownodeInstanceFilter flownodeInstanceFilter)
			throws OperateException {
		return operateService.searchFlownodeInstances(flownodeInstanceFilter);
	}

	public List<ProcessDefinition> searchProcessDefinitions(ProcessDefinitionFilter processDefinitionFilter)
			throws OperateException {
		return operateService.searchprocessDefinition(processDefinitionFilter);
	}

	public List<ProcessInstance> searchProcessInstances(ProcessInstanceFilter processInstanceFilter)
			throws OperateException {
		return operateService.searchProcessInstances(processInstanceFilter);
	}

	public List<Variable> searchVariables(VariableFilter variableFilter) throws OperateException {
		return operateService.searchVariables(variableFilter);
	}

	public ProcessInstanceEvent startProcessInstance(String bpmnProcessId, Map<String, Object> variables) {
		return operateService.startProcessInstance(bpmnProcessId, variables);
	}

}
