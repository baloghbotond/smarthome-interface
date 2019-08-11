package com.project.smarthome.services;

import java.util.List;

import com.project.smarthome.entities.Livingroom;

public interface LivingroomService {

public List<Livingroom> findAll();
	
	public Livingroom findById(int id);
	
	public void save(Livingroom livingroomObj);
	
	public void deleteById(int id);
}
