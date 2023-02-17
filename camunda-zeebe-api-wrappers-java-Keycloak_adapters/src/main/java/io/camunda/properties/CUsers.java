package io.camunda.properties;

import java.util.ArrayList;
import java.util.List;

import io.camunda.dtos.User;

public class CUsers {

	private List<User> users = new ArrayList<>();

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

}
