package io.camunda.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import io.camunda.dtos.Task;
import io.camunda.dtos.TaskSearch;
import io.camunda.properties.KeycloakProperties;
import io.camunda.properties.ZeebeCredentials;
import io.camunda.services.TaskListService;
import io.camunda.tasklist.CamundaTaskListClient;
import io.camunda.tasklist.dto.Form;
import io.camunda.tasklist.dto.TaskState;
import io.camunda.tasklist.dto.Variable;
import io.camunda.tasklist.exception.TaskListException;

@Service
public class TaskListServiceimpl implements TaskListService {

	private static final Logger LOG = LoggerFactory.getLogger(TaskListServiceimpl.class);

	private ZeebeCredentials credentials;
	private KeycloakProperties keycloakProperties ;
	
	TaskListServiceimpl(ZeebeCredentials credentials, KeycloakProperties keycloakProperties) {
		super();
		this.credentials = credentials;
		this.keycloakProperties = keycloakProperties;
	}

	public Task claim(String taskId, String assignee) throws TaskListException {
		return convert(getCamundaTaskListClient().claim(taskId, assignee));
	}

	public void completeTask(String taskId, Map<String, Object> variables) throws TaskListException {
		LOG.info("Completing task {} with variables: {}", taskId, variables);
		getCamundaTaskListClient().completeTask(taskId, variables);
	}

	private Task convert(io.camunda.tasklist.dto.Task task) {
		Task result = new Task();
		BeanUtils.copyProperties(task, result);
		if (task.getVariables() != null) {
			result.setVariables(new HashMap<>());
			for (Variable var_ : task.getVariables()) {
				result.getVariables().put(var_.getName(), var_.getValue());
			}
		}
		return result;
	}

	private List<Task> convert(List<io.camunda.tasklist.dto.Task> tasks) {
		List<Task> result = new ArrayList<>();
		for (io.camunda.tasklist.dto.Task task : tasks) {
			result.add(convert(task));
		}
		return result;
	}

	public List<Task> getAssigneeTasks(String assignee, TaskState state, Integer pageSize) throws TaskListException {
		return convert(getCamundaTaskListClient().getAssigneeTasks(assignee, state, pageSize));
	}

	CamundaTaskListClient getCamundaTaskListClient() throws TaskListException{
		return getCamundaTaskListClient(credentials, keycloakProperties);
	}

	public String getForm(String processDefinitionId, String formId) throws TaskListException {
		Form form = getCamundaTaskListClient().getForm(formId, processDefinitionId);
		return form.getSchema();
	}
	
	public List<Task> getGroupTasks(String group, TaskState state, Integer pageSize) throws TaskListException {
		return convert(getCamundaTaskListClient().getGroupTasks(group, state, pageSize));
	}

	public Task getTask(String taskId) throws TaskListException {
		return convert(getCamundaTaskListClient().getTask(taskId));
	}

	public List<Task> getTasks(TaskSearch taskSearch) throws TaskListException {
		if (Boolean.TRUE.equals(taskSearch.getAssigned()) && taskSearch.getAssignee() != null) {
			return convert(getCamundaTaskListClient().getAssigneeTasks(taskSearch.getAssignee(),
					TaskState.fromJson(taskSearch.getState()), taskSearch.getPageSize()));
		}
		if (taskSearch.getGroup() != null) {
			return convert(getCamundaTaskListClient().getGroupTasks(taskSearch.getGroup(),
					TaskState.fromJson(taskSearch.getState()), taskSearch.getPageSize()));
		}
		return convert(getCamundaTaskListClient().getTasks(taskSearch.getAssigned(),
				TaskState.fromJson(taskSearch.getState()), taskSearch.getPageSize()));
	}

	public List<Task> getTasks(TaskState state, Integer pageSize) throws TaskListException {
		return convert(getCamundaTaskListClient().getTasks(null, state, pageSize));
	}

	public Task unclaim(String taskId) throws TaskListException {
		return convert(getCamundaTaskListClient().unclaim(taskId));
	}
}
