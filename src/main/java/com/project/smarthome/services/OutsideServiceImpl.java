package com.project.smarthome.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.smarthome.entities.Outside;
import com.project.smarthome.repositories.OutsideRepository;

@Service
public class OutsideServiceImpl implements OutsideService{

	private OutsideRepository outsideRepo;
	
	@Autowired
	public OutsideServiceImpl(OutsideRepository outsideRepo) {
		this.outsideRepo = outsideRepo;
	}

	@Override
	public List<Outside> findAll() {
		return outsideRepo.findAll();
	}

	@Override
	public Outside findById(int id) {
		Optional<Outside> result = outsideRepo.findById(id);
		
		Outside outsideObj = null;
		
		if(result.isPresent()) {
			outsideObj = result.get();
		}
		else {
			throw new RuntimeException("Did not find outsideObj id - " + id);
		}
		
		return outsideObj;
	}

	@Override
	public void save(Outside outsideObj) {
		outsideRepo.save(outsideObj);
	}

	@Override
	public void deleteById(int id) {
		// TODO Auto-generated method stub
		
	}

}
