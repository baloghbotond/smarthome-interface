package com.project.smarthome.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.smarthome.helpers.StatusFields;
import com.project.smarthome.helpers.StatusHelper;
import com.project.smarthome.mqtt.MQTTClient;

@Controller
@RequestMapping(path = "admin")
public class AdminController {

	@Autowired
	private MQTTClient mqttClient;
	
	@Autowired
	private StatusFields statusFields;
	
	@Autowired
	private StatusHelper statusHelper;
	
	@RequestMapping("/home")
	public String home(Model model) {
		
		settingTheAttributes(model);
		
		return "admin";
	}
	
	@RequestMapping("/sleep/{room}")
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
	
	@RequestMapping("/ts/{room}")
	public String sampleTime(@PathVariable("room") String room, @RequestParam("newTsValue") String newValue,
			Model model) {

		mqttClient.publishMessage("home/" + room + "/mcu/ts/set", newValue);

		statusHelper.waitForTsValue(room);

		settingTheAttributes(model);
		
		return "admin";
	}
	
private Model settingTheAttributes(Model model) {
		
		model.addAttribute("livingroomMcuStatus", StatusHelper.displayMcuStatus("livingroom"));
		model.addAttribute("kitchenMcuStatus", StatusHelper.displayMcuStatus("kitchen"));
		model.addAttribute("outsideMcuStatus", StatusHelper.displayMcuStatus("outside"));
		
		model.addAttribute("livingroomTs", StatusHelper.displayMcuTs("livingroom"));
		model.addAttribute("kitchenTs", StatusHelper.displayMcuTs("kitchen"));
		model.addAttribute("outsideTs", StatusHelper.displayMcuTs("outside"));
		
		return model;
	}

}
