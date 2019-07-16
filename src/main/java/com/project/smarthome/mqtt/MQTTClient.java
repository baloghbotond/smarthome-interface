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
    private MqttConnectOptions connectionOptions;
	
	public void establishMqttConnection() throws MqttException {
		
		mqttClient = new MqttClient(brokerUrl, clientId);
		connectionOptions = new MqttConnectOptions();
		connectionOptions.setCleanSession(true);
		connectionOptions.setAutomaticReconnect(true);
		
		mqttClient.setCallback(mqttCallback);
		mqttClient.connect();
		
		subscribeToTheTopic("home/livingroom/lights/status");
		subscribeToTheTopic("home/livingroom/mcu/ts/value");
		subscribeToTheTopic("home/livingroom/mcu/status");
		subscribeToTheTopic("home/kitchen/lights/status");
		subscribeToTheTopic("home/kitchen/mcu/ts/value");
		subscribeToTheTopic("home/kitchen/mcu/status");
		publishMessage("home/livingroom/lights/check", "1");
		publishMessage("home/livingroom/mcu/ts/check", "1");
		publishMessage("home/livingroom/mcu/check", "1");
		publishMessage("home/kitchen/lights/check", "1");
		publishMessage("home/kitchen/mcu/ts/check", "1");
		publishMessage("home/kitchen/mcu/check", "1");
			
		System.out.println("Connected: " + mqttClient.isConnected());
	}
	
	public void publishMessage(String topic, String message) throws MqttException {
		
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
