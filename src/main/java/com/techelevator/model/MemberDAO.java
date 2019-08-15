package com.techelevator.model;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public interface MemberDAO {

	public void saveMember(String userName, String password, String name, String email, String workoutGoals, String workoutProfile, String avatar);

	public boolean searchForUsernameAndPassword(String userName, String password);

	public void updatePassword(String userName, String password);

	public Object getMemberByUserName(String userName);
	
	public List<User> getAllProfileMembers();
	
	public void updateWorkoutGoals(String updatedGoals, String email);
	
}
