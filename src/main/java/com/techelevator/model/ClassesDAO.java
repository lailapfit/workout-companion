package com.techelevator.model;

import java.sql.Time;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public interface ClassesDAO {
	
	public List<Classes> getClassesByClassName();
	
	public void updateClassSchedule(int classId, String userName);
	
	public List<Classes> getClassesScheduled(String userName);
	
//	public List<Classes> getMemberByClass(String userNameTest); 
	
}