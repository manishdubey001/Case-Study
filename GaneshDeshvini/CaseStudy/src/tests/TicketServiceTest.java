package tests;

import factory.TicketServiceFactory;
import model.TicketModel;
import org.junit.runners.MethodSorters;
import service.TicketService;

import org.junit.*;

import java.util.Arrays;
import java.util.HashSet;

import static org.junit.Assert.*;

/**
 * Created by root on 19/1/16.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TicketServiceTest {

    TicketService ts = TicketServiceFactory.getInstance();
    int id;
    String subject;
    String agent;
    String tags;
    TicketModel ticketModel;

    @Test
    public void test1ValidCreateTicket() throws Exception {
        id = 1;
        subject = "sub";
        agent = "a1";
        tags = "a, b";
        HashSet<String> hsTags = new HashSet<>(Arrays.asList("a", "b"));
        assertTrue(ts.createTicket(id, subject, agent, tags));

        //validate data which has been saved
        ticketModel = ts.getTicketDetail(id);
        assertEquals(subject, ticketModel.getSubject());
        assertEquals(agent, ticketModel.getAgentName());
        assertEquals(hsTags, ticketModel.getTags());
    }

    @Test
    public void test2CreateTicketDuplicateId() throws Exception {
        id = 1;
        subject = "sub2";
        agent = "a2";
        tags = "d, b";
        assertFalse(ts.createTicket(id, subject, agent, tags));
    }

    @Test
    public void test3CreateTicketNullSubject() throws Exception {
        id = 2;
        subject = null;
        agent = "a2";
        tags = "a";
        assertFalse(ts.createTicket(id, subject, agent, tags));
    }

    @Test
    public void test4CreateTicketNullAgentName() throws Exception {
        id = 3;
        subject = "sub";
        agent = null;
        tags = "";
        assertFalse(ts.createTicket(id, subject, agent, tags));
    }
//
    @Test
    public void test5CreateTicketNullTags() throws Exception {
        id = 4;
        subject = "sub";
        agent = "a1";
        tags = null;
        assertTrue(ts.createTicket(id, subject, agent, tags));

        ticketModel = ts.getTicketDetail(id);
        assertNull(ticketModel.getTags());
    }
}