package com.techelevator.model;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class User {
	
	private String userName;
	@Size(min = 10, message = "Password too short, must be at least 10")
	@Pattern.List({ @Pattern(regexp = ".*[a-z].*", message = "Must have a lower case"),
			@Pattern(regexp = ".*[A-Z].*", message = "Must have a capital") })
	private String password;
	private int roleId;
	private String salt;
	private String confirmPassword;
	private String name;
	private String email;
	private String workoutGoals;
	private String workoutProfile;
	private String avatar;
	
	//Getters and setters below this line---------------------------------------------------
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getWorkoutGoals() {
		return workoutGoals;
	}
	public void setWorkoutGoals(String workoutGoals) {
		this.workoutGoals = workoutGoals;
	}
	public String getWorkoutProfile() {
		return workoutProfile;
	}
	public void setWorkoutProfile(String workoutProfile) {
		this.workoutProfile = workoutProfile;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}	
	
}
