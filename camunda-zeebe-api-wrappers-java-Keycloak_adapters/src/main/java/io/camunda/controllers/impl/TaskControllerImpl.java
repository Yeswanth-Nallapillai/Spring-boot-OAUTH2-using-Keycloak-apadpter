package io.camunda.controllers.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import io.camunda.controllers.TaskController;
import io.camunda.dtos.Task;
import io.camunda.dtos.TaskSearch;
import io.camunda.services.TaskListService;
import io.camunda.tasklist.dto.TaskState;
import io.camunda.tasklist.exception.TaskListException;
import io.camunda.utils.Utils;

@CrossOrigin
@RestController
public class TaskControllerImpl implements TaskController {

	@Autowired
	private TaskListService taskListService;

	@Autowired
	private Utils utils;

	public Task claimTask(String taskId) throws TaskListException {
		String username = utils.getAuthenticatedUser().getName();
		return taskListService.claim(taskId, username);
	}

	public void completeTask(String taskId, Map<String, Object> variables) throws TaskListException {
		taskListService.completeTask(taskId, variables);
	}

	public Task getTask(String taskId) throws TaskListException {
		return taskListService.getTask(taskId);
	}

	public List<Task> getTasks() throws TaskListException {
		return taskListService.getTasks(TaskState.CREATED, null);
	}

	public List<Task> searchTasks(TaskSearch taskSearch) throws TaskListException {
		return taskListService.getTasks(taskSearch);
	}

	public Task unclaimTask(String taskId) throws TaskListException {
		return taskListService.unclaim(taskId);
	}

}
