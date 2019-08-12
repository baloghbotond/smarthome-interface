package com.project.smarthome.helpers;

import java.util.Calendar;

import org.springframework.stereotype.Component;

@Component
public class McuTimeSync {

	private int minutePuffer;
	private int secondPuffer;
	
	public int getSyncTime() {
		
		Calendar calendar = Calendar.getInstance();
		
		int minute = calendar.get(Calendar.MINUTE);
		int second = calendar.get(Calendar.SECOND);
		
		for(int i = 0; i < 12; i++) {
			if(minute >= (5 * i) && minute < (5 * (i + 1))) {
				minutePuffer = (5 * (i + 1)) - minute - 1;
			}
		}
		secondPuffer = 60 - second;
		int syncTime = (minutePuffer * 60) + (secondPuffer);
		
		return syncTime;
	}
}
