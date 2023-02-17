package io.camunda.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import io.camunda.annotations.OpenAPI;
import io.camunda.constants.Constants;
import io.camunda.dtos.Task;
import io.camunda.dtos.TaskSearch;
import io.camunda.tasklist.exception.TaskListException;

public interface TaskController extends VersionController {

	@GetMapping("/claim/{taskId}")
	@OpenAPI(summary = "Claim a Task", name = Constants.TASKS)
	public Task claimTask(@PathVariable String taskId) throws TaskListException;

	@PostMapping("/completeTask/{taskId}")
	@OpenAPI(summary = "Complete a Task", name = Constants.TASKS)
	public void completeTask(@PathVariable String taskId, @RequestBody Map<String, Object> variables)
			throws TaskListException;

	@GetMapping("/getTask/{taskId}")
	@OpenAPI(summary = "Get Task", name = Constants.TASKS)
	public Task getTask(@PathVariable String taskId) throws TaskListException;

	@GetMapping("/getTasks")
	@OpenAPI(summary = "Get all Tasks", name = Constants.TASKS)
	public List<Task> getTasks() throws TaskListException;

	@PostMapping("/search/tasks")
	@OpenAPI(summary = "Search Tasks", name = Constants.SEARCH_QUERIES)
	public List<Task> searchTasks(@RequestBody TaskSearch taskSearch) throws TaskListException;

	@GetMapping("/unclaim/{taskId}")
	@OpenAPI(summary = "Unclaim a Task", name = Constants.TASKS)
	public Task unclaimTask(@PathVariable String taskId) throws TaskListException;

}
