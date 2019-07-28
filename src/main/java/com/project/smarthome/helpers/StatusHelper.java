package com.project.smarthome.helpers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StatusHelper {

	private static StatusFields statusFields;
	
	@Autowired
	public StatusHelper(StatusFields statusFields) {
		this.statusFields = statusFields;
	}
	
	public static int displayLightStatus(String room) {
	
		return statusFields.getLightStatus(room);
	}
	
	public static String displayMcuStatus(String room) {
		
		if(statusFields.getMcuStatus(room) == 0) {
			return "OFF";
		}
		else {
			return "ON";
		}
	}
	
	public static int displayMcuTs(String room) {
		
		return statusFields.getSampleTime(room);
	}
	
	public static int displayTemperature(String room) {
		
		return statusFields.getTemperature(room);
	}
	
	public static int displayHumidity(String room) {
		
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
	
	public void waitForNewTemperature(String room) {
		
		int countTimeout = 0;
		boolean lastStatus = statusFields.getTemperatureFlag(room);
		while(lastStatus == statusFields.getTemperatureFlag(room) && countTimeout < 10000) {
			try {
				Thread.sleep(1);
				countTimeout++;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
