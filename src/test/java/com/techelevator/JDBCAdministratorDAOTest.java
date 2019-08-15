package com.techelevator;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import com.techelevator.model.JDBCAdministratorDAO;
import com.techelevator.model.User;
import com.techelevator.security.PasswordHasher;

public class JDBCAdministratorDAOTest extends DAOIntegrationTest {
	JdbcTemplate jdbcTemplate = new JdbcTemplate(this.getDataSource());
	
	private PasswordHasher hashMaster = new PasswordHasher();
	private JDBCAdministratorDAO dao = new JDBCAdministratorDAO(this.getDataSource(),  this.hashMaster);
	
	@Before
	public void setUp() throws Exception {
		String usernameTest = "test";
		String passwordTest = "testpassword";
		String nameTest = "Goofy";
		String emailTest = "goofy@icloud.com";
		String workoutGoalsTest = "Goofing around";
		String workoutProfileTest = "Lifting";
		String avatarTest = "goofy";
		int roleId = 2; 
		dao.addEmployee(usernameTest, passwordTest, nameTest, emailTest, workoutGoalsTest, workoutProfileTest, avatarTest, roleId);
	}

	@Test
	public void getAllEmployeesTest() {
		List<User> allMembersTest = dao.getAllEmployees();
		Integer size = allMembersTest.size();	

		assertEquals("Goofy", allMembersTest.get(size - 1).getName());
		assertEquals("goofy@icloud.com", allMembersTest.get(size - 1).getEmail());
		assertEquals("Goofing around", allMembersTest.get(size - 1).getWorkoutGoals());
	}

}
