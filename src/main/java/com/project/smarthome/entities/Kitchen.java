package com.project.smarthome.entities;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "kitchen")
public class Kitchen {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name="temperature")
	private int temperature;
	
	@Column(name="humidity")
	private int humidity;
	
	@Column(name="date_of_receiving")
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar dateOfReceiving;
	
	public Kitchen(int temperature, int humidity, Calendar dateOfReceiving) {
		this.temperature = temperature;
		this.humidity = humidity;
		this.dateOfReceiving = dateOfReceiving;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTemperature() {
		return temperature;
	}

	public void setTemperature(int temperature) {
		this.temperature = temperature;
	}

	public int getHumidity() {
		return humidity;
	}

	public void setHumidity(int humidity) {
		this.humidity = humidity;
	}

	public Calendar getDateOfReceiving() {
		return dateOfReceiving;
	}

	public void setDateOfReceiving(Calendar dateOfReceiving) {
		this.dateOfReceiving = dateOfReceiving;
	}
	
	
}
