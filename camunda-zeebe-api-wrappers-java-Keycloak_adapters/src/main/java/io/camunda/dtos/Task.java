package io.camunda.dtos;

import java.util.List;
import java.util.Map;

import io.camunda.tasklist.dto.TaskState;

public class Task {
	private String assignee;

	private List<String> candidateGroups;

	private String creationTime;

	private String formKey;

	private String id;

	private Boolean isFirst;

	private String jobKey;

	private String name;

	private String processDefinitionId;

	private String processName;

	private List<String> sortValues;

	private TaskState taskState;

	private Map<String, Object> variables;

	public String getAssignee() {
		return assignee;
	}

	public List<String> getCandidateGroups() {
		return candidateGroups;
	}

	public String getCreationTime() {
		return creationTime;
	}

	public String getFormKey() {
		return formKey;
	}

	public String getId() {
		return id;
	}

	public Boolean getIsFirst() {
		return isFirst;
	}

	public String getJobKey() {
		return jobKey;
	}

	public String getName() {
		return name;
	}

	public String getProcessDefinitionId() {
		return processDefinitionId;
	}

	public String getProcessName() {
		return processName;
	}

	public List<String> getSortValues() {
		return sortValues;
	}

	public TaskState getTaskState() {
		return taskState;
	}

	public Map<String, Object> getVariables() {
		return variables;
	}

	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}

	public void setCandidateGroups(List<String> candidateGroups) {
		this.candidateGroups = candidateGroups;
	}

	public void setCreationTime(String creationTime) {
		this.creationTime = creationTime;
	}

	public void setFormKey(String formKey) {
		this.formKey = formKey;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setIsFirst(Boolean isFirst) {
		this.isFirst = isFirst;
	}

	public void setJobKey(String jobKey) {
		this.jobKey = jobKey;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setProcessDefinitionId(String processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public void setSortValues(List<String> sortValues) {
		this.sortValues = sortValues;
	}

	public void setTaskState(TaskState taskState) {
		this.taskState = taskState;
	}

	public void setVariables(Map<String, Object> variables) {
		this.variables = variables;
	}

	@Override
	public String toString() {
		return "Task [id=" + id + ", jobKey=" + jobKey + ", name=" + name + ", processName=" + processName
				+ ", assignee=" + assignee + ", creationTime=" + creationTime + ", taskState=" + taskState
				+ ", candidateGroups=" + candidateGroups + ", sortValues=" + sortValues + ", isFirst=" + isFirst
				+ ", variables=" + variables + ", formKey=" + formKey + ", processDefinitionId=" + processDefinitionId
				+ "]";
	}

}
