package com.techelevator.model.session;


import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

@Component
public class JDBCSessionDAO implements SessionDAO {

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public JDBCSessionDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Session> getAllSessions() {
		List<Session> allSessions = new ArrayList<Session>();
		String sqlAllSessions = "SELECT * FROM workout_log";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlAllSessions);
		while (results.next()) {
			allSessions.add(mapToRowSession(results));
		}
		return allSessions;
	}

	@Override
	public void saveSession(String username, Integer equipment_id, Integer reps, Integer sets, Integer weight, Date date) {
		date = getCurrentDate();
		String sqlMemberSession = "INSERT INTO workout_log (workout_log_username, workout_log_equipment_id, workout_log_reps, workout_log_sets, workout_log_weight, workout_log_date) "
				+ "VALUES (?, ?, ?, ?, ?, ?)";
		jdbcTemplate.update(sqlMemberSession, username, equipment_id, reps, sets, weight, date);
	}

	@Override
	public List<Session> getAllSessionsPerMember(String username) {
		List<Session> perMemberSessions = new ArrayList<Session>();
		String sqlSessionPerMember = "SELECT * FROM workout_log WHERE workout_log_username = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlSessionPerMember, username);
		while (results.next()) {
			perMemberSessions.add(mapToRowSession(results));
		}
		return perMemberSessions;
	}

	private Session mapToRowSession(SqlRowSet row) {
		Session session = new Session();
		session.setSets(row.getInt("workout_log_sets"));
		session.setReps(row.getInt("workout_log_reps"));
		session.setWeights(row.getInt("workout_log_weight"));
		session.setStart(row.getTime("workout_log_start"));
		session.setEnd(row.getTime("workout_log_end"));
		session.setUsername(row.getString("workout_log_username"));
		session.setEquipmentId(row.getInt("workout_log_equipment_id"));
		session.setDate(row.getDate("workout_log_date"));
		return session;
	}

	@Override
	public List<Session> getAllSessionsPerMemberByDate(String username, Date date) {
		List<Session> perMemberSessionsByDate = new ArrayList<Session>();
		String sessions = "SELECT * FROM workout_log WHERE workout_log_username = ? AND workout_log_date = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sessions, username, date);
		while (results.next()) {
			perMemberSessionsByDate.add(mapToRowSession(results));
		}
		return perMemberSessionsByDate;
	}

	@Override
	public List<Session> getAllSessionsPerMemberByMonth(String username, String month) {
		List<Session> monthlySessions = new ArrayList<Session>();
		//find a sql string to grab all sessions by momth
		String monthNumber = getMonthNumber(month);
		String sqlMonthlySessions = "SELECT * FROM workout_log WHERE username = ? AND date = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlMonthlySessions, username, monthNumber);
		while(results.next()) {
			monthlySessions.add(mapToRowSession(results));
		}
		return monthlySessions;
	}

	private String getMonthNumber(String input) {
		input = input.toLowerCase();
		switch (input) {
		case "january":
		case "jan":
			input = "1";
			break;

		case "febuary":
		case "feb":
			input = "2";
			break;

		case "march":
		case "mar":
			input = "3";
			break;

		case "april":
		case "apr":
			input = "4";
			break;

		case "may":
			input = "5";
			break;

		case "june":
		case "jun":
			input = "6";
			break;

		case "july":
		case "jul":
			input = "7";
			break;

		case "august":
		case "aug":
			input = "8";
			break;

		case "september":
		case "sep":
		case "sept":
			input = "9";
			break;

		case "october":
		case "oct":
			input = "10";
			break;

		case "november":
		case "nov":
			input = "11";
			break;

		case "december":
		case "dec":
			input = "12";
			break;
		}
		return input;
	}
	
	public Date getCurrentDate() {
		LocalDateTime ldt = LocalDateTime.now();
		String dateString = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH).format(ldt);
		Integer month = Integer.valueOf(dateString.substring(5, 7));
		Integer year = Integer.valueOf(dateString.substring(0, 4));
		Integer day = Integer.valueOf(dateString.substring(8));
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month - 1);
		cal.set(Calendar.DAY_OF_MONTH, day);

		java.sql.Date date = new java.sql.Date(cal.getTimeInMillis());
		
		return date;
	}

	@Override
	public List<Session> getAllSessionsPerMemberByDateTime(String username, Date date, Time start) {
		List<Session> perMemberSessionsByDateTime = new ArrayList<Session>();
		String sessions = "SELECT * FROM workout_log WHERE workout_log_username = ? AND workout_log_date = ? AND workout_log_start = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sessions, username, date, start);
		while (results.next()) {
			perMemberSessionsByDateTime.add(mapToRowSession(results));
		}
		return perMemberSessionsByDateTime;

	}

	@Override
	public List<Session> getMemberSessionData(String username) {
		List<Session> getMemberSessionData = new ArrayList<Session>();
		String data = "SELECT workout_log_date, workout_log_end - workout_log_start AS duration, SUM(workout_log_reps) AS workout_log_reps, SUM(workout_log_sets) AS workout_log_sets, SUM(workout_log_weight) AS workout_log_weight " + 
				"FROM workout_log " + 
				"WHERE workout_log_username = ? " + 
				"GROUP BY workout_log_date, workout_log_end - workout_log_start " + 
				"ORDER BY workout_log_date ASC " + 
				"LIMIT 30";
		SqlRowSet results = jdbcTemplate.queryForRowSet(data, username);
		while(results.next()) {
			getMemberSessionData.add(mapToRowData(results));
		}
		return getMemberSessionData;
	}
	
	private Session mapToRowData(SqlRowSet row) {
		Session session = new Session();
		session.setSets(row.getInt("workout_log_sets"));
		session.setReps(row.getInt("workout_log_reps"));
		session.setDuration(row.getString("duration").substring(30, 37));
		session.setDate(row.getDate("workout_log_date"));
		session.setWeights(row.getInt("workout_log_weight"));
		return session;
	}
	
	@Override
	public List<Session> getAllSessionsPerMemberWithEquipment(String username) {
		List<Session> sessionsWithEquipment = new ArrayList<Session>();
		String sqlSessionPerMember = "SELECT workout_log_date, workout_log_start, workout_log_end, workout_log_reps, workout_log_sets, workout_log_weight, equipment.equipment_name FROM workout_log " + 
				"JOIN equipment on equipment.equipment_id = workout_log.workout_log_equipment_id " + 
				"WHERE workout_log_username = ? ORDER BY workout_log_date ASC LIMIT 15";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlSessionPerMember, username);
		while (results.next()) {
			sessionsWithEquipment.add(mapToRowSessionEquipment(results));
		}
		return sessionsWithEquipment;
	}
	
	private Session mapToRowSessionEquipment(SqlRowSet row) {
		Session session = new Session();
		session.setSets(row.getInt("workout_log_sets"));
		session.setReps(row.getInt("workout_log_reps"));
		session.setWeights(row.getInt("workout_log_weight"));
		session.setStart(row.getTime("workout_log_start"));
		session.setEnd(row.getTime("workout_log_end"));
		session.setEquipmentName(row.getString("equipment_name"));
		session.setDate(row.getDate("workout_log_date"));
		return session;
	}

	@Override
	public void updateTime(String username, Date date, Time start, Time end) {
		String sqlUpdateTime = "UPDATE workout_log " + 
				"SET workout_log_start = ?, workout_log_end = ? " + 
				"WHERE workout_log_username = ? AND workout_log_date = ?";
		jdbcTemplate.update(sqlUpdateTime, start, end, username, date);
		
	}

	@Override
	public Time getCurrentTime() {
		LocalDateTime ldt = LocalDateTime.now();
		String times = DateTimeFormatter.ofPattern("HH:mm:ss", Locale.ENGLISH).format(ldt);
		Integer hour = Integer.valueOf(times.substring(0, 2));
		Integer minute = Integer.valueOf(times.substring(3, 5));
		Integer sec = Integer.valueOf(times.substring(6, 8));
		@SuppressWarnings("deprecation")
		Time time = new Time(hour, minute, sec);
		return time;
	}

	@Override
	public Integer getTotalTime(String username, Date date) {
		// TODO Auto-generated method stub
		return null;
	}
}
