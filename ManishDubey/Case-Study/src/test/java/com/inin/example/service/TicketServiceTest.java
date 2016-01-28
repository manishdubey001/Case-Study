package com.inin.example.service;

import com.inin.example.model.Ticket;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

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
    public void testCreateTicketWithNullTags()
    {
        TicketService ticketService = new TicketService();
        Ticket ticket = ticketService.create("Test Subject","Agent1",null);
        Assert.assertEquals("Test Subject",ticket.getSubject());
        Assert.assertEquals("Agent1",ticket.getAgentName());
        Assert.assertEquals(null,ticket.getTags());
        ticketService.delete(ticket.getId());
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
        ticketService.delete(ticket.getId());
    }


    @Test
    public void testUpdateTicketWithAgentAndNullTags()
    {
        TicketService ticketService = new TicketService();
        Ticket ticket = ticketService.create("Test Subject","Agent1",new HashSet<>(Arrays.asList("tag1","tag2","tag3")));
        Ticket ticket1 = ticketService.update(ticket.getId(),"Agent2",null);
        Assert.assertEquals("Agent2",ticket1.getAgentName());
        Assert.assertEquals(ticket.getTags(),ticket1.getTags());
        ticketService.delete(ticket.getId());
    }

    @Test
    public void testUpdateTicketWithTagsAndWithNullAgent()
    {
        TicketService ticketService = new TicketService();
        Ticket ticket = ticketService.create("Test Subject","Agent1",new HashSet<>(Arrays.asList("tag1","tag2","tag3")));
        HashSet<String > tags = new HashSet<>(Arrays.asList("tag4","tag5","tag6"));
        Ticket ticket1 = ticketService.update(ticket.getId(),null,tags);
        Assert.assertEquals(ticket.getAgentName(),ticket1.getAgentName());
        Assert.assertEquals(tags,ticket1.getTags());
        ticketService.delete(ticket.getId());
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
        ticketService.delete(ticket.getId());
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
        Assert.assertNull(ticketService.ticket(100000000));
        Ticket ticket1 = ticketService.ticket(ticket.getId());
        Assert.assertEquals("Test Subject",ticket1.getSubject());
        Assert.assertEquals("Agent1",ticket1.getAgentName());
        Assert.assertEquals(tags,ticket1.getTags());
        ticketService.delete(ticket.getId());
    }


    @Test
    public void testGetTicketsByAgentWithValidAgent()
    {
        TicketService ticketService = new TicketService();
        List<Ticket> dummyTicketTicketList = generateDummyTicket(ticketService);
        List<Ticket>  ticketList = ticketService.ticketsByAgent("Agent1");
        ticketList.forEach(ticket -> Assert.assertEquals("Agent1",ticket.getAgentName()));
        deleteDummyTicket(dummyTicketTicketList,ticketService);
    }

    @Test
    public void testGetTicketsByAgentWithInValidAgent()
    {
        TicketService ticketService = new TicketService();
        List<Ticket> dummyTicketTicketList = generateDummyTicket(ticketService);
        List<Ticket>  ticketList = ticketService.ticketsByAgent("12122112");
        Assert.assertEquals(0,ticketList.size());
        deleteDummyTicket(dummyTicketTicketList,ticketService);
    }

    @Test
    public void testGetTicketsByTagsWithValidTag()
    {
        TicketService ticketService = new TicketService();
        List<Ticket> dummyTicketTicketList = generateDummyTicket(ticketService);
        List<Ticket>  ticketList = ticketService.ticketsByAgent("tag1");
        ticketList.forEach(ticket -> Assert.assertTrue(ticket.getTags().contains("tag1")));
        deleteDummyTicket(dummyTicketTicketList,ticketService);
    }

    @Test
    public void testGetTicketsByTagsWithInValidTag()
    {
        TicketService ticketService = new TicketService();
        List<Ticket> dummyTicketTicketList = generateDummyTicket(ticketService);
        List<Ticket>  ticketList = ticketService.ticketsByAgent("1234567");
        Assert.assertEquals(0,ticketList.size());
        deleteDummyTicket(dummyTicketTicketList,ticketService);
    }

    @Test
    public void testTicketsGroupByAgent(){
        TicketService ticketService = new TicketService();
        Map<String, List<Ticket>> oldTicketsGroupByAgent = ticketService.ticketsGroupByAgent();
        List<Ticket> dummyTicketTicketList = generateDummyTicket(ticketService);
        Map<String, List<Ticket>> ticketsGroupByAgent = ticketService.ticketsGroupByAgent();
        if(oldTicketsGroupByAgent.containsKey("Agent1"))
            Assert.assertEquals(oldTicketsGroupByAgent.get("Agent1").size()+2,ticketsGroupByAgent.get("Agent1").size());
        else
            Assert.assertEquals(2,ticketsGroupByAgent.get("Agent1").size());

        if(oldTicketsGroupByAgent.containsKey("Agent2"))
            Assert.assertEquals(oldTicketsGroupByAgent.get("Agent2").size()+2,ticketsGroupByAgent.get("Agent2").size());
        else
            Assert.assertEquals(2,ticketsGroupByAgent.get("Agent2").size());
        deleteDummyTicket(dummyTicketTicketList,ticketService);
    }

    private static synchronized List<Ticket> generateDummyTicket(TicketService ticketService){
        List<Ticket> ticketList = new ArrayList<>();
        ticketList.add(ticketService.create("Test Subject1","Agent1",new HashSet<>(Arrays.asList("tag1","tag2","tag3"))));
        ticketList.add(ticketService.create("Test Subject2","Agent2",new HashSet<>(Arrays.asList("tag4","tag1","tag2"))));
        ticketList.add(ticketService.create("Test Subject3","Agent2",new HashSet<>(Arrays.asList("tag1","tag4","tag3"))));
        ticketList.add(ticketService.create("Test Subject4","Agent1",new HashSet<>(Arrays.asList("tag2","tag5"))));
        return ticketList;
    }

    private static synchronized void deleteDummyTicket(List<Ticket> ticketList,TicketService ticketService){
        ticketList.forEach(ticket -> ticketService.delete(ticket.getId()));

    }


}
