package com.project.smarthome.mqtt;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Component;

@Component
public class MQTTCallback implements MqttCallback{
	
	private int livingroomLightStatus;
	private int kitchenLightStatus;
	private int outsideLightStatus;

	@Override
	public void connectionLost(Throwable cause) {

	}

	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		System.out.println("The following topic and message have arrived: " + topic + " " + message.toString());
		
		if(topic.equals("home/kitchen/lights/status") && message.toString().equals("0")) {
			kitchenLightStatus = 0;
		}
		
		if(topic.equals("home/kitchen/lights/status") && message.toString().equals("1")) {
			kitchenLightStatus = 1;
		}
		
		if(topic.equals("home/livingroom/lights/status") && message.toString().equals("0")) {
			livingroomLightStatus = 0;
		}
		
		if(topic.equals("home/livingroom/lights/status") && message.toString().equals("1")) {
			livingroomLightStatus = 1;
		}
		
		if(topic.equals("home/outside/lights/status") && message.toString().equals("0")) {
			outsideLightStatus = 0;
		}
		
		if(topic.equals("home/outside/lights/status") && message.toString().equals("1")) {
			outsideLightStatus = 1;
		}

	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken token) {
	
	}

	public int getLivingroomLightStatus() {
		return livingroomLightStatus;
	}

	public int getKitchenLightStatus() {
		return kitchenLightStatus;
	}

	public int getOutsideLightStatus() {
		return outsideLightStatus;
	}
	
	public int getAnyStatus(String room) {
		
		if(room.equals("livingroom")) {
			return livingroomLightStatus;
		}
		
		else if(room.equals("kitchen")) {
			return kitchenLightStatus;
		}
		
		else {
			return outsideLightStatus;
		}

	}

}
