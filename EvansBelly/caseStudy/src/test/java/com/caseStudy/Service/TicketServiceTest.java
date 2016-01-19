package com.caseStudy.Service;

import com.caseStudy.Model.Ticket;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by root on 19/1/16.
 */
public class TicketServiceTest {
	TicketService ticketService = new TicketService();

	@Test
	public Ticket testGetTicketData() throws Exception {

		Set tags = new HashSet(Arrays.asList("a", "b"));
		Ticket ticket = ticketService.getTicketData(1, "Complain", "Evans", tags);
		assertEquals(1, ticket.getId());
		assertEquals("Complain", ticket.getSubject());
		assertEquals("Evans", ticket.getAgent());
		assertEquals(tags, ticket.getTags());
		return ticket;
	}

	@Test
	public void testUpdateAgent() throws Exception {

		Ticket ticket = testGetTicketData();
		ticketService.updateAgent(ticket,"Leroy");
	}

	@Test
	public void testUpdatetags() throws Exception {

	}

	@Test
	public void testGetTagsInfo() throws Exception {

	}
}