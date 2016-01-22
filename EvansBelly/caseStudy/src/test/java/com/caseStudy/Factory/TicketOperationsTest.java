package test.java.com.caseStudy.Factory;

import com.caseStudy.Factory.TicketOperations;
import com.caseStudy.Model.Ticket;
import com.caseStudy.Service.TicketService;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by root on 19/1/16.
 */
public class TicketOperationsTest {

	int id = 10;
	String subject = "Complain";
	String agent = "Evans";
	Set tags = new HashSet<>(Arrays.asList("info", "latest", "edited"));
	TicketOperations ticketOperations = new TicketOperations();
	TicketService ticketService = new TicketService();
	HashMap<Integer, Ticket> ticketDetails = new HashMap();

	@Test
	public void testCreateTicketIdZero() throws Exception {

		int ValidId = ticketOperations.setTicketData(0, this.subject, this.agent, this.tags);
		assertEquals(0, ValidId);
	}

	@Test
	public void testCreateTicketBlankSubject() throws Exception {
		String subject = "";
		int ValidId = ticketOperations.setTicketData(this.id, subject, this.agent, this.tags);
		assertEquals(0, ValidId);
	}

	@Test
	public void testCreateTicketBlankAgent() throws Exception {
		String agent = "";
		int ValidId = ticketOperations.setTicketData(this.id, subject, agent, this.tags);
		assertEquals(0, ValidId);
	}

	@Test
	public void testCreateTicketBlankTags() throws Exception {
		Set tags = new HashSet();
		int ValidId = ticketOperations.setTicketData(this.id, subject, this.agent, tags);
		assertEquals(0, ValidId);
	}

	@Test
	public void testCreateTicketValidData() throws Exception {
		int validId = ticketOperations.setTicketData(this.id, this.subject, this.agent, this.tags);
		assertEquals(10, validId);
		Ticket ticket = ticketOperations.ticketDetails.get(validId);
		assertEquals("Complain", ticket.getSubject());
		assertEquals("Evans", ticket.getAgent());
		assertEquals(tags, ticket.getTags());
	}

	@Test
	public void updateAgentBlankAndNewValue() throws Exception {

		int validId = ticketOperations.setTicketData(this.id, this.subject, this.agent, this.tags);
		assertEquals(10, validId);
		Ticket ticket = ticketOperations.ticketDetails.get(validId);

		//Blank agent
		ticketService.updateAgent(ticket, "");
		assertEquals("Evans", ticket.getAgent());

		// Agent Leroy
		ticketService.updateAgent(ticket, "Leroy");
		assertEquals("Leroy", ticket.getAgent());
	}

	@Test
	public void updateTagsBlankValue() throws Exception {

		int validId = ticketOperations.setTicketData(this.id, this.subject, this.agent, this.tags);
		assertEquals(10, validId);
		Ticket ticket = ticketOperations.ticketDetails.get(validId);

		// Blank tags
		ticketService.updateTags(ticket, "");
		assertEquals(tags, ticket.getTags());

	}

	@Test
	public void updateTagsNewValue() throws Exception {

		int validId = ticketOperations.setTicketData(this.id, this.subject, this.agent, this.tags);
		assertEquals(10, validId);
		Ticket ticket = ticketOperations.ticketDetails.get(validId);

		// New tags update
		ticketService.updateTags(ticket, "Tag1, Tag2, Tag3");
		assertEquals("[Tag1, Tag2, Tag3]", ticket.getTags().toString());

	}

	@Test
	public void updateTagsNewDuplicateValue() throws Exception {

		int validId = ticketOperations.setTicketData(this.id, this.subject, this.agent, this.tags);
		assertEquals(10, validId);
		Ticket ticket = ticketOperations.ticketDetails.get(validId);

		// New duplicate tags update
		ticketService.updateTags(ticket, "Tag1, Tag2, Tag2");
		assertEquals("[Tag1, Tag2]", ticket.getTags().toString());

	}

