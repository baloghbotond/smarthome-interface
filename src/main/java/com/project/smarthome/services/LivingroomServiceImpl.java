package com.project.smarthome.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.smarthome.entities.Livingroom;
import com.project.smarthome.repositories.LivingroomRepository;

@Service
public class LivingroomServiceImpl implements LivingroomService{

	private LivingroomRepository livingroomRepo;
	
	@Autowired
	public LivingroomServiceImpl(LivingroomRepository livingroomRepo) {
		this.livingroomRepo = livingroomRepo;
	}
	
	@Override
	public List<Livingroom> findAll() {
		return livingroomRepo.findAll();
	}

	@Override
	public Livingroom findById(int id) {
		Optional<Livingroom> result = livingroomRepo.findById(id);
		
		Livingroom livingroomObj = null;
		
		if(result.isPresent()) {
			livingroomObj = result.get();
		}
		else {
			throw new RuntimeException("Did not find livingroomObj id - " + id);
		}
		
		return livingroomObj;
	}

	@Override
	public void save(Livingroom livingroonObj) {
		livingroomRepo.save(livingroonObj);
	}

	@Override
	public void deleteById(int id) {
		// TODO Auto-generated method stub
		
	}

}
