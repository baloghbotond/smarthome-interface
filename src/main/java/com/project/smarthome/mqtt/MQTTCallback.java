package com.project.smarthome.mqtt;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Component;

@Component
public class MQTTCallback implements MqttCallback{

	@Override
	public void connectionLost(Throwable cause) {

	}

	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		System.out.println("The following message has arrived: " + message.toString());
		
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken token) {
	
	}

}
