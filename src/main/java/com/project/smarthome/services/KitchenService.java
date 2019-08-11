package com.project.smarthome.services;

import java.util.List;

import com.project.smarthome.entities.Kitchen;

public interface KitchenService {

	public List<Kitchen> findAll();
	
	public Kitchen findById(int id);
	
	public void save(Kitchen kitchenObj);
	
	public void deleteById(int id);
}
