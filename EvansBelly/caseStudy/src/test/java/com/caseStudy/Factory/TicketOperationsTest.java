package test.java.com.caseStudy.Factory;

import com.caseStudy.Factory.TicketOperations;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

/**
 * Created by root on 19/1/16.
 */
public class TicketOperationsTest {

	int id = 10;
	String subject = "Complain";
	String agent = "Evans";
	Set tags = new HashSet<>(Arrays.asList("info", "latest", "edited"));
	TicketOperations ticketOperations = new TicketOperations();

	@Test
	public void testCreateTicketIdZero() throws Exception {

		System.out.println("Id is zero");
		int ValidId = ticketOperations.setTicketData(0, this.subject, this.agent, this.tags);
		assertEquals(0, ValidId);
	}

	@Test
	public void testCreateTicketBlankSubject() throws Exception
	{
		String subject = "";
		int ValidId = ticketOperations.setTicketData(this.id, subject, this.agent, this.tags);
		assertEquals(0, ValidId);
	}

	@Test
	public void testCreateTicketBlankAgent() throws Exception
	{
		String agent = "";
		int ValidId = ticketOperations.setTicketData(this.id, subject, agent, this.tags);
		assertEquals(0, ValidId);
	}

	@Test
	public void testCreateTicketBlankTags() throws Exception
	{
		Set tags = new HashSet();
		int ValidId = ticketOperations.setTicketData(this.id, subject, this.agent, tags);
		assertEquals(0, ValidId);
	}

	@Test
	public void testCreateTicketValidData() throws Exception
	{
		int ValidId = ticketOperations.setTicketData(this.id, this.subject, this.agent, this.tags);
		assertEquals(10, ValidId);
	}
}