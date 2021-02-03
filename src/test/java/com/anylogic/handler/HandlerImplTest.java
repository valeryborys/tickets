package com.anylogic.handler;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.anylogic.testproject.Main;
import com.anylogic.testproject.entity.Ticket;
import com.anylogic.testproject.exeption.JsonTicketException;
import com.anylogic.testproject.handler.Handler;
import com.anylogic.testproject.handler.HandlerImpl;
import com.anylogic.testproject.parser.Parser;
import com.anylogic.testproject.parser.ParserImpl;

public class HandlerImplTest {
	private static Parser parser = ParserImpl.PARSER;
	private static Handler handler = HandlerImpl.HANDLER;
	private static List<Ticket> tickets;

	@BeforeClass
	public static void init() {
		String path = "tickets.json";
		String jsonAsString = null;
		try {
			jsonAsString = parser.getJsonAsString(path);
			tickets = parser.parseTickets(jsonAsString);
		} catch (JsonTicketException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void getAverageTimeTest() {
		String averageTime = handler.getAverageTime(tickets);
		assertEquals("6 hours 32 minutes", averageTime);
	}

	@Test
	public void getPercentilTest() {
		Double percentil = handler.getPercentil(tickets, 0.5);
		assertEquals(Double.valueOf(392.5), percentil, 0.01);
	}

}
