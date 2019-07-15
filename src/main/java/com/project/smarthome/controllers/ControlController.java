package com.project.smarthome.controllers;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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
	public ModelAndView home() {
		System.out.println(mqttClient.isConnected());
		ModelAndView modelAndView = new ModelAndView("control.jsp");
		
		return modelAndView;
	}
	
	@RequestMapping("/{room}")
	public ModelAndView lights(@PathVariable("room") String room) {
		System.out.println(mqttClient.isConnected());
		ModelAndView modelAndView = new ModelAndView("control.jsp");
		
		try {	
			if(statusFields.getLightStatus(room) == 0) {
				mqttClient.publishMessage("home/" + room + "/lights", "1");
			}
			
			if(statusFields.getLightStatus(room) == 1) {
				mqttClient.publishMessage("home/" + room + "/lights", "0");		
			}
			
			statusHelper.waitForLightStatus(room);

		} catch (MqttException e) {
			e.printStackTrace();
		}
		
		return modelAndView;
	}

	
}
