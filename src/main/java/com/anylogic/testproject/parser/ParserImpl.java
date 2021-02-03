package com.anylogic.testproject.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.anylogic.testproject.entity.Ticket;
import com.anylogic.testproject.entity.TicketsList;
import com.anylogic.testproject.exeption.JsonTicketException;
import com.google.gson.Gson;

public class ParserImpl implements Parser {

	public static final ParserImpl PARSER = new ParserImpl();
	private static Logger logger = LogManager.getLogger(ParserImpl.class);

	private ParserImpl() {
	}

	public String getJsonAsString(String filePath) throws JsonTicketException   {
		StringBuilder stringBuilder = new StringBuilder();
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filePath);
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"))){
			String line;
			while ((line = reader.readLine()) != null) {
				stringBuilder.append(line);
			}
		} catch (IOException e) {
			logger.error("File \"tickets.json\" reading error!");
			throw new JsonTicketException(e);
		}
		return stringBuilder.toString();
	}

	public List<Ticket> parseTickets(String json) throws JsonTicketException {
		try {
			Gson gson = new Gson();
			TicketsList list = gson.fromJson(json, TicketsList.class);
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy H:mm");
			return list.tickets.stream()
					.map(p -> new Ticket(p.getOrigin(), p.getOrigin_name(), p.getDestination(), p.getDestination_name(),
							LocalDateTime.parse(p.getDeparture_date() + " " + p.getDeparture_time(), formatter),
							LocalDateTime.parse(p.getArrival_date() + " " + p.getArrival_time(), formatter),
							p.getCarrier(), p.getStops(), p.getPrice()))
					.collect(Collectors.toList());
		} catch (Exception e) {
			logger.error("JSON parsing error!");
			throw new JsonTicketException(e);
		}
	}

}
