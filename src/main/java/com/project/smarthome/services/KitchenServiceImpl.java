package com.project.smarthome.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.smarthome.entities.Kitchen;
import com.project.smarthome.repositories.KitchenRepository;

@Service
public class KitchenServiceImpl implements KitchenService {

	private KitchenRepository kitchenRepo;
	
	@Autowired
	public KitchenServiceImpl(KitchenRepository kitchenRepo) {
		this.kitchenRepo = kitchenRepo;
	}
	
	@Override
	public List<Kitchen> findAll() {
		return kitchenRepo.findAll();
	}

	@Override
	public Kitchen findById(int id) {
		Optional<Kitchen> result = kitchenRepo.findById(id);
		
		Kitchen kitchenObj = null;
		
		if(result.isPresent()) {
			kitchenObj = result.get();
		}
		else {
			throw new RuntimeException("Did not find kitchenObj id - " + id);
		}
		
		return kitchenObj;
	}

	@Override
	public void save(Kitchen kitchenObj) {
		kitchenRepo.save(kitchenObj);
	}

	@Override
	public void deleteById(int id) {
		// TODO Auto-generated method stub
		
	}

}
