package com.techelevator;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import com.techelevator.model.JDBCMemberDAO;
import com.techelevator.model.User;
import com.techelevator.security.PasswordHasher;
import static org.junit.Assert.assertEquals;

public class JDBCMemberDAOTest extends DAOIntegrationTest{
	JdbcTemplate jdbcTemplate = new JdbcTemplate(this.getDataSource());
	
	private PasswordHasher hashMaster = new PasswordHasher();
	private JDBCMemberDAO dao = new JDBCMemberDAO(this.getDataSource(),  this.hashMaster);
	
	@Before
	public void setUp() throws Exception {
		String usernameTest = "test";
		String passwordTest = "testpassword";
		String nameTest = "Mickey";
		String emailTest = "mickey@icloud.com";
		String workoutGoalsTest = "Running around";
		String workoutProfileTest = "Runner";
		String avatarTest = "mickey";
		dao.saveMember(usernameTest, passwordTest, nameTest, emailTest, workoutGoalsTest, workoutProfileTest, avatarTest);
	}

	@Test
	public void saveMemberTest() {
		
		List<User> allMembersTest = dao.getAllProfileMembers();
		Integer size = allMembersTest.size();	

		assertEquals("Mickey", allMembersTest.get(size - 1).getName());
		assertEquals("mickey@icloud.com", allMembersTest.get(size - 1).getEmail());
		assertEquals("Running around", allMembersTest.get(size - 1).getWorkoutGoals());
		
	}
	
	@Test
	public void updateWorkoutGoalsTest() {
		String updatedGoalsTest = "Lifting";
		dao.updateWorkoutGoals(updatedGoalsTest, "test");
		List<User> allMembersTest = dao.getAllProfileMembers();
		Integer size = allMembersTest.size();
		
		assertEquals(updatedGoalsTest, allMembersTest.get(size - 1).getWorkoutGoals());
	}

}
