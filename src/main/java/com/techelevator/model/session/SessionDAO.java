package com.techelevator.model.session;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public interface SessionDAO {

	public void saveSession(String username, Integer equipment_id, Integer reps, Integer sets, Integer weight, Date date);
	
	public List<Session> getAllSessions();
	
	public List<Session> getAllSessionsPerMember(String username);
	
	public List<Session> getAllSessionsPerMemberByDate(String username, Date date);
	
	public List<Session> getAllSessionsPerMemberByDateTime(String username, Date date, Time start);
	
	public List<Session> getAllSessionsPerMemberByMonth(String username, String month);
	
	public Integer getTotalTime(String username, Date date);
	
	public Date getCurrentDate();
	
	public List<Session> getMemberSessionData(String username);
	
	public List<Session> getAllSessionsPerMemberWithEquipment(String username);
	
	public void updateTime(String username, Date date, Time start, Time end);
	
	public Time getCurrentTime();
	

}
