package com.inin.example.service;

import com.inin.example.model.Ticket;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * Created by root on 18/1/16.
 */
public class TicketServiceTest {

    @Test
    public void testCreateTicketWithNullSubject(){
        TicketService ticketService = new TicketService();
        Ticket ticket = ticketService.create(null,"Agent1",new HashSet<>());
        Assert.assertNull(ticket);
    }

    @Test
    public void testCreateTicketWithNullAgent(){
        TicketService ticketService = new TicketService();
        Ticket ticket = ticketService.create("Test Subject",null,new HashSet<>());
        Assert.assertNull(ticket);
    }
    @Test
    public void testCreateTicketWithEmptySubject(){
        TicketService ticketService = new TicketService();
        Ticket ticket = ticketService.create("","Agent1",new HashSet<>());
        Assert.assertNull(ticket);
    }

    @Test
    public void testCreateTicketWithEmptyAgent(){
        TicketService ticketService = new TicketService();
        Ticket ticket = ticketService.create("Test Subject","",new HashSet<>());
        Assert.assertNull(ticket);
    }
    @Test
    public void testCreateTicket()
    {
        TicketService ticketService = new TicketService();
        HashSet<String> tags = new HashSet<>(Arrays.asList("tag1","tag2","tag3"));
        Ticket ticket = ticketService.create("Test Subject","Agent1",tags);
        Assert.assertEquals("Test Subject",ticket.getSubject());
        Assert.assertEquals("Agent1",ticket.getAgentName());
        Assert.assertEquals(tags,ticket.getTags());
    }

    @Test
    public void testUpdateTicketWithAgentAndTags()
    {
        TicketService ticketService = new TicketService();
        Ticket ticket = ticketService.create("Test Subject","Agent1",new HashSet<>(Arrays.asList("tag1","tag2","tag3")));
        HashSet<String > tags = new HashSet<>(Arrays.asList("tag4","tag5","tag6"));
        Ticket ticket1 = ticketService.update(ticket.getId(),"Agent2",tags);
        Assert.assertEquals("Agent2",ticket1.getAgentName());
        Assert.assertEquals(tags,ticket1.getTags());
    }

    @Test
    public void testTicketDelete()
    {
        TicketService ticketService = new TicketService();
        Ticket ticket = ticketService.create("Test Subject","Agent1",new HashSet<>(Arrays.asList("tag1","tag2","tag3")));
        Assert.assertTrue(ticketService.delete(ticket.getId()));
        Assert.assertFalse(ticketService.delete(ticket.getId()));
    }

    @Test
    public void testGetTicket()
    {
        TicketService ticketService = new TicketService();
        HashSet<String> tags = new HashSet<>(Arrays.asList("tag1","tag2","tag3"));
        Ticket ticket = ticketService.create("Test Subject","Agent1",tags);
        Assert.assertNull(ticketService.getTicket(45));
        Ticket ticket1 = ticketService.getTicket(ticket.getId());
        Assert.assertEquals("Test Subject",ticket1.getSubject());
        Assert.assertEquals("Agent1",ticket1.getAgentName());
        Assert.assertEquals(tags,ticket1.getTags());
    }


    @Test
    public void testGetTicketsByAgentWithValidAgent()
    {
        TicketService ticketService = new TicketService();
        ticketService.loadDummyTickets(1000);
        List<Ticket>  ticketList = ticketService.getTicketsByAgent("Agent12");
        ticketList.forEach(ticket -> Assert.assertEquals("Agent12",ticket.getAgentName()));
    }

    @Test
    public void testGetTicketsByAgentWithInValidAgent()
    {
        TicketService ticketService = new TicketService();
        ticketService.loadDummyTickets(1000);
        List<Ticket>  ticketList = ticketService.getTicketsByAgent("12122112");
        Assert.assertEquals(0,ticketList.size());
    }

    @Test
    public void testGetTicketsByTagsWithValidTag()
    {
        TicketService ticketService = new TicketService();
        ticketService.loadDummyTickets(1000);
        List<Ticket>  ticketList = ticketService.getTicketsByAgent("tag1");
        ticketList.forEach(ticket -> Assert.assertTrue(ticket.getTags().contains("tag1")));
    }

    @Test
    public void testGetTicketsByTagsWithInValidTag()
    {
        TicketService ticketService = new TicketService();
        ticketService.loadDummyTickets(1000);
        List<Ticket>  ticketList = ticketService.getTicketsByAgent("1234567");
        Assert.assertEquals(0,ticketList.size());
    }


}
