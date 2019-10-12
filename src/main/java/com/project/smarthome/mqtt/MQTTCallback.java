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

		String messageString = message.toString();
		
		if (topic.equals("home/kitchen/lights/status")) {
			if(messageString.equals("0")) {
				statusFields.setKitchenLightStatus(0);
			}
			else if(messageString.equals("1")) {
				statusFields.setKitchenLightStatus(1);
			}
		}

		else if (topic.equals("home/livingroom/lights/status")) {
			if(messageString.equals("0")) {
				statusFields.setLivingroomLightStatus(0);
			}
			else if(messageString.equals("1")) {
				statusFields.setLivingroomLightStatus(1);
			}
		}

		else if (topic.equals("home/outside/lights/status")) {
			if(messageString.equals("0")) {
				statusFields.setOutsideLightStatus(0);
			}
			else if(messageString.equals("1")) {
				statusFields.setOutsideLightStatus(1);
			}
		}

		else if (topic.equals("home/kitchen/mcu/status")) {
			if(messageString.equals("0")) {
				statusFields.setKitchenMcuStatus(0);
			}
			else if(messageString.equals("1")) {
				statusFields.setKitchenMcuStatus(1);
			}
		}

		else if (topic.equals("home/livingroom/mcu/status")) {
			if(messageString.equals("0")) {
				statusFields.setLivingroomMcuStatus(0);
			}
			else if(messageString.equals("1")) {
				statusFields.setLivingroomMcuStatus(1);
			}
		}

		else if (topic.equals("home/outside/mcu/status")) {
			if(messageString.equals("0")) {
				statusFields.setOutsideMcuStatus(0);
			}
			else if(messageString.equals("1")) {
				statusFields.setOutsideMcuStatus(1);
			}
		}

		else if (topic.equals("home/kitchen/temperature/value")) {
			statusFields.setActualKitchenTemp(statusHelper.decryptValue(messageString));
		}

		else if (topic.equals("home/livingroom/temperature/value")) {
			statusFields.setActualLivingroomTemp(statusHelper.decryptValue(messageString));
		}

		else if (topic.equals("home/outside/temperature/value")) {
			statusFields.setActualOutsideTemp(statusHelper.decryptValue(messageString));
		}

		else if (topic.equals("home/livingroom/humidity/value")) {
			statusFields.setActualLivingroomHum(statusHelper.decryptValue(messageString));
		}

		else if (topic.equals("home/kitchen/humidity/value")) {
			statusFields.setActualKitchenHum(statusHelper.decryptValue(messageString));
		}

		else if (topic.equals("home/outside/humidity/value")) {
			statusFields.setActualOutsideHum(statusHelper.decryptValue(messageString));
		}

		else if (topic.equals("home/java/app/check")) {
			mqttClient.publishMessage("home/java/app/status", "1");
		}

		else if (topic.equals("home/livingroom/time/sync/req")) {
			String syncTime = Integer.toString(mcuTimeSync.getSyncTime());
			mqttClient.publishMessageWithQos("home/livingroom/time/sync/value", syncTime, 2);
		}

		else if (topic.equals("home/kitchen/time/sync/req")) {
			String syncTime = Integer.toString(mcuTimeSync.getSyncTime());
			mqttClient.publishMessageWithQos("home/kitchen/time/sync/value", syncTime, 2);
		}

		else if (topic.equals("home/outside/time/sync/req")) {
			String syncTime = Integer.toString(mcuTimeSync.getSyncTime());
			mqttClient.publishMessageWithQos("home/outside/time/sync/value", syncTime, 2);
		}

		else if (topic.equals("home/livingroom/object") || topic.equals("home/kitchen/object")
				|| topic.equals("home/outside/object")) {
			statusHelper.tempHumMapper(topic, messageString);
		}
		
		else if(topic.equals("home/livingroom/regulator/status")) {
			if(messageString.equals("0")) {
				statusFields.setRegulatorWorkingFlag(false);
			}
			else if(messageString.equals("1")) {
				statusFields.setRegulatorWorkingFlag(true);
			}
		}
		
		else if(topic.equals("home/livingroom/regulator/optimum/value")) {
			statusFields.setRegulatorOptimum(statusHelper.decryptValue(messageString));
		}
		
		else if(topic.equals("home/livingroom/regulator/range/value")) {
			statusFields.setRegulatorRange(statusHelper.decryptValue(messageString));
			statusFields.setNewRegulatorValues(!statusFields.isNewRegulatorValues());
		}
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken token) {

	}
}
