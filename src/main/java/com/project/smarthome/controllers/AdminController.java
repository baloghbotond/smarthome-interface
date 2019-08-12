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
@RequestMapping(path = "admin")
public class AdminController {

	private MQTTClient mqttClient;
	private StatusFields statusFields;
	private StatusHelper statusHelper;
	
	@Autowired
	public AdminController(MQTTClient mqttClient, StatusFields statusFields, StatusHelper statusHelper) {
		this.mqttClient = mqttClient;
		this.statusFields = statusFields;
		this.statusHelper = statusHelper;
	}

	@RequestMapping("/home")
	public String home(Model model) {
		
		settingTheAttributes(model);
		
		return "admin";
	}
	
	@RequestMapping("/mcu_{room}")
	public String mcuStatus(@PathVariable("room") String room, Model model) {

		if (statusFields.getMcuStatus(room) == 0) {
			mqttClient.publishMessage("home/" + room + "/mcu/on", "1");
		}

		if (statusFields.getMcuStatus(room) == 1) {
			mqttClient.publishMessage("home/" + room + "/mcu/off", "1");
		}

		statusHelper.waitForMcuStatus(room);

		settingTheAttributes(model);

		return "admin";
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
		
		return "admin";
	}
	
	@RequestMapping("/all")
	public String everywhereOff(Model model) {
		
		mqttClient.publishMessage("home/everywhere/lights", "0");
		
		statusHelper.waitForLightStatus("livingroom");
		settingTheAttributes(model);
		
		return "admin";
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
		
		model.addAttribute("livingroomMcuStatus", StatusHelper.displayMcuStatus("livingroom"));
		model.addAttribute("kitchenMcuStatus", StatusHelper.displayMcuStatus("kitchen"));
		model.addAttribute("outsideMcuStatus", StatusHelper.displayMcuStatus("outside"));
		
		return model;
	}

}
