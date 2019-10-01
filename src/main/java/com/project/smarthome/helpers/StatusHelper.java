package com.project.smarthome.helpers;

import java.util.Calendar;
import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.project.smarthome.entities.Kitchen;
import com.project.smarthome.entities.Livingroom;
import com.project.smarthome.entities.Outside;
import com.project.smarthome.security.RotEncryption;
import com.project.smarthome.services.KitchenService;
import com.project.smarthome.services.LivingroomService;
import com.project.smarthome.services.OutsideService;

@Component
public class StatusHelper {

	private KitchenService kitchenService;
	private LivingroomService livingroomService;
	private OutsideService outsideService;
	private StatusFields statusFields;
	private RotEncryption rotEncryption;
	
	@Autowired
	public StatusHelper(KitchenService kitchenService, LivingroomService livingroomService,
			OutsideService outsideService, StatusFields statusFields, RotEncryption rotEncryption) {
		this.kitchenService = kitchenService;
		this.livingroomService = livingroomService;
		this.outsideService = outsideService;
		this.statusFields = statusFields;
		this.rotEncryption = rotEncryption;
	}
	
	public int displayLightStatus(String room) {
	
		return statusFields.getLightStatus(room);
	}

	public String displayMcuStatus(String room) {
		
		if(statusFields.getMcuStatus(room) == 0) {
			return "OFF";
		}
		else {
			return "ON";
		}
	}
	
	public int displayRegulatorStatus() {
		
		if(statusFields.isRegulatorWorkingFlag() == false || statusFields.getLivingroomMcuStatus() == 0) {
			return 0;
		}
		else {
			return 1;
		}
	}
	
	public String displayRegulatorOptimum() {
		
		if(statusFields.getMcuStatus("livingroom") == 0) {
			return "-";
		} else {
			return Integer.toString(statusFields.getRegulatorOptimum());
		}
	}
	
	public String displayRegulatorRange() {
		
		if(statusFields.getMcuStatus("livingroom") == 0) {
			return "-";
		} else {
			return Integer.toString(statusFields.getRegulatorRange());
		}
	}
	
	public String displayTemperature(String room) {
		
		if(statusFields.getMcuStatus(room) == 0) {
			return "-";
		} else {
			return Integer.toString(statusFields.getTemperature(room));
		}
	}
	
	public String displayHumidity(String room) {
		
		if(statusFields.getMcuStatus(room) == 0) {
			return "-";
		} else {
			return Integer.toString(statusFields.getHumidity(room));
		}
	}
	
	public void waitForLightStatus(String room) {
		
		int countTimeout = 0;
		int lastStatus = statusFields.getLightStatus(room);
		while(lastStatus == statusFields.getLightStatus(room) && countTimeout < 10000) {
			waiting(1);
			countTimeout++;
		}
	}
	
	public void waitForMcuStatus(String room) {
		
		int countTimeout = 0;
		int lastStatus = statusFields.getMcuStatus(room);
		while(lastStatus == statusFields.getMcuStatus(room)  && countTimeout < 10000) {
			waiting(1);
			countTimeout++;
		}
	}
	
	public void waitForNewRegulatorValues() {
		
		int countTimeout = 0;
		boolean lastValue = statusFields.isNewRegulatorValues();
		while(lastValue == statusFields.isNewRegulatorValues() && countTimeout < 10000) {
			waiting(1);
			countTimeout++;
		}
	}
	
	public void tempHumMapper(String topic, String message) {
		Calendar dateOfReceiving = Calendar.getInstance();
		
		StringTokenizer strTokForTheTopic = new StringTokenizer(topic, "/");
		strTokForTheTopic.nextToken();
		String room = strTokForTheTopic.nextToken();
		
		StringTokenizer strTokForTheMessage = new StringTokenizer(message, ";");
		int temperature = Integer.parseInt(rotEncryption.decryption(strTokForTheMessage.nextToken()));
		int humidity = Integer.parseInt(rotEncryption.decryption(strTokForTheMessage.nextToken()));
		
		if(room.equals("kitchen")) {
			kitchenService.save(new Kitchen(temperature, humidity, dateOfReceiving));
			statusFields.setActualKitchenTemp(temperature);
			statusFields.setActualKitchenHum(humidity);
		}
		
		if(room.equals("livingroom")) {
			livingroomService.save(new Livingroom(temperature, humidity, dateOfReceiving));
			statusFields.setActualLivingroomTemp(temperature);
			statusFields.setActualLivingroomHum(humidity);
		}
		
		if(room.equals("outside")) {
			outsideService.save(new Outside(temperature, humidity, dateOfReceiving));
			statusFields.setActualOutsideTemp(temperature);
			statusFields.setActualOutsideHum(humidity);
		}
	}
	
	public void waiting(long millisec) {
		try {
			Thread.sleep(millisec);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public int decryptValue(String message) {
		
		return Integer.parseInt(rotEncryption.decryption(message));
	}
}
