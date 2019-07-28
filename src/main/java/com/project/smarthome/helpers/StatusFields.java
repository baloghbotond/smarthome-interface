package com.project.smarthome.helpers;

import org.springframework.stereotype.Component;

@Component
public class StatusFields {

	private int livingroomLightStatus;
	private int kitchenLightStatus;
	private int outsideLightStatus;

	private int livingroomMcuStatus;
	private int kitchenMcuStatus;
	private int outsideMcuStatus;
	
	private int livingroomTs;
	private int kitchenTs;
	private int outsideTs;
	
	private int actualLivingroomTemp;
	private int actualKitchenTemp;
	private int actualOutsideTemp;
	
	private int actualLivingroomHum;
	private int actualKitchenHum;
	private int actualOutsideHum;
	
	public int getActualLivingroomHum() {
		return actualLivingroomHum;
	}
	public void setActualLivingroomHum(int actualLivingroomHum) {
		this.actualLivingroomHum = actualLivingroomHum;
	}
	public int getActualKitchenHum() {
		return actualKitchenHum;
	}
	public void setActualKitchenHum(int actualKitchenHum) {
		this.actualKitchenHum = actualKitchenHum;
	}
	public int getActualOutsideHum() {
		return actualOutsideHum;
	}
	public void setActualOutsideHum(int actualOutsideHum) {
		this.actualOutsideHum = actualOutsideHum;
	}

	private boolean newLivingroomTempFlag;
	private boolean newKitchenTempFlag;
	private boolean newOutsideTempFlag;
	
	public boolean isNewLivingroomTempFlag() {
		return newLivingroomTempFlag;
	}
	public void setNewLivingroomTempFlag(boolean newLivingroomTempFlag) {
		this.newLivingroomTempFlag = newLivingroomTempFlag;
	}
	public boolean isNewKitchenTempFlag() {
		return newKitchenTempFlag;
	}
	public void setNewKitchenTempFlag(boolean newKitchenTempFlag) {
		this.newKitchenTempFlag = newKitchenTempFlag;
	}
	public boolean isNewOutsideTempFlag() {
		return newOutsideTempFlag;
	}
	public void setNewOutsideTempFlag(boolean newOutsideTempFlag) {
		this.newOutsideTempFlag = newOutsideTempFlag;
	}
	public int getActualLivingroomTemp() {
		return actualLivingroomTemp;
	}
	public void setActualLivingroomTemp(int actualLivingroomTemp) {
		this.actualLivingroomTemp = actualLivingroomTemp;
	}
	public int getActualKitchenTemp() {
		return actualKitchenTemp;
	}
	public void setActualKitchenTemp(int actualKitchenTemp) {
		this.actualKitchenTemp = actualKitchenTemp;
	}
	public int getActualOutsideTemp() {
		return actualOutsideTemp;
	}
	public void setActualOutsideTemp(int actualOutsideTemp) {
		this.actualOutsideTemp = actualOutsideTemp;
	}
	public int getLivingroomLightStatus() {
		return livingroomLightStatus;
	}
	public void setLivingroomLightStatus(int livingroomLightStatus) {
		this.livingroomLightStatus = livingroomLightStatus;
	}
	public int getKitchenLightStatus() {
		return kitchenLightStatus;
	}
	public void setKitchenLightStatus(int kitchenLightStatus) {
		this.kitchenLightStatus = kitchenLightStatus;
	}
	public int getOutsideLightStatus() {
		return outsideLightStatus;
	}
	public void setOutsideLightStatus(int outsideLightStatus) {
		this.outsideLightStatus = outsideLightStatus;
	}
	public int getLivingroomMcuStatus() {
		return livingroomMcuStatus;
	}
	public void setLivingroomMcuStatus(int livingroomMcuStatus) {
		this.livingroomMcuStatus = livingroomMcuStatus;
	}
	public int getKitchenMcuStatus() {
		return kitchenMcuStatus;
	}
	public void setKitchenMcuStatus(int kitchenMcuStatus) {
		this.kitchenMcuStatus = kitchenMcuStatus;
	}
	public int getOutsideMcuStatus() {
		return outsideMcuStatus;
	}
	public void setOutsideMcuStatus(int outsideMcuStatus) {
		this.outsideMcuStatus = outsideMcuStatus;
	}
	public int getLivingroomTs() {
		return livingroomTs;
	}
	public void setLivingroomTs(int livingroomTs) {
		this.livingroomTs = livingroomTs;
	}
	public int getKitchenTs() {
		return kitchenTs;
	}
	public void setKitchenTs(int kitchenTs) {
		this.kitchenTs = kitchenTs;
	}
	public int getOutsideTs() {
		return outsideTs;
	}
	public void setOutsideTs(int outsideTs) {
		this.outsideTs = outsideTs;
	}
	
	public int getLightStatus(String room) {
		
		if(room.equals("livingroom")) {
			return livingroomLightStatus;
		}
		
		else if(room.equals("kitchen")) {
			return kitchenLightStatus;
		}
		
		else {
			return outsideLightStatus;
		}
	}
	
	public int getMcuStatus(String mcu) {
		
		if(mcu.equals("livingroom")) {
			return livingroomMcuStatus;
		}
		
		else if(mcu.equals("kitchen")) {
			return kitchenMcuStatus;
		}
		
		else {
			return outsideMcuStatus;
		}
	}
	
	public int getSampleTime(String mcu) {
		
		if(mcu.equals("livingroom")) {
			return livingroomTs;
		}
		
		else if(mcu.equals("kitchen")) {
			return kitchenTs;
		}
		
		else {
			return outsideTs;
		}
	}
	
	public boolean getTemperatureFlag(String room) {
		
		if(room.equals("livingroom")) {
			return newLivingroomTempFlag;
		}
		
		else if(room.equals("kitchen")) {
			return newKitchenTempFlag;
		}
		
		else {
			return newOutsideTempFlag;
		}
	}
	
	public int getTemperature(String room) {
		
		if(room.equals("livingroom")) {
			return actualLivingroomTemp;
		}
		
		else if(room.equals("kitchen")) {
			return actualKitchenTemp;
		}
		
		else {
			return actualOutsideTemp;
		}
	}
	
	public int getHumidity(String room) {
		
		if(room.equals("livingroom")) {
			return actualLivingroomHum;
		}
		
		else if(room.equals("kitchen")) {
			return actualKitchenHum;
		}
		
		else {
			return actualOutsideHum;
		}
	}
	
}
