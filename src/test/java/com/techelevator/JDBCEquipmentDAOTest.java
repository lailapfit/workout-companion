package com.techelevator;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import com.techelevator.model.equipment.Equipment;
import com.techelevator.model.equipment.JDBCEquipmentDAO;

public class JDBCEquipmentDAOTest extends DAOIntegrationTest{
	
	JdbcTemplate jdbcTemplate = new JdbcTemplate(this.getDataSource());	
	private JDBCEquipmentDAO dao = new JDBCEquipmentDAO(this.getDataSource());
	
	@Before
	public void setUp() throws Exception {
		String name = "Squat Rack";
		String muscleGroup = "Legs";
		String description = "Low bar squat";
		String video = "youtube";
		
		Equipment test = new Equipment();
		test.setName(name);
		test.setMuscleGroup(muscleGroup);
		test.setDescription(description);
		test.setVideo(video);
		
		dao.addEquipment(test);
	}

	@Test
	public void getAllEquipmentTest() {
		List<Equipment> allEquipments = dao.getAllEquipments();
		Integer size = allEquipments.size();
		String name = "Squat Rack";
		String muscleGroup = "Legs";
		String description = "Low bar squat";
		
		assertEquals(name, allEquipments.get(size - 1).getName());
	}

}
