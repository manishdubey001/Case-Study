package com.yogesh.Test;

import com.yogesh.model.Ticket;
import com.yogesh.service.TicketService;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.*;

/**
 * Created by root on 18/1/16.
 */
public class TicketServiceTest {

    TicketService ticketService = new TicketService();

    @Test
    public void testCreateTicketService() throws Exception {
        int id = 1;
        String subject = "Subject";
        String agent = "James";
        List<String> categories = asList("one", "two", "three");
        assertTrue( ticketService.createTicketService(id, subject, agent, categories ));
        Ticket ticket = ticketService.showSingleTicketService(id);
        Assert.assertEquals("James",ticket.getAgentName());
        Assert.assertEquals(1,ticket.getId());
        Assert.assertEquals("Subject",ticket.getSubject());
        Assert.assertEquals(asList("one", "two", "three"),ticket.getTags());
        System.out.println("Mangesh");
    }

    @Test
    public void testCreateTicketServicewithLessthenZeroId() throws Exception {

        int id = -1;
        String subject = "Subject";
        String agent = "James";
        List<String> categories = asList("one", "two", "three");
        assertFalse( ticketService.createTicketService(id, subject, agent, categories ));
    }
}