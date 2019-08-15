package com.techelevator.model;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public interface AdministratorDAO {

	public void addEmployee(String userName, String password, String name, String email, String workoutGoals, String workoutProfile, String avatar, int roleId);

	public List<User> getAllEmployees();
}
