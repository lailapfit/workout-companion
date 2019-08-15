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
public class JDBCMemberDAO implements MemberDAO {

	private JdbcTemplate jdbcTemplate;
	private PasswordHasher hashMaster;

	@Autowired
	public JDBCMemberDAO(DataSource dataSource, PasswordHasher hashMaster) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.hashMaster = hashMaster;
	}
	
	@Override
	public void saveMember(String userName, String password, String name, String email, String workoutGoals, String workoutProfile, String avatar) {
		byte[] salt = hashMaster.generateRandomSalt();
		String saltString = new String(Base64.encode(salt));
		
		jdbcTemplate.update("INSERT INTO profile(name, email, photo, workout_goals, workout_profile, role_id) VALUES (?, ?, ?, ?, ?, ?)",
				name, email, avatar, workoutGoals, workoutProfile, 3);
		jdbcTemplate.update("INSERT INTO login(username, password, salt, profile_id) VALUES (?, ?, ?, (SELECT max (profile.profile_id) FROM profile))",
				userName, password, saltString);	
	}
	public void updateWorkoutGoals(String updatedGoals, String username) {
		String sqlUpdateGoals = "UPDATE profile SET workout_goals = ? FROM login WHERE username = ? AND login.profile_id = profile.profile_id";
		jdbcTemplate.update(sqlUpdateGoals, updatedGoals, username);	
	}

	@Override
	public boolean searchForUsernameAndPassword(String userName, String password) {
		String sqlSearchForUser = "SELECT * "+                                                                                        
							      "FROM login "+
							      "WHERE username = ? ";
		
		SqlRowSet user = jdbcTemplate.queryForRowSet(sqlSearchForUser, userName.toLowerCase());
		if(user.next()) {
			String dbSalt = user.getString("salt");
			String dbHashedPassword = user.getString("password");
			String givenPassword = hashMaster.computeHash(password, Base64.decode(dbSalt));
			return dbHashedPassword.equals(password);
		} else {
			return false;
		}
	}
	public List<User> getAllProfileMembers() {
		List<User> allMembers = new ArrayList<User>();
		String sqlSelectAllProfileMembers = "SELECT * FROM profile";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlSelectAllProfileMembers);
		while(results.next()) {
			allMembers.add(mapToRowProfile(results));
		}
		
		return allMembers;
	}
	
	@Override
	public void updatePassword(String userName, String password) {
		jdbcTemplate.update("UPDATE login SET password = ? WHERE username = ?", password, userName);
	}

	public Object getMemberByUserName(String userName) {
		String sqlSearchForUsername = "SELECT * FROM login JOIN profile on login.profile_id = profile.profile_id "+
		"WHERE username = ?";

		SqlRowSet user = jdbcTemplate.queryForRowSet(sqlSearchForUsername, userName.toLowerCase()); 
		User thisUser = null;
		if(user.next()) {
			thisUser = new User();
			thisUser.setUserName(user.getString("username"));
			thisUser.setPassword(user.getString("password"));
			thisUser.setName(user.getString("name"));
			thisUser.setEmail(user.getString("email"));
			thisUser.setAvatar(user.getString("photo"));
			thisUser.setWorkoutGoals(user.getString("workout_goals"));
			thisUser.setWorkoutProfile(user.getString("workout_profile"));
			thisUser.setRoleId(user.getInt("role_id"));
		}
		return thisUser;
	}
	
	private User mapToRowProfile(SqlRowSet row) {
		User user = new User();
//		user.setUserName(row.getString("username"));
//		user.setPassword(row.getString("password"));
//		user.setSalt(row.getString("salt"));
		user.setName(row.getString("name"));
		user.setEmail(row.getString("email"));
		user.setAvatar(row.getString("photo"));
		user.setWorkoutGoals(row.getString("workout_goals"));
		user.setWorkoutProfile(row.getString("workout_profile"));
		user.setRoleId(row.getInt("role_id"));
		return user;
	}
}
