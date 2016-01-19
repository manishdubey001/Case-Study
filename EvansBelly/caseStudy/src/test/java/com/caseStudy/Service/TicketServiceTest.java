package test.java.com.caseStudy.Service;

import com.caseStudy.Model.Ticket;
import com.caseStudy.Service.TicketService;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by root on 19/1/16.
 */
public class TicketServiceTest {
	TicketService ticketService = new TicketService();
	Set tags = new HashSet(Arrays.asList("a", "b"));
//	Ticket ticket = ticketService.getTicketData(1, "Complain", "Evans", tags);

	@Test
	public void testGetTicketData() throws Exception {

//		assertEquals(1, ticket.getId());
//		assertEquals("Complain", ticket.getSubject());
//		assertEquals("Evans", ticket.getAgent());
//		assertEquals(tags, ticket.getTags());
	}

	@Test
	public void testUpdateAgent() throws Exception {

		Ticket ticket = new Ticket(2, "Test", "Leroy", tags);
		ticketService.updateAgent(ticket, "Mangesh");
		assertEquals("Mangesh", ticket.getAgent());
	}

	@Test
	public void testUpdatetags() throws Exception {

	}

	@Test
	public void testGetTagsInfo() throws Exception {

	}
}