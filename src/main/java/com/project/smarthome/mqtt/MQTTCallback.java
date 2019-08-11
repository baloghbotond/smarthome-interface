package com.project.smarthome.mqtt;

import java.util.Calendar;
import java.util.StringTokenizer;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.project.smarthome.helpers.McuTimeSync;
import com.project.smarthome.helpers.StatusFields;
import com.project.smarthome.helpers.StatusHelper;

@Component
public class MQTTCallback implements MqttCallback {

	@Autowired
	private StatusFields statusFields;

	@Autowired
	private MQTTClient mqttClient;
	
	@Autowired
	private StatusHelper statusHelper;

	@Override
	public void connectionLost(Throwable cause) {

	}

	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		// System.out.println("The following topic and message have arrived: " + topic +
		// " " + message.toString());

		if (topic.equals("home/kitchen/lights/status") && message.toString().equals("0")) {
			statusFields.setKitchenLightStatus(0);
		}

		if (topic.equals("home/kitchen/lights/status") && message.toString().equals("1")) {
			statusFields.setKitchenLightStatus(1);
		}

		if (topic.equals("home/livingroom/lights/status") && message.toString().equals("0")) {
			statusFields.setLivingroomLightStatus(0);
		}

		if (topic.equals("home/livingroom/lights/status") && message.toString().equals("1")) {
			statusFields.setLivingroomLightStatus(1);
		}

		if (topic.equals("home/outside/lights/status") && message.toString().equals("0")) {
			statusFields.setOutsideLightStatus(0);
		}

		if (topic.equals("home/outside/lights/status") && message.toString().equals("1")) {
			statusFields.setLivingroomLightStatus(1);
		}

		if (topic.equals("home/kitchen/mcu/status") && message.toString().equals("0")) {
			statusFields.setKitchenMcuStatus(0);
		}

		if (topic.equals("home/kitchen/mcu/status") && message.toString().equals("1")) {
			statusFields.setKitchenMcuStatus(1);
		}

		if (topic.equals("home/livingroom/mcu/status") && message.toString().equals("0")) {
			statusFields.setLivingroomMcuStatus(0);
		}

		if (topic.equals("home/livingroom/mcu/status") && message.toString().equals("1")) {
			statusFields.setLivingroomMcuStatus(1);
		}

		if (topic.equals("home/outside/mcu/status") && message.toString().equals("0")) {
			statusFields.setOutsideMcuStatus(0);
		}

		if (topic.equals("home/outside/mcu/status") && message.toString().equals("1")) {
			statusFields.setOutsideMcuStatus(1);
		}

		if (topic.equals("home/kitchen/mcu/ts/value")) {
			statusFields.setKitchenTs(Integer.parseInt(message.toString()));
		}

		if (topic.equals("home/livingroom/mcu/ts/value")) {
			statusFields.setLivingroomTs(Integer.parseInt(message.toString()));
		}

		if (topic.equals("home/outside/mcu/ts/value")) {
			statusFields.setOutsideTs(Integer.parseInt(message.toString()));
		}

		if (topic.equals("home/kitchen/temperature/value")) {
			statusFields.setActualKitchenTemp(Integer.parseInt(message.toString()));
			statusFields.setNewKitchenTempFlag(!statusFields.isNewKitchenTempFlag());
		}

		if (topic.equals("home/livingroom/temperature/value")) {
			statusFields.setActualLivingroomTemp(Integer.parseInt(message.toString()));
			statusFields.setNewLivingroomTempFlag(!statusFields.isNewLivingroomTempFlag());
		}

		if (topic.equals("home/outside/temperature/value")) {
			statusFields.setActualOutsideTemp(Integer.parseInt(message.toString()));
			statusFields.setNewOutsideTempFlag(!statusFields.isNewOutsideTempFlag());
		}

		if (topic.equals("home/livingroom/humidity/value")) {
			statusFields.setActualLivingroomHum(Integer.parseInt(message.toString()));
		}

		if (topic.equals("home/kitchen/humidity/value")) {
			statusFields.setActualKitchenHum(Integer.parseInt(message.toString()));
		}

		if (topic.equals("home/outside/humidity/value")) {
			statusFields.setActualOutsideHum(Integer.parseInt(message.toString()));
		}

		if (topic.equals("home/livingroom/app/check") || topic.equals("home/kitchen/app/check")
				|| topic.equals("home/outside/app/check")) {
			mqttClient.publishMessage("home/livingroom/app/status", "1");
			mqttClient.publishMessage("home/kitchen/app/status", "1");
		}

		if (topic.equals("home/livingroom/time/sync/req")) {
			String syncTime = Integer.toString(new McuTimeSync().getSyncTime());
			mqttClient.publishMessage("home/livingroom/time/sync/value", syncTime);
		}

		if (topic.equals("home/kitchen/time/sync/req")) {
			String syncTime = Integer.toString(new McuTimeSync().getSyncTime());
			mqttClient.publishMessage("home/kitchen/time/sync/value", syncTime);
		}

		if (topic.equals("home/livingroom/object") || topic.equals("home/kitchen/object")
				|| topic.equals("home/outside/object")) {
			statusHelper.tempHumMapper(topic, message.toString());
		}
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken token) {

	}
}
