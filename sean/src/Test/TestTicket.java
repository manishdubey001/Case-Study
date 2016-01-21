package Test;

import org.junit.*;
import java.util.HashMap;
import java.util.Set;
import Ticket.*;

public class TestTicket {
	private static TicketController tController = new TicketController();

	@Test
	public void testCreateTicket() {
		HashMap<String, String> data = new HashMap<String, String>();
		data.put("subject", "Test 1");
		data.put("agentName", "Sangam");
		data.put("tags", "foo,bar");
		Ticket ticket = tController.createTicket(data);
		Assert.assertEquals("Test 1", ticket.getSubject());
		Assert.assertEquals("Sangam", ticket.getAgentName());
		Assert.assertEquals(1, ticket.getId());
		Assert.assertNotNull(ticket.getCreated());
		Assert.assertNotNull(ticket.getModified());
		Assert.assertNotNull(ticket.getTags());

		Set<String> tags = ticket.getTags();
		int i = 0;
		for (String s : tags) {
			Logger.info(s);
			if (i == 0)
				Assert.assertEquals("bar", s);
			else
				Assert.assertEquals("foo", s);
			i++;
		}
		tController.deleteTicket(ticket.getId());
	}

	@Test
	public void testUpdateTicket() {
		HashMap<String, String> data = new HashMap<String, String>();
		data.put("subject", "Test 1");
		data.put("agentName", "Sangam");
		data.put("tags", "foo,bar");
		Ticket ticket = tController.createTicket(data);
		Assert.assertEquals("Test 1", ticket.getSubject());
		Assert.assertEquals("Sangam", ticket.getAgentName());
		Assert.assertEquals(2, ticket.getId());
		Assert.assertNotNull(ticket.getCreated());
		Assert.assertNotNull(ticket.getModified());
		Assert.assertNotNull(ticket.getTags());

		Set<String> tags = ticket.getTags();
		int i = 0;
		for (String s : tags) {
			Logger.info(s);
			if (i == 0)
				Assert.assertEquals("bar", s);
			else
				Assert.assertEquals("foo", s);
			i++;
		}

		HashMap<String, String> data1 = new HashMap<String, String>();
		data1.put("subject", "Test 2");
		data1.put("agentName", "Pradeep");
		data1.put("tags", "max,min");
		Ticket ticket1 = tController.updateTicket(data1, ticket.getId());
		Assert.assertEquals("Test 2", ticket1.getSubject());
		Assert.assertEquals("Pradeep", ticket1.getAgentName());
		Logger.info(" " + ticket1.getId());
		Assert.assertEquals(2, ticket1.getId());
		Assert.assertNotNull(ticket1.getCreated());
		Assert.assertNotNull(ticket1.getModified());
		Assert.assertNotNull(ticket1.getTags());

		Set<String> tags1 = ticket1.getTags();
		int j = 0;
		for (String s : tags1) {
			Logger.info(s);
			if (j == 0)
				Assert.assertEquals("min", s);
			else
				Assert.assertEquals("max", s);
			j++;
		}
		tController.deleteTicket(ticket1.getId());
	}
	
	@Test
	public void testDeleteTicket() {
		HashMap<String, String> data = new HashMap<String, String>();
		data.put("subject", "Test 1");
		data.put("agentName", "Sangam");
		data.put("tags", "foo,bar");
		Ticket ticket = tController.createTicket(data);
		tController.deleteTicket(ticket.getId());
		Assert.assertEquals(0, tController.getTickets().size());
	}
	
	@Test
	public void testGetTicket() {
		HashMap<String, String> data = new HashMap<String, String>();
		data.put("subject", "Test 1");
		data.put("agentName", "Sangam");
		data.put("tags", "foo,bar");
		Ticket ticket = tController.createTicket(data);
		
		Ticket detailTicket = tController.getTicket(ticket.getId());
		Assert.assertEquals("Test 1", detailTicket.getSubject());
		Assert.assertEquals("Sangam", detailTicket.getAgentName());
		Assert.assertEquals(4, detailTicket.getId());
		Assert.assertNotNull(detailTicket.getCreated());
		Assert.assertNotNull(detailTicket.getModified());
		Assert.assertNotNull(detailTicket.getTags());

		Set<String> tags = detailTicket.getTags();
		int i = 0;
		for (String s : tags) {
			Logger.info(s);
			if (i == 0)
				Assert.assertEquals("bar", s);
			else
				Assert.assertEquals("foo", s);
			i++;
		}
		tController.deleteTicket(detailTicket.getId());
	}
	

}