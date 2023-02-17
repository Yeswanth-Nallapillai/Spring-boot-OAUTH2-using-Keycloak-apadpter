package io.camunda.services;

import java.util.List;
import java.util.Map;

import io.camunda.dtos.Task;
import io.camunda.dtos.TaskSearch;
import io.camunda.tasklist.dto.TaskState;
import io.camunda.tasklist.exception.TaskListException;

public interface TaskListService extends AuthService {

	public Task claim(String taskId, String assignee) throws TaskListException;

	public void completeTask(String taskId, Map<String, Object> variables) throws TaskListException;

	public List<Task> getAssigneeTasks(String assignee, TaskState state, Integer pageSize) throws TaskListException;

	public String getForm(String processDefinitionId, String formId) throws TaskListException;

	public List<Task> getGroupTasks(String group, TaskState state, Integer pageSize) throws TaskListException;

	public Task getTask(String taskId) throws TaskListException;

	public List<Task> getTasks(TaskSearch taskSearch) throws TaskListException;

	public List<Task> getTasks(TaskState state, Integer pageSize) throws TaskListException;

	public Task unclaim(String taskId) throws TaskListException;
}
