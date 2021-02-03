package com.anylogic.testproject.handler;

import java.util.List;

import com.anylogic.testproject.entity.Ticket;

public interface Handler {
	
	String getAverageTime(List<Ticket> tickets);

	Double getPercentil(List<Ticket> tickets, Double percentil);

}
