package io.camunda.dtos;

public class TaskSearch {

	private Boolean assigned;

	private String assignee;

	private String group;

	private Integer pageSize;

	private String state;

	public Boolean getAssigned() {
		return assigned;
	}

	public String getAssignee() {
		return assignee;
	}

	public String getGroup() {
		return group;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public String getState() {
		return state;
	}

	public void setAssigned(Boolean assigned) {
		this.assigned = assigned;
	}

	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public void setState(String state) {
		this.state = state;
	}
}
