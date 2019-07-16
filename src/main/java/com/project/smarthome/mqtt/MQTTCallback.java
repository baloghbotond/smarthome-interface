package com.project.smarthome.mqtt;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.project.smarthome.helpers.StatusFields;

@Component
public class MQTTCallback implements MqttCallback{

	@Autowired
	private StatusFields statusFields;
	
	@Override
	public void connectionLost(Throwable cause) {

	}

	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		// System.out.println("The following topic and message have arrived: " + topic + " " + message.toString());
		
		if(topic.equals("home/kitchen/lights/status") && message.toString().equals("0")) {
			statusFields.setKitchenLightStatus(0);
		}
		
		if(topic.equals("home/kitchen/lights/status") && message.toString().equals("1")) {
			statusFields.setKitchenLightStatus(1);
		}
		
		if(topic.equals("home/livingroom/lights/status") && message.toString().equals("0")) {
			statusFields.setLivingroomLightStatus(0);
		}
		
		if(topic.equals("home/livingroom/lights/status") && message.toString().equals("1")) {
			statusFields.setLivingroomLightStatus(1);
		}
		
		if(topic.equals("home/outside/lights/status") && message.toString().equals("0")) {
			statusFields.setOutsideLightStatus(0);
		}
		
		if(topic.equals("home/outside/lights/status") && message.toString().equals("1")) {
			statusFields.setLivingroomLightStatus(1);
		}
		
		if(topic.equals("home/kitchen/mcu/status") && message.toString().equals("0")) {
			statusFields.setKitchenMcuStatus(0);
		}
		
		if(topic.equals("home/kitchen/mcu/status") && message.toString().equals("1")) {
			statusFields.setKitchenMcuStatus(1);
		}
		
		if(topic.equals("home/livingroom/mcu/status") && message.toString().equals("0")) {
			statusFields.setLivingroomMcuStatus(0);
		}
		
		if(topic.equals("home/livingroom/mcu/status") && message.toString().equals("1")) {
			statusFields.setLivingroomMcuStatus(1);
		}
		
		if(topic.equals("home/outside/mcu/status") && message.toString().equals("0")) {
			statusFields.setOutsideMcuStatus(0);
		}
		
		if(topic.equals("home/outside/mcu/status") && message.toString().equals("1")) {
			statusFields.setOutsideMcuStatus(1);
		}
		
		if(topic.equals("home/kitchen/mcu/ts/value")) {
			statusFields.setKitchenTs(Integer.parseInt(message.toString()));
		}
		
		if(topic.equals("home/livingroom/mcu/ts/value")) {
			statusFields.setLivingroomTs(Integer.parseInt(message.toString()));
		}
		
		if(topic.equals("home/outside/mcu/ts/value")) {
			statusFields.setOutsideTs(Integer.parseInt(message.toString()));
		}

	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken token) {
		
	}
}
