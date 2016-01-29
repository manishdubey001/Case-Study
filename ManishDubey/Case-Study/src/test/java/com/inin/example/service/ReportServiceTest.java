package com.inin.example.service;

import com.inin.example.model.Ticket;
import com.inin.example.util.TicketSerializationUtil;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.*;

/**
 * Created by root on 27/1/16.
 */
public class ReportServiceTest {

    @Test
    public void testTotalTicketCount()
    {
        TicketReportService ticketReportService = new TicketReportService();
        int oldTicketCount = ticketReportService.totalTicketCount();
        TicketService ticketService = new TicketService();
        List<Ticket> dummyTicketTicketList = generateDummyTicket(ticketService);
        Assert.assertEquals(oldTicketCount+4,ticketReportService.totalTicketCount());
        deleteDummyTicket(dummyTicketTicketList,ticketService);
    }

    @Test
    public void testOldestTicket()
    {
        TicketReportService ticketReportService = new TicketReportService();
        TicketService ticketService = new TicketService();
        List<Ticket> dummyTicketTicketList = generateDummyTicket(ticketService);
        Ticket oldest = ticketReportService.oldestTicket();
        List<Ticket> ticketList = ticketService.tickets();
        ticketList.forEach(ticket -> Assert.assertTrue(oldest.getCreated().compareTo(ticket.getCreated()) <= 0 ));
        deleteDummyTicket(dummyTicketTicketList,ticketService);
    }

    @Test
    public void testTicketOlderByDate()
    {
        TicketReportService ticketReportService = new TicketReportService();
        TicketService ticketService = new TicketService();
        List<Ticket> dummyTicketTicketList = generateDummyTicket(ticketService);
        LocalDateTime date = LocalDateTime.now();
        List<Ticket> ticketList = ticketReportService.ticketOlderByDate(date);
        ticketList.forEach(ticket -> Assert.assertTrue(ticket.getCreated().compareTo(date) <= 0));
        deleteDummyTicket(dummyTicketTicketList,ticketService);
    }

    @Test
    public void testTicketsGroupByTag()
    {
        TicketReportService ticketReportService = new TicketReportService();
        TicketService ticketService = new TicketService();
        Map<String,List<Ticket>> oldTicketGroupByTag = ticketReportService.ticketsGroupByTag();
        List<Ticket> dummyTicketTicketList = generateDummyTicket(ticketService);
        Map<String,List<Ticket>> ticketGroupByTag = ticketReportService.ticketsGroupByTag();
        if(oldTicketGroupByTag.containsKey("tag1"))
            Assert.assertEquals(oldTicketGroupByTag.get("tag1").size()+3,ticketGroupByTag.get("tag1").size());
        else
            Assert.assertEquals(3,ticketGroupByTag.get("tag1").size());

        if(oldTicketGroupByTag.containsKey("tag2"))
            Assert.assertEquals(oldTicketGroupByTag.get("tag2").size()+3,ticketGroupByTag.get("tag2").size());
        else
            Assert.assertEquals(3,ticketGroupByTag.get("tag2").size());

        if(oldTicketGroupByTag.containsKey("tag3"))
            Assert.assertEquals(oldTicketGroupByTag.get("tag3").size()+2,ticketGroupByTag.get("tag3").size());
        else
            Assert.assertEquals(2,ticketGroupByTag.get("tag3").size());

        if(oldTicketGroupByTag.containsKey("tag4"))
            Assert.assertEquals(oldTicketGroupByTag.get("tag4").size()+2,ticketGroupByTag.get("tag4").size());
        else
            Assert.assertEquals(2,ticketGroupByTag.get("tag4").size());

        if(oldTicketGroupByTag.containsKey("tag5"))
            Assert.assertEquals(oldTicketGroupByTag.get("tag5").size()+1,ticketGroupByTag.get("tag5").size());
        else
            Assert.assertEquals(1,ticketGroupByTag.get("tag5").size());
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
