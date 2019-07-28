package com.project.smarthome.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.project.smarthome.helpers.StatusFields;
import com.project.smarthome.helpers.StatusHelper;
import com.project.smarthome.mqtt.MQTTClient;

@Controller
@RequestMapping(path = "control")
public class ControlController {

	@Autowired
	private MQTTClient mqttClient;
	
	@Autowired
	private StatusFields statusFields;
	
	@Autowired
	private StatusHelper statusHelper;
	
	@RequestMapping("/home")
	public String home(Model model) {
		
		settingTheAttributes(model);
		
		return "control";
	}
	
	@RequestMapping("/{room}")
	public String lights(@PathVariable("room") String room, Model model) {
			
		if (statusFields.getLightStatus(room) == 0) {
			mqttClient.publishMessage("home/" + room + "/lights", "1");
		}

		if (statusFields.getLightStatus(room) == 1) {
			mqttClient.publishMessage("home/" + room + "/lights", "0");
		}

		statusHelper.waitForLightStatus(room);

		settingTheAttributes(model);
		
		return "control";
	}
	
	@RequestMapping("allLightsOff")
	public String everywhereOff(Model model) {
		
		mqttClient.publishMessage("home/everywhere/lights", "0");
		
		settingTheAttributes(model);
		
		return "control";
	}
	
	private Model settingTheAttributes(Model model) {
		
		model.addAttribute("livingroomLightStatus", StatusHelper.displayLightStatus("livingroom"));
		model.addAttribute("kitchenLightStatus", StatusHelper.displayLightStatus("kitchen"));
		model.addAttribute("outsideLightStatus", StatusHelper.displayLightStatus("outside"));
		
		model.addAttribute("livingroomTemperature", StatusHelper.displayTemperature("livingroom"));
		model.addAttribute("kitchenTemperature", StatusHelper.displayTemperature("kitchen"));
		model.addAttribute("outsideTemperature", StatusHelper.displayTemperature("outside"));
		
		model.addAttribute("livingroomHumidity", StatusHelper.displayHumidity("livingroom"));
		model.addAttribute("kitchenHumidity", StatusHelper.displayHumidity("kitchen"));
		model.addAttribute("outsideHumidity", StatusHelper.displayHumidity("outside"));
		
		return model;
	}
}
