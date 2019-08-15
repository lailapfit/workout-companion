package com.techelevator;

import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import com.techelevator.security.PasswordHasher;

public class JDBCEmployeeDAOTest extends DAOIntegrationTest {
	JdbcTemplate jdbcTemplate = new JdbcTemplate(this.getDataSource());
	
	private PasswordHasher hashMaster = new PasswordHasher();
	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
