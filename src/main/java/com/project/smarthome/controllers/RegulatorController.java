package com.project.smarthome.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.smarthome.helpers.StatusHelper;
import com.project.smarthome.mqtt.MQTTClient;

@Controller
@RequestMapping(path = "regulator")
public class RegulatorController {

	private MQTTClient mqttClient;
	private StatusHelper statusHelper;

	@Autowired
	public RegulatorController(MQTTClient mqttClient, StatusHelper statusHelper) {
		this.mqttClient = mqttClient;
		this.statusHelper = statusHelper;
	}
	
	@RequestMapping("/home")
	public String home(Model model) {

		settingTheAttributes(model);

		return "regulator";
	}
	
	@RequestMapping("/set_optimum")
	public String setOptimum(@RequestParam(name = "optimum") String optimum, Model model) {

		mqttClient.publishMessage("home/livingroom/regulator/optimum/set", optimum);
		// delay for the javascript to submit the range
		statusHelper.waiting(1000);
		
		settingTheAttributes(model);
		return "regulator";
	}
	
	@RequestMapping("/set_range")
	public String setRange(@RequestParam(name = "range") String range, Model model) {
		
		mqttClient.publishMessage("home/livingroom/regulator/range/set", range);
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
