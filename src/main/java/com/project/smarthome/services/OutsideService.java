package com.project.smarthome.services;

import java.util.List;

import com.project.smarthome.entities.Outside;

public interface OutsideService {

public List<Outside> findAll();
	
	public Outside findById(int id);
	
	public void save(Outside outsideObj);
	
	public void deleteById(int id);
}
