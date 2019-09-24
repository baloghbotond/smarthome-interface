package com.project.smarthome.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.smarthome.helpers.StatusHelper;
import com.project.smarthome.mqtt.MQTTClient;
import com.project.smarthome.security.RotEncryption;

@Controller
@RequestMapping(path = "regulator")
public class RegulatorController {

	private MQTTClient mqttClient;
	private StatusHelper statusHelper;
	private RotEncryption rotEncryption;

	@Autowired
	public RegulatorController(MQTTClient mqttClient, StatusHelper statusHelper, RotEncryption rotEncryption) {
		this.mqttClient = mqttClient;
		this.statusHelper = statusHelper;
		this.rotEncryption = rotEncryption;
	}
	
	@GetMapping("/home")
	public String home(Model model) {

		settingTheAttributes(model);

		return "regulator";
	}
	
	@PostMapping("/set_optimum")
	public String setOptimum(@RequestParam String optimum, Model model) {

		mqttClient.publishMessage("home/livingroom/regulator/optimum/set", rotEncryption.encryption(optimum));
		// delay for the javascript to submit the range
		statusHelper.waiting(1000);
		
		settingTheAttributes(model);
		return "regulator";
	}
	
	@PostMapping("/set_range")
	public String setRange(@RequestParam String range, Model model) {
		
		mqttClient.publishMessage("home/livingroom/regulator/range/set", rotEncryption.encryption(range));
		statusHelper.waitForNewRegulatorValues();
		
		settingTheAttributes(model);
		
		return "regulator";
	}
	
	private Model settingTheAttributes(Model model) {

		model.addAttribute("livingroomHumidity", statusHelper.displayHumidity("livingroom"));
		model.addAttribute("regulatorOptimum", statusHelper.displayRegulatorOptimum());
		model.addAttribute("regulatorRange", statusHelper.displayRegulatorRange());
		model.addAttribute("regulatorStatus", statusHelper.displayRegulatorStatus());

		return model;
	}
}
