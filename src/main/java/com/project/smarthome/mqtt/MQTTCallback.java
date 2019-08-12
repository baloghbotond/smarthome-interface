package com.project.smarthome.mqtt;

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

	private StatusFields statusFields;
	private MQTTClient mqttClient;
	private StatusHelper statusHelper;
	private McuTimeSync mcuTimeSync;

	@Autowired
	public MQTTCallback(StatusFields statusFields, MQTTClient mqttClient, StatusHelper statusHelper,
			McuTimeSync mcuTimeSync) {
		this.statusFields = statusFields;
		this.mqttClient = mqttClient;
		this.statusHelper = statusHelper;
		this.mcuTimeSync = mcuTimeSync;
	}

	@Override
	public void connectionLost(Throwable cause) {

	}

	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {

		if (topic.equals("home/kitchen/lights/status")) {
			if(message.toString().equals("0")) {
				statusFields.setKitchenLightStatus(0);
			}
			else if(message.toString().equals("1")) {
				statusFields.setKitchenLightStatus(1);
			}
		}

		if (topic.equals("home/livingroom/lights/status")) {
			if(message.toString().equals("0")) {
				statusFields.setLivingroomLightStatus(0);
			}
			else if(message.toString().equals("1")) {
				statusFields.setLivingroomLightStatus(1);
			}
		}

		if (topic.equals("home/outside/lights/status")) {
			if(message.toString().equals("0")) {
				statusFields.setOutsideLightStatus(0);
			}
			else if(message.toString().equals("1")) {
				statusFields.setOutsideLightStatus(1);
			}
		}

		if (topic.equals("home/kitchen/mcu/status")) {
			if(message.toString().equals("0")) {
				statusFields.setKitchenMcuStatus(0);
			}
			else if(message.toString().equals("1")) {
				statusFields.setKitchenMcuStatus(1);
			}
		}

		if (topic.equals("home/livingroom/mcu/status")) {
			if(message.toString().equals("0")) {
				statusFields.setLivingroomMcuStatus(0);
			}
			else if(message.toString().equals("1")) {
				statusFields.setLivingroomMcuStatus(1);
			}
		}

		if (topic.equals("home/outside/mcu/status")) {
			if(message.toString().equals("0")) {
				statusFields.setOutsideMcuStatus(0);
			}
			else if(message.toString().equals("1")) {
				statusFields.setOutsideMcuStatus(1);
			}
		}

		if (topic.equals("home/kitchen/temperature/value")) {
			statusFields.setActualKitchenTemp(Integer.parseInt(message.toString()));
		}

		if (topic.equals("home/livingroom/temperature/value")) {
			statusFields.setActualLivingroomTemp(Integer.parseInt(message.toString()));
		}

		if (topic.equals("home/outside/temperature/value")) {
			statusFields.setActualOutsideTemp(Integer.parseInt(message.toString()));
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

		if (topic.equals("home/java/app/check")) {
			mqttClient.publishMessage("home/java/app/status", "1");
		}

		if (topic.equals("home/livingroom/time/sync/req")) {
			String syncTime = Integer.toString(mcuTimeSync.getSyncTime());
			mqttClient.publishMessage("home/livingroom/time/sync/value", syncTime);
		}

		if (topic.equals("home/kitchen/time/sync/req")) {
			String syncTime = Integer.toString(mcuTimeSync.getSyncTime());
			mqttClient.publishMessage("home/kitchen/time/sync/value", syncTime);
		}

		if (topic.equals("home/outside/time/sync/req")) {
			String syncTime = Integer.toString(mcuTimeSync.getSyncTime());
			mqttClient.publishMessage("home/outside/time/sync/value", syncTime);
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
