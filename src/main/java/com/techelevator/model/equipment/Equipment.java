package com.techelevator.model.equipment;

public class Equipment {
	
	private Integer equipment_id;
	private String name;
	private String muscleGroup;
	private String description;
	private String video;
	
	//getters and setters
	
	public String getMuscleGroup() {
		return muscleGroup;
	}
	public Integer getEquipment_id() {
		return equipment_id;
	}
	public void setEquipment_id(Integer equipment_id) {
		this.equipment_id = equipment_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setMuscleGroup(String muscleGroup) {
		this.muscleGroup = muscleGroup;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getVideo() {
		return video;
	}
	public void setVideo(String video) {
		this.video = video;
	}
}
