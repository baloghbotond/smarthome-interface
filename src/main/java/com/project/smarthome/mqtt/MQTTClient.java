package com.project.smarthome.mqtt;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MQTTClient {

	@Value("${mqtt.broker.url}")
    private String brokerUrl;
	
	@Value("${mqtt.client.id}")
    private String clientId;
	
	@Autowired
	private MQTTCallback mqttCallback;
    
    private MqttClient mqttClient;
	
	public void establishMqttConnection() throws MqttException {
		
		mqttClient = new MqttClient(brokerUrl, clientId);
		MqttConnectOptions connectionOptions = new MqttConnectOptions();
		connectionOptions.setCleanSession(true);
		
		mqttClient.setCallback(mqttCallback);
		while(!mqttClient.isConnected()) {
			mqttClient.connect();
		}	
		
		subscribeToTheTopic("home/livingroom/lights/status");
		subscribeToTheTopic("home/livingroom/mcu/ts/value");
		subscribeToTheTopic("home/livingroom/mcu/status");
		publishMessage("home/livingroom/lights/check", "1");
		publishMessage("home/livingroom/mcu/ts/check", "1");
		publishMessage("home/livingroom/mcu/check", "1");
			
		System.out.println("The connection has been established.");
	}
	
	public void publishMessage(String topic, String message) throws MqttException {
		System.out.println(mqttClient.isConnected());
		byte[] payload = message.getBytes();
		MqttMessage mqttMessage = new MqttMessage(payload);
		
		mqttClient.publish(topic, mqttMessage);
	}
	
	public void subscribeToTheTopic(String topic) throws MqttException {
		
		mqttClient.subscribe(topic);
		
	}
	
	public boolean isConnected() {
		return mqttClient.isConnected();
	}
}
