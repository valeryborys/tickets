package com.anylogic.testproject.handler;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;
import java.util.TreeMap;
import java.util.stream.Collectors;

import com.anylogic.testproject.entity.Ticket;

public class HandlerImpl implements Handler {

	public static final HandlerImpl HANDLER = new HandlerImpl();

	private HandlerImpl() {
	}

	public String getAverageTime(List<Ticket> tickets) {
		OptionalDouble avg = tickets.stream()
				.mapToLong(ticket -> Duration.between(ticket.getDeparture(), ticket.getArrival()).toMinutes())
				.average();
		int avenger = (int) avg.getAsDouble();
		int hours = avenger / 60;
		int minutes = avenger % 60;
		return hours + " hours " + minutes + " minutes";
	}

	public Double getPercentil(List<Ticket> tickets, Double percentil) {
		Map<Double, Integer> map = new TreeMap<>();
		List<Long> avg = tickets.stream()
				.map(ticket -> Long.valueOf(Duration.between(ticket.getDeparture(), ticket.getArrival()).toMinutes()))
				.sorted().collect(Collectors.toList());
		for (int i = 1; i <= avg.size(); i++) {
			Double fx = ((i - 1) / (avg.size() - 1.0));
			map.put(fx, i);
		}
		int interpolateLowIndex = getLowIndex(map, percentil) - 1;
		int interpolateHighIndex = getHighIndex(map, percentil) - 1;
		double highFx = ((interpolateHighIndex) / (avg.size() - 1.0));
		double lowFx = ((interpolateLowIndex) / (avg.size() - 1.0));
		percentil = avg.get(interpolateLowIndex) + (avg.get(interpolateHighIndex) - avg.get(interpolateLowIndex))
				/ (highFx - lowFx) * (percentil - lowFx);
		return percentil;
	}

	private static Integer getLowIndex(Map<Double, Integer> map, Double percentil) {
		Double tempKey = Double.MAX_VALUE;
		Double tempSub = Double.MAX_VALUE;
		for (Map.Entry<Double, Integer> entry : map.entrySet()) {
			if (Math.abs((entry.getKey() - percentil)) < tempSub && (entry.getKey() - percentil) <= 0) {
				tempSub = Math.abs(entry.getKey() - percentil);
				tempKey = entry.getKey();
			}
		}
		return map.get(tempKey);
	}

	private static Integer getHighIndex(Map<Double, Integer> map, Double percentil) {
		Double tempKey = Double.MAX_VALUE;
		Double tempSub = Double.MAX_VALUE;
		for (Map.Entry<Double, Integer> entry : map.entrySet()) {
			if (Math.abs((entry.getKey() - percentil)) < tempSub && (entry.getKey() - percentil) >= 0) {
				tempSub = Math.abs(entry.getKey() - percentil);
				tempKey = entry.getKey();
			}
		}
		return map.get(tempKey);
	}

}
