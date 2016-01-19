package com.Tickets.UnitTests;

import com.Tickets.Sout;
import com.Tickets.Ticket;
import com.Tickets.TicketServiceComponent;
import com.Tickets.TicketWareHouse;
import org.junit.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by root on 18/1/16.
*/
public class TicketsTestClass {
    TicketServiceComponent ticketServiceComponent = new TicketServiceComponent();
    public String subject;
    public String agent;
    public String tags;

    @Before
    public void TicketsTestClass(){
        this.subject = "Test Subject";
        this.agent = "Agent1";
        this.tags = "great,good,awesome";
    }

    @Test
    public void testcreateTicket(){
        boolean create = ticketServiceComponent.createTicket(subject,agent,tags);
        System.out.println("Checking Ticket create with dummy values");
        Assert.assertTrue(create);
    }

    @Test
    public void testIfSubjecNotPassed(){
        this.subject = "";
        boolean create = ticketServiceComponent.createTicket(subject,agent,tags);
        System.out.println("Checking Ticket creation on blank subject");
        Assert.assertFalse(create);
    }

    @Test
    public void testIfAgentNotPassed(){
        this.agent = "";
        boolean create = ticketServiceComponent.createTicket(subject,agent,tags);
        System.out.println("Checking Ticket creation on blank agent");
        Assert.assertFalse(create);
    }

    @After
    public void clearTicketData(){
        ticketServiceComponent.thm = null;
        ticketServiceComponent = null;
    }

}
