package com.anylogic.testproject;

import java.io.File;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.anylogic.testproject.entity.Ticket;
import com.anylogic.testproject.exeption.JsonTicketException;
import com.anylogic.testproject.handler.Handler;
import com.anylogic.testproject.handler.HandlerImpl;
import com.anylogic.testproject.parser.Parser;
import com.anylogic.testproject.parser.ParserImpl;

public class Main {
	private static Logger logger = LogManager.getLogger(Main.class);
private static final String FILE_NAME = "tickets.json";
	public static void main(String[] args) {
		Parser parser = ParserImpl.PARSER;
		Handler handler = HandlerImpl.HANDLER;
		try {
			String json = parser.getJsonAsString(FILE_NAME);
			List<Ticket> tickets = parser.parseTickets(json);
			if (tickets != null) {
				for (Ticket ticket : tickets) {
					System.out.println(ticket);
				}
				System.out.printf("Average flight time from Vladivostok to Tel Aviv is %s\n",
						handler.getAverageTime(tickets));
				Double percentil = handler.getPercentil(tickets, 0.9);
				System.out.printf("90-s percentil of of flight time is %.1f\n", percentil);
			} else {
				System.out.println("Not wellformed JSON file! Please, check JSON format and try again.");
			}
		} catch (JsonTicketException e) {
			logger.error("Not wellformed JSON file! Please, check JSON format and try again.");
		}
	}

	public static String getDirectory(Class<?> cl) {
		return System.getProperty("user.dir") + File.separator + "src" + File.separator;

	}

}