	@Test
	public void updateTagsNewUnorderedValue() throws Exception {

		int validId = ticketOperations.setTicketData(this.id, this.subject, this.agent, this.tags);
		assertEquals(10, validId);
		Ticket ticket = ticketOperations.ticketDetails.get(validId);

		// New tags update (Unordered input)
		ticketService.updateTags(ticket, "Tag1, Tag3, Tag2");
		assertEquals("[Tag1, Tag2, Tag3]", ticket.getTags().toString());
	}

	@Test
	public void deleteTicketWrongId() throws Exception {

		int validId = ticketOperations.setTicketData(this.id, this.subject, this.agent, this.tags);
		assertEquals(10, validId);

		// Incorrect Id = 1;
		assertFalse(ticketService.deleteTicket(ticketOperations.ticketDetails, 1));

	}

	@Test
	public void deleteTicketCorrectId() throws Exception {

		int validId = ticketOperations.setTicketData(this.id, this.subject, this.agent, this.tags);
		assertEquals(10, validId);

		// Correct Id = 10;
		assertTrue(ticketService.deleteTicket(ticketOperations.ticketDetails, validId));
	}

	@Test
	public void showTicketIncorrectId() throws Exception {

		int validId = ticketOperations.setTicketData(this.id, this.subject, this.agent, this.tags);
		assertEquals(10, validId);

		Ticket ticket = ticketService.showTicket(ticketOperations.ticketDetails, 1);
		assertEquals(null, ticket);
	}

	@Test
	public void showTicketCorrectId() throws Exception {

		int validId = ticketOperations.setTicketData(this.id, this.subject, this.agent, this.tags);
		assertEquals(10, validId);

		Ticket ticket = ticketService.showTicket(ticketOperations.ticketDetails, validId);
		assertEquals(agent, ticket.getAgent());
		assertEquals(subject, ticket.getSubject());
		assertEquals(tags, ticket.getTags());
	}

	@Test
	public void ticketsAssignedToEachAgent() throws Exception {

		int id1 = ticketOperations.setTicketData(this.id, this.subject, this.agent, this.tags);
		int id2 = ticketOperations.setTicketData(1, "Hello", this.agent, this.tags);

		Ticket ticket1 = ticketService.showTicket(ticketOperations.ticketDetails, id1);
		Ticket ticket2 = ticketService.showTicket(ticketOperations.ticketDetails, id2);

		ticketDetails.put(id1, ticket1);
		ticketDetails.put(id2, ticket2);

		List<Ticket> ticketObjs = ticketService.agentSearchTicket(this.agent, ticketDetails);

		assertEquals(id1, ticketObjs.get(1).getId());
		assertEquals(this.agent, ticketObjs.get(1).getAgent());
		assertEquals(this.subject, ticketObjs.get(1).getSubject());

		assertEquals(id2, ticketObjs.get(0).getId());
		assertEquals(this.agent, ticketObjs.get(0).getAgent());
		assertEquals("Hello", ticketObjs.get(0).getSubject());
	}

	@Test
	public void agentWiseTicketCount() throws Exception {

		int id1 = ticketOperations.setTicketData(this.id, this.subject, this.agent, this.tags);
		int id2 = ticketOperations.setTicketData(1, "Hello", this.agent, this.tags);
		int id3 = ticketOperations.setTicketData(2, "Hello", "Lee", this.tags);

		Ticket ticket1 = ticketService.showTicket(ticketOperations.ticketDetails, id1);
		Ticket ticket2 = ticketService.showTicket(ticketOperations.ticketDetails, id2);
		Ticket ticket3 = ticketService.showTicket(ticketOperations.ticketDetails, id3);

		ticketDetails.put(id1, ticket1);
		ticketDetails.put(id2, ticket2);
		ticketDetails.put(id3, ticket3);

		Map agentTicketCount = ticketService.agentTicketCount(ticketDetails);

		assertEquals(2, agentTicketCount.get(this.agent));
		assertEquals(1, agentTicketCount.get("Lee"));
	}

}