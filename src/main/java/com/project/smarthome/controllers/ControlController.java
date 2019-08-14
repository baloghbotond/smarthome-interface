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

	private MQTTClient mqttClient;
	private StatusFields statusFields;
	private StatusHelper statusHelper;

	@Autowired
	public ControlController(MQTTClient mqttClient, StatusFields statusFields, StatusHelper statusHelper) {
		this.mqttClient = mqttClient;
		this.statusFields = statusFields;
		this.statusHelper = statusHelper;
	}

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

	@RequestMapping("/all")
	public String everywhereOff(Model model) {

		mqttClient.publishMessage("home/everywhere/lights", "0");

		statusHelper.waitForLightStatus("livingroom");
		settingTheAttributes(model);

		return "control";
	}

	private Model settingTheAttributes(Model model) {

		model.addAttribute("livingroomLightStatus", statusHelper.displayLightStatus("livingroom"));
		model.addAttribute("kitchenLightStatus", statusHelper.displayLightStatus("kitchen"));
		model.addAttribute("outsideLightStatus", statusHelper.displayLightStatus("outside"));

		model.addAttribute("livingroomTemperature", statusHelper.displayTemperature("livingroom"));
		model.addAttribute("kitchenTemperature", statusHelper.displayTemperature("kitchen"));
		model.addAttribute("outsideTemperature", statusHelper.displayTemperature("outside"));

		model.addAttribute("livingroomHumidity", statusHelper.displayHumidity("livingroom"));
		model.addAttribute("kitchenHumidity", statusHelper.displayHumidity("kitchen"));
		model.addAttribute("outsideHumidity", statusHelper.displayHumidity("outside"));
		
		model.addAttribute("livingroomRegulator", statusHelper.displayLivingroomRegulatorStatus());

		return model;
	}
}
