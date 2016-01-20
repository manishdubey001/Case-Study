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
        assertTrue(ticketService.createTicketService(id, subject, agent, categories));
        Ticket ticket = ticketService.showSingleTicketService(id);
        Assert.assertEquals("James", ticket.getAgentName());
        Assert.assertEquals(1, ticket.getId());
        Assert.assertEquals("Subject", ticket.getSubject());
        Assert.assertEquals(asList("one", "two", "three"), ticket.getTags());
    }

    @Test
    public void testCreateTicketServicewithLessthenZeroId() throws Exception {

        int id = -1;
        String subject = "Subject";
        String agent = "James";
        List<String> categories = asList("one", "two", "three");
        assertFalse(ticketService.createTicketService(id, subject, agent, categories));
    }

    @Test
    public void testCreateTicketServicewithSubjectNull() throws Exception {

        int id = 1;
        String subject = "";
        String agent = "James";
        List<String> categories = asList("one", "two", "three");
        assertFalse(ticketService.createTicketService(id, subject, agent, categories));
    }

    @Test
    public void testCreateTicketServicewithAllNullValue() throws Exception {

        int id = 0;
        String subject = "";
        String agent = "";
        List<String> categories = asList("one", "two", "three");
        assertFalse(ticketService.createTicketService(id, subject, agent, categories));
    }

    @Test
    public void testIsTicketExistService() throws Exception {

        int id = 1;
        String subject = "Subject";
        String agent = "James";
        List<String> categories = asList("one", "two", "three");
        assertTrue(ticketService.createTicketService(id, subject, agent, categories));

        id = 1;
        assertTrue(ticketService.isTicketIdExit(id));
    }

    @Test
    public void testIsTicketNotExistService() throws Exception {

        int id = 1;
        String subject = "Subject";
        String agent = "James";
        List<String> categories = asList("one", "two", "three");
        assertTrue(ticketService.createTicketService(id, subject, agent, categories));

        id = 2;
        assertFalse(ticketService.isTicketIdExit(id));
    }


    @Test
    public void testUpdateTicketAgentNameService() throws Exception {

        int id = 1;
        String subject = "Subject";
        String agent = "James";
        List<String> categories = asList("one", "two", "three");
        assertTrue(ticketService.createTicketService(id, subject, agent, categories));

        String newagent = "Yogesh";
        assertTrue(ticketService.updateAgentName(id, newagent));
        Ticket ticket = ticketService.showSingleTicketService(id);
        Assert.assertEquals("Yogesh", ticket.getAgentName());

    }

    @Test
    public void testUpdateTicketAgentNameServicewithIdBlackAgentname() throws Exception {

        int id = 1;
        String subject = "Subject";
        String agent = "James";
        List<String> categories = asList("one", "two", "three");
        ticketService.createTicketService(id, subject, agent, categories);
        id = 1;
        String newAgent = "";
        assertFalse(ticketService.updateAgentName(id, newAgent));
    }


    @Test
    public void testUpdateTicketTagsService() throws Exception {

        int id = 1;
        String subject = "Subject";
        String agent = "James";
        List<String> categories = asList("one", "two", "three");
        assertTrue(ticketService.createTicketService(id, subject, agent, categories));

        List<String> newtags = asList();
        assertTrue(ticketService.updateTags(id, newtags));
        Ticket ticket = ticketService.showSingleTicketService(id);
        Assert.assertEquals(asList(), ticket.getTags());
    }

    @Test
    public void testRemoveTicketService() throws Exception {

        int id = 1;
        String subject = "Subject";
        String agent = "James";
        List<String> categories = asList("one", "two", "three");
        assertTrue(ticketService.createTicketService(id, subject, agent, categories));

        assertTrue(ticketService.removeTicketService(id));
        assertFalse(ticketService.isTicketIdExit(id));

    }

    @Test
    public void testRemoveTicketServicewithWrongId() throws Exception {

        int id = 1;
        String subject = "Subject";
        String agent = "James";
        List<String> categories = asList("one", "two", "three");
        assertTrue(ticketService.createTicketService(id, subject, agent, categories));

        id = 2;
        assertFalse(ticketService.removeTicketService(id));
    }


    @Test
    public void testSingleTicketDetailsService() throws Exception {

        int id = 1;
        String subject = "Subject";
        String agent = "James";
        List<String> categories = asList("one", "two", "three");
        assertTrue(ticketService.createTicketService(id, subject, agent, categories));

        id = 1;
        Ticket ticket = (ticketService.showSingleTicketService(id));
        Assert.assertEquals("James", ticket.getAgentName());
        Assert.assertEquals(1, ticket.getId());
        Assert.assertEquals("Subject", ticket.getSubject());
        Assert.assertEquals(asList("one", "two", "three"), ticket.getTags());
    }

    @Test
    public void testSingleTicketDetailsServicewithWrongId() throws Exception {

        int id = 1;
        String subject = "Subject";
        String agent = "James";
        List<String> categories = asList("one", "two", "three");
        assertTrue(ticketService.createTicketService(id, subject, agent, categories));

        id = 2;
        Ticket ticket = (ticketService.showSingleTicketService(id));
        Assert.assertEquals(null, ticket);

    }

    @Test
    public void testShowAllTicketDetailsService() throws Exception {

        int id = 1;
        String subject = "Subject";
        String agent = "James";
        List<String> categories = asList("one", "two", "three");
        assertTrue(ticketService.createTicketService(id, subject, agent, categories));

        id = 2;
        subject = "Subject2";
        agent = "James2";
        categories = asList("one", "two", "three");
        assertTrue(ticketService.createTicketService(id, subject, agent, categories));


        List<Ticket> list = (ticketService.showAllTicketService());

        Ticket ticket = list.get(0);
        Assert.assertEquals("James", ticket.getAgentName());
        Assert.assertEquals(1, ticket.getId());
        Assert.assertEquals("Subject", ticket.getSubject());
        Assert.assertEquals(asList("one", "two", "three"), ticket.getTags());

        Ticket ticket1 = list.get(1);
        Assert.assertEquals("James2", ticket1.getAgentName());
        Assert.assertEquals(2, ticket1.getId());
        Assert.assertEquals("Subject2", ticket1.getSubject());
        Assert.assertEquals(asList("one", "two", "three"), ticket1.getTags());
        System.out.println(list);

    }

}