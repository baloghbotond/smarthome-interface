package com.project.smarthome.helpers;

import java.util.Calendar;
import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.project.smarthome.entities.Kitchen;
import com.project.smarthome.entities.Livingroom;
import com.project.smarthome.entities.Outside;
import com.project.smarthome.services.KitchenService;
import com.project.smarthome.services.LivingroomService;
import com.project.smarthome.services.OutsideService;

@Component
public class StatusHelper {

	private KitchenService kitchenService;
	private LivingroomService livingroomService;
	private OutsideService outsideService;
	private StatusFields statusFields;
	
	@Autowired
	public StatusHelper(KitchenService kitchenService, LivingroomService livingroomService,
			OutsideService outsideService, StatusFields statusFields) {
		this.kitchenService = kitchenService;
		this.livingroomService = livingroomService;
		this.outsideService = outsideService;
		this.statusFields = statusFields;
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
	
	public String displayLivingroomRegulatorStatus() {
		
		if(statusFields.isRegulatorWorkingFlag() == false) {
			return "OFF";
		}
		else {
			return "ON";
		}
	}
	
	public int displayMcuTs(String room) {
		
		return statusFields.getSampleTime(room);
	}
	
	public int displayTemperature(String room) {
		
		return statusFields.getTemperature(room);
	}
	
	public int displayHumidity(String room) {
		
		return statusFields.getHumidity(room);
	}
	
	public void waitForLightStatus(String room) {
		
		int countTimeout = 0;
		int lastStatus = statusFields.getLightStatus(room);
		while(lastStatus == statusFields.getLightStatus(room) && countTimeout < 10000) {
			try {
				Thread.sleep(1);
				countTimeout++;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void waitForMcuStatus(String room) {
		
		int countTimeout = 0;
		int lastStatus = statusFields.getMcuStatus(room);
		while(lastStatus == statusFields.getMcuStatus(room)  && countTimeout < 10000) {
			try {
				Thread.sleep(1);
				countTimeout++;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void waitForTsValue(String room) {
		
		int countTimeout = 0;
		int lastValue = statusFields.getSampleTime(room);
		while(lastValue == statusFields.getSampleTime(room) && countTimeout < 10000) {
			try {
				Thread.sleep(1);
				countTimeout++;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void tempHumMapper(String topic, String message) {
		Calendar dateOfReceiving = Calendar.getInstance();
		
		StringTokenizer strTokForTheTopic = new StringTokenizer(topic, "/");
		strTokForTheTopic.nextToken();
		String room = strTokForTheTopic.nextToken();
		
		StringTokenizer strTokForTheMessage = new StringTokenizer(message, ";");
		int temperature = Integer.parseInt(strTokForTheMessage.nextToken());
		int humidity = Integer.parseInt(strTokForTheMessage.nextToken());
		
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
}
