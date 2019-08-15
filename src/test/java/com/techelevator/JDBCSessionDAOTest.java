package com.techelevator;

import static org.junit.Assert.assertEquals;

import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import com.techelevator.model.session.JDBCSessionDAO;
import com.techelevator.model.session.Session;

public class JDBCSessionDAOTest extends DAOIntegrationTest {

	JdbcTemplate jdbcTemplate = new JdbcTemplate(this.getDataSource());
	private JDBCSessionDAO dao = new JDBCSessionDAO(this.getDataSource());

	@Before
	public void setUp() throws Exception {
		String username = "mickey";
		Integer equipmentId = 1;
		Integer reps = 50;
		Integer sets = 5;
		Integer weights = 10;
		
		//Date
		LocalDateTime ldt = LocalDateTime.now();
		System.out.println(ldt);
		String date = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH).format(ldt);
		Integer month = Integer.valueOf(date.substring(5, 7));
		Integer year = Integer.valueOf(date.substring(0, 4));
		Integer day = Integer.valueOf(date.substring(8));
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month - 1);
		cal.set(Calendar.DAY_OF_MONTH, day);
		java.sql.Date dates = new java.sql.Date(cal.getTimeInMillis());
		
		//Time
		String times = DateTimeFormatter.ofPattern("HH:mm:ss.SS", Locale.ENGLISH).format(ldt);
		Integer hour = Integer.valueOf(times.substring(0, 2));
		Integer minute = Integer.valueOf(times.substring(3, 5));
		Integer sec = Integer.valueOf(times.substring(6, 8));
		Integer millisec = Integer.valueOf(times.substring(9));
		@SuppressWarnings("deprecation")
		Time time = new Time(hour, minute, sec);
		
		Time start = dao.getCurrentTime();
		
		dao.saveSession(username, equipmentId, reps, sets, weights, dates);
		dao.updateTime(username, dates, start, start);
		
	}

	@Test
	public void getAllSessionsTest() {
		List<Session> allSessionsList = dao.getAllSessions();
		Integer size = allSessionsList.size();
		String usernameTest = "mickey";
		Integer repsTest = 50;
		Integer setsTest = 5;

		assertEquals(usernameTest, allSessionsList.get(size - 1).getUsername());
		assertEquals(repsTest, allSessionsList.get(size - 1).getReps());
		assertEquals(setsTest, allSessionsList.get(size - 1).getSets());
	}
	
	@Test
	public void getAllSessionsPerMemberTest() {
		String usernameTest = "mickey";
		Integer repsTest = 50;
		Integer setsTest = 5;
		List<Session> allSessionsPerMemberList = dao.getAllSessionsPerMember(usernameTest);
		Integer size = allSessionsPerMemberList.size();
		
		assertEquals(repsTest, allSessionsPerMemberList.get(size - 1).getReps());
		assertEquals(setsTest, allSessionsPerMemberList.get(size - 1).getSets());
	}
	
	@Test
	public void getAllSessionsPerMemberByDateTest() {
		String usernameTest = "mickey";
		Integer repsTest = 50;
		Integer setsTest = 5;
		Integer year = 2019;
		Integer month = 8;
		Integer day = 14;
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month - 1);
		cal.set(Calendar.DAY_OF_MONTH, day);

		java.sql.Date date = new java.sql.Date(cal.getTimeInMillis());
		List<Session> allSessionsPerMemberListByDate = dao.getAllSessionsPerMemberByDate(usernameTest, date);
		Integer size = allSessionsPerMemberListByDate.size();
		
		assertEquals(repsTest, allSessionsPerMemberListByDate.get(size - 1).getReps());
		assertEquals(setsTest, allSessionsPerMemberListByDate.get(size - 1).getSets());
	}
	
	
	@Test
	public void getAllSessionsPerMemberWithEquipmentTest() {
		String username = "lfitriana";
		String equipment = "Ab Crunch";
		List<Session> sessionsWithEquipment = dao.getAllSessionsPerMemberWithEquipment(username);
		Integer size = sessionsWithEquipment.size();
		
		assertEquals(equipment, sessionsWithEquipment.get(size - 1).getEquipmentName());
		
	}
	
	@Test
	public void getUpdateTimeTest() {
		Time start = dao.getCurrentTime();
		String usernameTest = "mickey";
		Integer repsTest = 50;
		Integer setsTest = 5;
		List<Session> allSessionsPerMemberList = dao.getAllSessionsPerMember(usernameTest);
		Integer size = allSessionsPerMemberList.size();
		
		assertEquals(start, allSessionsPerMemberList.get(size - 1).getStart());
		
	}
}
