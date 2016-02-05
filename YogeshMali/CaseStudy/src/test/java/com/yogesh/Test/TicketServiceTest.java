package com.yogesh.Test;

import com.yogesh.exception.TicketNotFountException;
import com.yogesh.model.Ticket;
import com.yogesh.service.TicketService;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
        Set<String>  categories = new HashSet<String>(Arrays.asList("tag1", "tag2"));
        assertTrue(ticketService.createTicketService(id, subject, agent, categories));
        Ticket ticket = ticketService.showSingleTicketService(id);
        Assert.assertEquals("James", ticket.getAgentName());
        Assert.assertEquals(1, ticket.getId());
        Assert.assertEquals("Subject", ticket.getSubject());
        Assert.assertEquals(new HashSet<String>(Arrays.asList("tag1", "tag2")), ticket.getTags());
    }

    @Test
    public void testCreateTicketServicewithLessthenZeroId() throws Exception {

        int id = -1;
        String subject = "Subject";
        String agent = "James";
        Set<String>  categories = new HashSet<String>(Arrays.asList("tag1", "tag2"));
        assertFalse(ticketService.createTicketService(id, subject, agent, categories));
    }

    @Test
    public void testCreateTicketServicewithSubjectNull() throws Exception {

        int id = 1;
        String subject = "";
        String agent = "James";
        Set<String>  categories = new HashSet<String>(Arrays.asList("tag1", "tag2"));
        assertFalse(ticketService.createTicketService(id, subject, agent, categories));
    }

    @Test
    public void testCreateTicketServicewithAllNullValue() throws Exception {

        int id = 0;
        String subject = "";
        String agent = "";
        Set<String>  categories = new HashSet<String>(Arrays.asList("tag1", "tag2"));
        assertFalse(ticketService.createTicketService(id, subject, agent, categories));
    }

    @Test
    public void testIsTicketExistService() throws Exception {

        int id = 1;
        String subject = "Subject";
        String agent = "James";
        Set<String>  categories = new HashSet<String>(Arrays.asList("tag1", "tag2"));
        assertTrue(ticketService.createTicketService(id, subject, agent, categories));

        id = 1;
        assertTrue(ticketService.isTicketIdExist(id));
    }

    @Test
    public void testIsTicketNotExistService() throws Exception {

        int id = 1;
        String subject = "Subject";
        String agent = "James";
        Set<String>  categories = new HashSet<String>(Arrays.asList("tag1", "tag2"));
        assertTrue(ticketService.createTicketService(id, subject, agent, categories));

        id = 2;
        assertFalse(ticketService.isTicketIdExist(id));
    }


    @Test
    public void testUpdateTicketAgentNameService() throws Exception {

        int id = 1;
        String subject = "Subject";
        String agent = "James";
        Set<String>  categories = new HashSet<String>(Arrays.asList("tag1", "tag2"));
        assertTrue(ticketService.createTicketService(id, subject, agent, categories));

        String newagent = "Yogesh";
        assertTrue(ticketService.updateAgentName(id, newagent));
        Ticket ticket = ticketService.showSingleTicketService(id);
        Assert.assertEquals("Yogesh", ticket.getAgentName());

    }

    @Test
    public void testUpdateTicketAgentNameServicewithIdBlankAgentname() throws Exception {

        int id = 1;
        String subject = "Subject";
        String agent = "James";
        Set<String>  categories = new HashSet<String>(Arrays.asList("tag1", "tag2"));
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
        Set<String>  categories = new HashSet<String>(Arrays.asList("tag1", "tag2"));
        assertTrue(ticketService.createTicketService(id, subject, agent, categories));

        Set<String>  newtags = new HashSet<String>(Arrays.asList("tag3"));
        assertTrue(ticketService.updateTags(id, newtags));
        Ticket ticket = ticketService.showSingleTicketService(id);
        Assert.assertEquals(new HashSet<String>(Arrays.asList("tag3")), ticket.getTags());
    }

    @Test
    public void testRemoveTicketService() throws Exception {

        int id = 1;
        String subject = "Subject";
        String agent = "James";
        Set<String>  categories = new HashSet<String>(Arrays.asList("tag1", "tag2"));
        assertTrue(ticketService.createTicketService(id, subject, agent, categories));

        assertTrue(ticketService.removeTicketService(id));
        assertFalse(ticketService.isTicketIdExist(id));

    }

    @Test
    public void testRemoveTicketServicewithWrongId() throws Exception {

        int id = 1;
        String subject = "Subject";
        String agent = "James";
        Set<String>  categories = new HashSet<String>(Arrays.asList("tag1", "tag2"));
        assertTrue(ticketService.createTicketService(id, subject, agent, categories));

        id = 2;
        assertFalse(ticketService.removeTicketService(id));
    }


    @Test
    public void testSingleTicketDetailsService() throws Exception {

        int id = 1;
        String subject = "Subject";
        String agent = "James";
        Set<String>  categories = new HashSet<String>(Arrays.asList("tag1", "tag2"));
        assertTrue(ticketService.createTicketService(id, subject, agent, categories));

        id = 1;
        Ticket ticket = (ticketService.showSingleTicketService(id));
        Assert.assertEquals("James", ticket.getAgentName());
        Assert.assertEquals(1, ticket.getId());
        Assert.assertEquals("Subject", ticket.getSubject());
        Assert.assertEquals(new HashSet<String>(Arrays.asList("tag1", "tag2")), ticket.getTags());
    }

    @Test(expected=TicketNotFountException.class)
    public void testSingleTicketDetailsServicewithWrongId() throws Exception {

        int id = 1;
        String subject = "Subject";
        String agent = "James";
        Set<String>  categories = new HashSet<String>(Arrays.asList("tag1", "tag2"));
        assertTrue(ticketService.createTicketService(id, subject, agent, categories));

        id = 2;
        Ticket ticket = (ticketService.showSingleTicketService(id));



    }

    @Test
    public void testShowAllTicketDetailsService() throws Exception {

        int id = 1;
        String subject = "Subject";
        String agent = "James";
        Set<String>  categories = new HashSet<String>(Arrays.asList("tag1", "tag2"));
        assertTrue(ticketService.createTicketService(id, subject, agent, categories));

        id = 2;
        subject = "Subject2";
        agent = "James2";
        categories = new HashSet<String>(Arrays.asList("tag1", "tag2"));
        assertTrue(ticketService.createTicketService(id, subject, agent, categories));


        List<Ticket> list = (ticketService.showAllTicketService());

        list.stream().forEach(ticket -> {
            if(ticket.getId() == 1) {
                Assert.assertEquals("James", ticket.getAgentName());
                Assert.assertEquals(1, ticket.getId());
                Assert.assertEquals("Subject", ticket.getSubject());
                Assert.assertEquals(new HashSet<String>(Arrays.asList("tag1", "tag2")), ticket.getTags());
            }
            else  if(ticket.getId() == 2)
            {
                Assert.assertEquals("James2", ticket.getAgentName());
                Assert.assertEquals(2, ticket.getId());
                Assert.assertEquals("Subject2", ticket.getSubject());
                Assert.assertEquals(new HashSet<String>(Arrays.asList("tag1", "tag2")), ticket.getTags());
            }
        });


    }

    @Test
    public void testSearchTicketsUsingAgentnameService() throws Exception {

        int id = 1;
        String subject = "Subject";
        String agent = "James";
        Set<String>  categories = new HashSet<String>(Arrays.asList("tag1", "tag2"));
        assertTrue(ticketService.createTicketService(id, subject, agent, categories));

        String agentName = "James";
        List<Ticket> list = (ticketService.searchTicketsUsingAgentnameService(agentName));
        Ticket ticket = list.get(0);
        Assert.assertEquals("James", ticket.getAgentName());
        Assert.assertEquals(1, ticket.getId());
        Assert.assertEquals("Subject", ticket.getSubject());
        Assert.assertEquals(new HashSet<String>(Arrays.asList("tag1", "tag2")), ticket.getTags());

    }


    @Test
    public void testSearchTicketsUsingAgentnameServicewithWrongAgentname() throws Exception {

        int id = 1;
        String subject = "Subject";
        String agent = "James";
        Set<String>  categories = new HashSet<String>(Arrays.asList("tag1", "tag2"));
        assertTrue(ticketService.createTicketService(id, subject, agent, categories));

        String agentName = "Yogesh";
        List<Ticket> list = (ticketService.searchTicketsUsingAgentnameService(agentName));
        Assert.assertEquals(0, list.size());

    }

    @Test
    public void searchTicketsUsingtagService() throws Exception {

        int id = 1;
        String subject = "Subject";
        String agent = "James";
        Set<String>  categories = new HashSet<String>(Arrays.asList("tag1", "tag2"));
        assertTrue(ticketService.createTicketService(id, subject, agent, categories));

        id = 2;
        subject = "Subject2";
        agent = "James2";
         categories = new HashSet<String>(Arrays.asList("tag", "tag2"));
        assertTrue(ticketService.createTicketService(id, subject, agent, categories));

        String tag = "tag1";
        List<Ticket> list = (ticketService.searchTicketsUsingtagService(tag));

        Ticket ticket = list.get(0);
        Assert.assertEquals("James", ticket.getAgentName());
        Assert.assertEquals(1, ticket.getId());
        Assert.assertEquals("Subject", ticket.getSubject());
        Assert.assertEquals(new HashSet<String>(Arrays.asList("tag1", "tag2")), ticket.getTags());


    }


    @Test
    public void searchTicketsUsingtagServiceWithNonExistingTag() throws Exception {

        int id = 1;
        String subject = "Subject";
        String agent = "James";
        Set<String>  categories = new HashSet<String>(Arrays.asList("tag1", "tag2"));
        assertTrue(ticketService.createTicketService(id, subject, agent, categories));

        id = 2;
        subject = "Subject2";
        agent = "James2";
         categories = new HashSet<String>(Arrays.asList("one", "five", "four" ));
        assertTrue(ticketService.createTicketService(id, subject, agent, categories));

        String tag = "six";
        List<Ticket> list = (ticketService.searchTicketsUsingtagService(tag));
        Assert.assertEquals(0, list.size());

    }

    @Test
    public void searchshowTicketcountAgentService() throws Exception {

        int id = 1;
        String subject = "Subject";
        String agent = "James";
        Set<String>  categories = new HashSet<String>(Arrays.asList("tag1", "tag2"));
        assertTrue(ticketService.createTicketService(id, subject, agent, categories));

        id = 2;
        subject = "Subject2";
        agent = "James";
        categories = new HashSet<String>(Arrays.asList("tag1", "tag2"));
        assertTrue(ticketService.createTicketService(id, subject, agent, categories));

        id = 3;
        subject = "Subject3";
        agent = "Yogesh";
         categories = new HashSet<String>(Arrays.asList("tag1", "tag2"));
        assertTrue(ticketService.createTicketService(id, subject, agent, categories));

        TreeMap<String, Integer> tmCount = ticketService.showTicketCountAgentService();
        Assert.assertEquals(2, (int) tmCount.get("James"));
        Assert.assertEquals(1, (int) tmCount.get("Yogesh"));

    }


}