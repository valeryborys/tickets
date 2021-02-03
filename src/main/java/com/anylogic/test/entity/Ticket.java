package com.anylogic.test.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Ticket implements Serializable {

	private static final long serialVersionUID = -26114258891901030L;
	
	private String origin;
	private String origin_name;
	private String destination;
	private String destination_name;
	private LocalDateTime departure;
	private LocalDateTime arrival;
	private String carrier;
	private int stops;
	private BigDecimal price;
	
	
	public Ticket(String origin, String origin_name, String destination, String destination_name,
			LocalDateTime departure, LocalDateTime arrival, String carrier, int stops, BigDecimal price) {
		super();
		this.origin = origin;
		this.origin_name = origin_name;
		this.destination = destination;
		this.destination_name = destination_name;
		this.departure = departure;
		this.arrival = arrival;
		this.carrier = carrier;
		this.stops = stops;
		this.price = price;
	}

	public Ticket() {
	}
	
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	public String getOrigin_name() {
		return origin_name;
	}
	public void setOrigin_name(String origin_name) {
		this.origin_name = origin_name;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public String getDestination_name() {
		return destination_name;
	}
	public void setDestination_name(String destination_name) {
		this.destination_name = destination_name;
	}
	public LocalDateTime getDeparture() {
		return departure;
	}
	public void setDeparture(LocalDateTime departure) {
		this.departure = departure;
	}
	public LocalDateTime getArrival() {
		return arrival;
	}
	public void setArrival(LocalDateTime arrival) {
		this.arrival = arrival;
	}
	public String getCarrier() {
		return carrier;
	}
	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}
	public int getStops() {
		return stops;
	}
	public void setStops(int stops) {
		this.stops = stops;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((arrival == null) ? 0 : arrival.hashCode());
		result = prime * result + ((carrier == null) ? 0 : carrier.hashCode());
		result = prime * result + ((departure == null) ? 0 : departure.hashCode());
		result = prime * result + ((destination == null) ? 0 : destination.hashCode());
		result = prime * result + ((destination_name == null) ? 0 : destination_name.hashCode());
		result = prime * result + ((origin == null) ? 0 : origin.hashCode());
		result = prime * result + ((origin_name == null) ? 0 : origin_name.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		result = prime * result + stops;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ticket other = (Ticket) obj;
		if (arrival == null) {
			if (other.arrival != null)
				return false;
		} else if (!arrival.equals(other.arrival))
			return false;
		if (carrier == null) {
			if (other.carrier != null)
				return false;
		} else if (!carrier.equals(other.carrier))
			return false;
		if (departure == null) {
			if (other.departure != null)
				return false;
		} else if (!departure.equals(other.departure))
			return false;
		if (destination == null) {
			if (other.destination != null)
				return false;
		} else if (!destination.equals(other.destination))
			return false;
		if (destination_name == null) {
			if (other.destination_name != null)
				return false;
		} else if (!destination_name.equals(other.destination_name))
			return false;
		if (origin == null) {
			if (other.origin != null)
				return false;
		} else if (!origin.equals(other.origin))
			return false;
		if (origin_name == null) {
			if (other.origin_name != null)
				return false;
		} else if (!origin_name.equals(other.origin_name))
			return false;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		if (stops != other.stops)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Ticket [origin=" + origin + ", origin_name=" + origin_name + ", destination=" + destination
				+ ", destination_name=" + destination_name + ", departure=" + departure + ", arrival=" + arrival
				+ ", carrier=" + carrier + ", stops=" + stops + ", price=" + price + "]";
	}
	
	
	
}
