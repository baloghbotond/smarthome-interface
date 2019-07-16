package com.project.smarthome.controllers;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

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
	public ModelAndView home() {
		
		ModelAndView modelAndView = new ModelAndView("admin.jsp");
		
		return modelAndView;
	}
	
	@RequestMapping("/sleep_{room}")
	public ModelAndView mcuStatus(@PathVariable("room") String room) {

		ModelAndView modelAndView = new ModelAndView("admin.jsp");
		
		try {
			if(statusFields.getMcuStatus(room) == 0) {
				mqttClient.publishMessage("home/" + room + "/mcu/on", "1");
			}
			
			if(statusFields.getMcuStatus(room) == 1) {
				mqttClient.publishMessage("home/" + room + "/mcu/off", "1");
			}
			
			statusHelper.waitForMcuStatus(room);
			
		} catch (MqttException e) {
				e.printStackTrace();
		}
		return modelAndView;
	}
	
	@RequestMapping("/ts_{room}")
	public ModelAndView sampleTime(@PathVariable("room") String room, @RequestParam("newTsValue") String newValue) {
		
		ModelAndView modelAndView = new ModelAndView("admin.jsp");
		
		try {
			mqttClient.publishMessage("home/" + room + "/mcu/ts/set", newValue);
			
			statusHelper.waitForTsValue(room);
			
		} catch (MqttException e) {
			e.printStackTrace();
		}
		
		return modelAndView;
	}

}
