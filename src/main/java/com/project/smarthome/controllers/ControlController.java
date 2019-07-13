package com.project.smarthome.controllers;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.project.smarthome.mqtt.MQTTCallback;
import com.project.smarthome.mqtt.MQTTClient;

@Controller
public class ControlController {

	@Autowired
	private MQTTClient mqttClient;
	
	@Autowired
	private MQTTCallback mqttCallback;
	
	@RequestMapping("control")
	public ModelAndView home() {
		
		ModelAndView modelAndView = new ModelAndView("control.jsp");
		
		return modelAndView;
	}
	
	@RequestMapping("{room}")
	public ModelAndView livingroom(@PathVariable("room") String room) {
	
		ModelAndView modelAndView = new ModelAndView("control.jsp");
		
		try {
			if(mqttCallback.getAnyStatus(room) == 0) {
				mqttClient.publishMessage("home/" + room + "/lights", "1");
			}
			
			if(mqttCallback.getAnyStatus(room) == 1) {
				mqttClient.publishMessage("home/" + room + "/lights", "0");
					
			}

		} catch (MqttException e) {
			e.printStackTrace();
		}
		
		return modelAndView;
	}

	
}
