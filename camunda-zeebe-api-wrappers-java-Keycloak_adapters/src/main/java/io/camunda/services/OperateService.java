package io.camunda.services;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

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

public interface OperateService extends AuthService {

	public DeploymentEvent deploy(MultipartFile bpmnFile) throws IOException;

	public FlownodeInstance getFlownodeInstance(Long key) throws OperateException;

	public List<ProcessDefinition> getProcessDefinitions() throws OperateException;

	public String getProcessDefinitionXmlByKey(Long key) throws OperateException;

	public ProcessInstance getProcessInstance(Long key) throws OperateException;

	public List<ProcessInstance> getProcessInstances() throws OperateException;

	public Variable getVariable(Long key) throws OperateException;

	public void publishMessage(String messageName, String correlationKey, @RequestBody Map<String, Object> variables);

	public List<FlownodeInstance> searchFlownodeInstances(FlownodeInstanceFilter flownodeInstanceFilter)
			throws OperateException;

	public List<ProcessDefinition> searchprocessDefinition(ProcessDefinitionFilter processDefinitionFilter)
			throws OperateException;

	public List<ProcessInstance> searchProcessInstances(ProcessInstanceFilter processInstanceFilter)
			throws OperateException;

	public List<Variable> searchVariables(VariableFilter variableFilter) throws OperateException;

	public ProcessInstanceEvent startProcessInstance(String bpmnProcessId, Map<String, Object> variables);

}
