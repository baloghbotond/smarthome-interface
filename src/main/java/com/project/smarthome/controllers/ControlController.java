package com.project.smarthome.controllers;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.project.smarthome.mqtt.MQTTClient;

@Controller
public class ControlController {

	@Autowired
	private MQTTClient mqttClient;
	
	@RequestMapping("control")
	public ModelAndView home() {
		
		ModelAndView modelAndView = new ModelAndView("control.jsp");
		
		return modelAndView;
	}
	
	@RequestMapping("lightsKitchen")
	public ModelAndView kitchen() {
		
		ModelAndView modelAndView = new ModelAndView("control.jsp");
		
		try {
			mqttClient.publishMessage("home/lights/kitchen", "0");
		} catch (MqttException e) {
			e.printStackTrace();
		}
		
		return modelAndView;
	}

	
}
