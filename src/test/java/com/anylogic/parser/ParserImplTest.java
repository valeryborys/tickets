package com.anylogic.parser;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.anylogic.testproject.Main;
import com.anylogic.testproject.entity.Ticket;
import com.anylogic.testproject.exeption.JsonTicketException;
import com.anylogic.testproject.parser.Parser;
import com.anylogic.testproject.parser.ParserImpl;

public class ParserImplTest {
	public static final String JSON = "﻿{  \"tickets\": [{    \"origin\": \"VVO\",    \"origin_name\": \"Владивосток\",    \"destination\": \"TLV\",    \"destination_name\": \"Тель-Авив\",    \"departure_date\": \"12.05.18\",    \"departure_time\": \"17:00\",    \"arrival_date\": \"12.05.18\",    \"arrival_time\": \"23:30\",    \"carrier\": \"TK\",    \"stops\": 2,    \"price\": 11000  }, {    \"origin\": \"VVO\",    \"origin_name\": \"Владивосток\",    \"destination\": \"TLV\",    \"destination_name\": \"Тель-Авив\",    \"departure_date\": \"12.05.18\",    \"departure_time\": \"17:10\",    \"arrival_date\": \"12.05.18\",    \"arrival_time\": \"23:45\",    \"carrier\": \"TK\",    \"stops\": 1,    \"price\": 13600  }]}";
	public static Parser parser = ParserImpl.PARSER;
	private static final String PATH = "tickets.json";


	@Test
	public void getJsonAsStringTest() {
		String jsonAsString = null;
		try {
			jsonAsString = parser.getJsonAsString(PATH);
		} catch (JsonTicketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(JSON, jsonAsString);
	}

	@Test
	public void parseTicketsTest() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy H:mm");
		LocalDateTime date1 = LocalDateTime.parse("12.05.18 17:00", formatter);
		LocalDateTime date2 = LocalDateTime.parse("12.05.18 23:30", formatter);
		LocalDateTime date3 = LocalDateTime.parse("12.05.18 17:10", formatter);
		LocalDateTime date4 = LocalDateTime.parse("12.05.18 23:45", formatter);
		Ticket ticket1 = new Ticket("VVO", "Владивосток", "TLV", "Тель-Авив", date1, date2, "TK", 2,
				new BigDecimal(11000));
		Ticket ticket2 = new Ticket("VVO", "Владивосток", "TLV", "Тель-Авив", date3, date4, "TK", 1,
				new BigDecimal(13600));
		List<Ticket> list = new ArrayList<>();
		list.add(ticket1);
		list.add(ticket2);
		List<Ticket> parseTickets = null;
		String json = null;
		try {
			json = parser.getJsonAsString(PATH);
			parseTickets = parser.parseTickets(json);
		} catch (JsonTicketException e) {
			e.printStackTrace();
		}
		assertEquals(list, parseTickets);
	}

	@Test(expected = JsonTicketException.class)
	public void parseTicketsExceptionTest() throws JsonTicketException {
		String path2 = Main.getDirectory(Main.class) + "test" + File.separator + "resources" + File.separator
				+ "ticketsErr.json";
		String jsonAsString = parser.getJsonAsString("ticketsErr.json");
		parser.parseTickets(jsonAsString);
	}

}
