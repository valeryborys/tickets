package com.anylogic.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.stream.Collectors;

import com.anylogic.test.entity.Ticket;
import com.anylogic.test.entity.TicketsList;
import com.google.gson.Gson;

public class Handler {

	public static void main(String[] args) {
		String filePath = dir(Handler.class) + "tickets.json";
		StringBuilder stringBuilder = new StringBuilder();
		FileReader fr = null;
		Scanner scanner = null;
		try {
			fr = new FileReader(filePath);
			scanner = new Scanner(fr);
			while (scanner.hasNext()) {
				stringBuilder.append(scanner.next());
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (scanner != null) {
				scanner.close();
			}
			if (fr != null) {
				try {
					fr.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		List<Ticket> tickets = parseTickets(stringBuilder.toString());
		System.out.printf("Average flight time from Vladivostok to Tel Aviv is %s\n", getAverageTime(tickets));
		Double percentil = getPercentil(tickets, 0.9);
		System.out.println("90-s percentil of of flight time is " + percentil);
	}

	public static String dir(Class<?> cl) {
		String mainPath = System.getProperty("user.dir") + File.separator;
		String localPath = "src" + File.separator + "main" + File.separator + "resources" + File.separator;
		return mainPath + localPath;
	}

	public static List<Ticket> parseTickets(String json) {
		Gson gson = new Gson();
		TicketsList list = gson.fromJson(json, TicketsList.class);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy H:mm");
		return list.tickets.stream()
				.map(p -> new Ticket(p.getOrigin(), p.getOrigin_name(), p.getDestination(), p.getDestination_name(),
						LocalDateTime.parse(p.getDeparture_date() + " " + p.getDeparture_time(), formatter),
						LocalDateTime.parse(p.getArrival_date() + " " + p.getArrival_time(), formatter), p.getCarrier(),
						p.getStops(), p.getPrice()))
				.collect(Collectors.toList());
	}

	public static String getAverageTime(List<Ticket> tickets) {
		OptionalDouble avg = tickets.stream()
				.mapToLong(ticket -> Duration.between(ticket.getDeparture(), ticket.getArrival()).toMinutes())
				.average();
		int avenger = (int) avg.getAsDouble();
		int hours = avenger / 60;
		int minutes = avenger % 60;
		return hours + " hours " + minutes + " minutes";
	}

	public static Double getPercentil(List<Ticket> tickets, Double percentil) {
		Map<Double, Integer> map = new TreeMap<>();
		List<Long> avg = tickets.stream()
				.map(ticket -> Long.valueOf(Duration.between(ticket.getDeparture(), ticket.getArrival()).toMinutes()))
				.sorted().collect(Collectors.toList());
		for (int i = 1; i <= avg.size(); i++) {
			Double fx = ((i - 1) / (avg.size() - 1.0));
			map.put(fx, i);
		}
		int interpolateLowIndex = getLowIndex(map, percentil)-1;
		int interpolateHighIndex = getHighIndex(map, percentil)-1;
		double highFx = ((interpolateHighIndex) / (avg.size() - 1.0));
		double lowFx = ((interpolateLowIndex) / (avg.size() - 1.0));
		percentil = avg.get(interpolateLowIndex)+(avg.get(interpolateHighIndex)-avg.get(interpolateLowIndex))/(highFx-lowFx)*(percentil-lowFx);
		return percentil;
	}

	public static Integer getLowIndex(Map<Double, Integer> map, Double percentil) {
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

	public static Integer getHighIndex(Map<Double, Integer> map, Double percentil) {
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
