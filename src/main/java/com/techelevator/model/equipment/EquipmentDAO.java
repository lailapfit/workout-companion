package com.techelevator.model.equipment;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public interface EquipmentDAO {
	
	public void addEquipment(Equipment equipment);
	public List<Equipment> getAllEquipments();
}
