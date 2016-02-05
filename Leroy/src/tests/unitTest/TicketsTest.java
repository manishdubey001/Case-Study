package tests.unitTest;

import com.ticket.model.Ticket;
import com.ticket.service.TicketService;
import org.junit.*;

import java.util.*;

/**
 * Created by root on 18/1/16.
*/
public class TicketsTest {
    TicketService ticketService = new TicketService();
    public String subject;
    public String agent;
    public String tags;
    /**
     * Create Ticket Test Case functions
     */
    @Before
    public void TicketsTestClass(){
        this.subject = "Test Subject";
        this.agent = "agent1";
        this.tags = "great,good,awesome";
    }

    @Test
    public void testCreateTicket(){
        System.out.println("Checking Ticket create with dummy values");
        Ticket ticket = ticketService.createTicket(subject,agent,tags);
        Assert.assertEquals(1, ticket.getId());
        Assert.assertEquals("Test Subject", ticket.getSubject());
        Assert.assertEquals("[awesome, great, good]",ticket.getTags().toString());
        Assert.assertTrue(ticketService.checkIfExists(1));
    }

    @Test
    public void testIfSubjectNotPassed(){
        this.subject = "";
        Ticket create = ticketService.createTicket(subject,agent,tags);
        System.out.println("Checking Ticket creation on blank subject");
        Assert.assertNull(create);
    }

    @Test
    public void testIfAgentNotPassed(){
        this.agent = "";
        Ticket ticket = ticketService.createTicket(subject,agent,tags);
        System.out.println("Checking Ticket creation on blank agent");
        Assert.assertNull(ticket);
    }

    /**
     * Update Ticket Test Case Functions
     */

    /*@Test
    public void testUpdateTicket() throws Exception {
        Ticket create2 = ticketService.createTicket(this.subject, this.agent, this.tags);
            //update = ticketService.updateTicket(2, "agent", "newAgent1");
            Assert.assertTrue(ticketService.updateTicket(1, "agent", "newAgent1"));
            Assert.assertEquals("newAgent1", create2.getAgentName());
            Assert.assertTrue(ticketService.updateTicket(1, "tags", "tag4,tag3"));
            Assert.assertEquals("[tag4, tag3]", create2.getTags().toString());
    }
*/
    @Test
    public void testUpdateTicketWithBlankTypeValuesForAgent(){
        Ticket create2 = ticketService.createTicket(this.subject, this.agent, this.tags);
        boolean check2 = ticketService.checkIfExists(2);
        boolean update1;
        if (check2){
            update1 = ticketService.updateTicket(2, null, "newAgent1");
            Assert.assertFalse(update1);
            update1 = ticketService.updateTicket(2, "agent", null);
            Assert.assertFalse(update1);
        }else{
            update1 = ticketService.updateTicket(1, null, "newAgent1");
            Assert.assertFalse(update1);
            update1 = ticketService.updateTicket(1, "agent", null);
            Assert.assertFalse(update1);
        }
    }

    @Test
    public void testUpdateTicketWithBlankTypeValuesForTags(){

        Ticket create2 = ticketService.createTicket(this.subject, this.agent, this.tags);
        boolean check2 = ticketService.checkIfExists(2);
        boolean update2;
        if (check2){
            update2 = ticketService.updateTicket(11, null, "tag1,tag2");
            Assert.assertFalse(update2);
            update2 = ticketService.updateTicket(11, "tags", null);
            Assert.assertFalse(update2);
        }else{
            update2 = ticketService.updateTicket(12, null, "tags3,tag4");
            Assert.assertFalse(update2);
            update2 = ticketService.updateTicket(12, "tags", null);
            Assert.assertFalse(update2);
        }
    }

    @Test
    public void testUpdateTicketWithBlankId(){
        boolean check3 = ticketService.updateTicket(0, "agent", "agent2");
        Assert.assertFalse(check3);
    }

    @Test
    public void testUpdateTicketWithAllBlank(){
        boolean check3 = ticketService.updateTicket(0, null, null);
        Assert.assertFalse(check3);
    }

    /**
     * Remove Ticket Test Case Functions
     * @throws Exception
     */
    @Test
    public void testRemoveTicketById() throws Exception {
        Ticket create2 = ticketService.createTicket(this.subject, this.agent, this.tags);
        boolean check = ticketService.removeTicketById(1);
        Assert.assertTrue(check);
    }

    @Test
    public void testRemoveTicketByIdAsIdIncorrect() throws Exception {
        Ticket create2 = ticketService.createTicket(this.subject, this.agent, this.tags);
        boolean check = ticketService.removeTicketById(create2.getId());
        check = ticketService.removeTicketById(0);
        Assert.assertFalse(check);
    }

    /**
     * Get Ticket by Id Test Case functions
     * @throws Exception
     */
    @Test
    public void testGetTicketById() throws Exception {
        Ticket create2 = ticketService.createTicket(this.subject, this.agent, this.tags);

        this.subject = "sub2";
        this.agent = "agent2";
        this.tags = "tag2,tag3";
        ticketService.createTicket(this.subject, this.agent, this.tags);

        Assert.assertTrue(ticketService.checkIfExists(create2.getId()));
        Ticket ticket = ticketService.getTicketById(2);
        Assert.assertNotNull(ticket);
        Assert.assertEquals(2, ticket.getId());
        Assert.assertEquals("agent1", ticket.getAgentName());
        Assert.assertEquals("[awesome, great, good]", ticket.getTags().toString());
    }

    @Test
    public void testGetTicketIdWithWrongId(){

        Ticket create2 = ticketService.createTicket(this.subject, this.agent, this.tags);
        Assert.assertFalse(ticketService.checkIfExists(4));
    }


    @Test
    public void testGetTicketsByAgentNameWithWrongValue() throws Exception {

        List<Ticket> checkTickets = new ArrayList<>();
        Ticket create2 = ticketService.createTicket(this.subject, this.agent, this.tags);

        List actual = ticketService.getTicketsByAgentName("agent3");
        Assert.assertEquals(checkTickets, actual);
    }

    /**
     * Get All Tickets in the System Test Case functions
     * @throws Exception
     */
    @Test
    public void testGetAllTickets() throws Exception {
        List<Ticket> expected = new ArrayList<>();
        expected.add(ticketService.createTicket(this.subject, this.agent, this.tags));
        Assert.assertEquals(expected, ticketService.getAllTickets());
    }

    @Test
    public void testGetAllTicketsIfNone(){
        List l = new ArrayList<>();
        Assert.assertEquals(l, ticketService.getAllTickets());
    }

    @After
    public void clearTicketData(){
        ticketService = null;
    }
}
