package com.project.smarthome.mqtt;

import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.annotation.PostConstruct;

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
	
	@Value("${mqtt.client.username}")
    private String clientUsername;
	
	@Value("${mqtt.client.password}")
	private String clientPassword;
	
	@Value("${mqtt.rooms}")
	private String allRooms;
	
	@Autowired
	private MQTTCallback mqttCallback;
    
    private MqttClient mqttClient;
    private MqttConnectOptions connectionOptions;
	
    @PostConstruct
	public void establishMqttConnection() {
		
    	try {
			mqttClient = new MqttClient(brokerUrl, clientUsername);
			connectionOptions = new MqttConnectOptions();
			connectionOptions.setCleanSession(true);
			connectionOptions.setAutomaticReconnect(true);
			connectionOptions.setUserName(clientUsername);
			connectionOptions.setPassword(passwordToCharArray(clientPassword));
			
			byte[] payload = {'1'};
			connectionOptions.setWill("home/everywhere/mcu/off", payload, 2, false);
			
			mqttClient.setCallback(mqttCallback);
			mqttClient.connect(connectionOptions);
			
			System.out.println("Connected: " + mqttClient.isConnected());
			
			subAndPubInit();
			
    	} catch(MqttException ex) {
    		System.out.println("The connection cannot be established.");
    	}
	}
	
	public void publishMessage(String topic, String message) {
		
		byte[] payload = message.getBytes();
		MqttMessage mqttMessage = new MqttMessage(payload);

		try {
			mqttClient.publish(topic, mqttMessage);
		} catch (MqttException e) {
			System.out.println("The message cannot be published in topic: " + topic);
		}
	}
	
	public void publishMessageWithQos(String topic, String message, int qos) {
		
		try {
			byte[] payload = message.getBytes();
			mqttClient.publish(topic, payload, qos, false);
		} catch (MqttException e) {
			System.out.println("The message cannot be published in topic: " + topic);
		}
	}
	
	public void subscribeToTheTopic(String topic) {
		
		try {
			mqttClient.subscribe(topic);
		} catch (MqttException e) {
			System.out.println("The subscription failed: " + topic);
		}
	}
	
	public boolean isConnected() {
		return mqttClient.isConnected();
	}
	
	private void subAndPubInit() throws MqttException {
		
		ArrayList<String> listOfRooms = new ArrayList<>();
		StringTokenizer stringTokenizer = new StringTokenizer(allRooms, ";");
		while(stringTokenizer.hasMoreTokens()) {
			listOfRooms.add(stringTokenizer.nextToken());
		}
		
		subscribeToTheTopic("home/java/app/check");
		subscribeToTheTopic("home/livingroom/regulator/status");
		subscribeToTheTopic("home/livingroom/regulator/optimum/value");
		subscribeToTheTopic("home/livingroom/regulator/range/value");
		publishMessage("home/java/app/status", "1");
		publishMessage("home/livingroom/regulator/check", "1");
		publishMessage("home/livingroom/regulator/optimum/check", "1");
		publishMessage("home/livingroom/regulator/range/check", "1");
		
		for(String room : listOfRooms) {
			subscribeToTheTopic("home/" + room + "/lights/status");
			subscribeToTheTopic("home/" + room + "/mcu/status");
			subscribeToTheTopic("home/" + room + "/temperature/value");
			subscribeToTheTopic("home/" + room + "/humidity/value");
			subscribeToTheTopic("home/" + room + "/time/sync/req");
			subscribeToTheTopic("home/" + room + "/object");
			publishMessage("home/" + room + "/mcu/on", "1");
			publishMessage("home/" + room + "/temperature/check", "1");
			publishMessage("home/" + room + "/lights/check", "1");
			publishMessage("home/" + room + "/mcu/check", "1");
			publishMessage("home/" + room + "/humidity/check", "1");
		}
	}
	
	private char[] passwordToCharArray(String password) {
		
		char[] passwordArray = password.toCharArray();
		return passwordArray;
	}
}
