package com.anylogic.testproject.parser;

import java.util.List;

import com.anylogic.testproject.entity.Ticket;
import com.anylogic.testproject.exeption.JsonTicketException;

public interface Parser {
	
	String getJsonAsString(String filePath) throws JsonTicketException;

	List<Ticket> parseTickets(String json) throws JsonTicketException;
}
