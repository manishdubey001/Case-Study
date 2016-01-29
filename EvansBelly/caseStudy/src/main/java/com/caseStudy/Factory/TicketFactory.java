package com.caseStudy.Factory;

import com.caseStudy.Model.Ticket;

import java.util.Set;

/**
 * Created by root on 19/1/16.
 */
public class TicketFactory {

	public static Ticket ticketInstance(int id, String subject, String agent, Set tags){
		return new Ticket(id,subject, agent, tags);
	}
}
