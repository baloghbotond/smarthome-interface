package com.project.smarthome;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.project.smarthome.mqtt.MQTTClient;

@SpringBootApplication
public class SmarthomeApplication {
	
	private static MQTTClient mqttClient;
	
	@Autowired
	public SmarthomeApplication(MQTTClient mqttClient) {
		this.mqttClient = mqttClient;
	}

	public static void main(String[] args) {

		SpringApplication.run(SmarthomeApplication.class, args);
		
		try {
			mqttClient.establishMqttConnection();
			mqttClient.subscribeToTheTopic("home/rpi");
			mqttClient.publishMessage("home/desktop", "hello");
			
		} catch (MqttException ex) {
			ex.printStackTrace();
		}
		
	}

}
