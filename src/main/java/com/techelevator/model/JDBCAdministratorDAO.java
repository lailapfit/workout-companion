package com.techelevator.model;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.bouncycastle.util.encoders.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import com.techelevator.security.PasswordHasher;

@Component
public class JDBCAdministratorDAO implements AdministratorDAO {

	private JdbcTemplate jdbcTemplate;
	private PasswordHasher hashMaster;
	
	@Autowired
	public JDBCAdministratorDAO(DataSource dataSource, PasswordHasher hashMaster) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.hashMaster = hashMaster;
	}
	
	@Override
	public void addEmployee(String userName, String password, String name, String email, String workoutGoals,
			String workoutProfile, String avatar, int roleId) {
		byte[] salt = hashMaster.generateRandomSalt();
		String saltString = new String(Base64.encode(salt));
		
		jdbcTemplate.update("INSERT INTO profile(name, email, photo, workout_goals, workout_profile, role_id) VALUES (?, ?, ?, ?, ?, ?)",
				name, email, avatar, workoutGoals, workoutProfile, roleId);
		jdbcTemplate.update("INSERT INTO login(username, password, salt, profile_id) VALUES (?, ?, ?, (SELECT max (profile.profile_id) FROM profile))",
				userName, password, saltString);	
	}

	@Override
	public List<User> getAllEmployees() {
		List<User> allEmployeesList = new ArrayList<User>();
		String sqlSelectAllProfileMembers = "SELECT * FROM profile WHERE role_id = 2";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlSelectAllProfileMembers);
		while(results.next()) {
			allEmployeesList.add(mapToRowEmployee(results));
		} 
		
		return allEmployeesList;
	}
	
	private User mapToRowEmployee(SqlRowSet row) {
		User user = new User();
		user.setName(row.getString("name"));
		user.setEmail(row.getString("email"));
		user.setAvatar(row.getString("photo"));
		user.setWorkoutGoals(row.getString("workout_goals"));
		user.setWorkoutProfile(row.getString("workout_profile"));
		user.setRoleId(row.getInt("role_id"));
		return user;
	}

}
