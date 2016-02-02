package unitTest;

import com.ticket.model.Ticket;
import com.ticket.service.TicketService;
import org.junit.*;

import java.util.*;

/**
 * Created by root on 18/1/16.
*/
public class TicketsTestClass {
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
        boolean create = ticketService.createTicket(subject,agent,tags);
        Assert.assertTrue(create);
        Assert.assertEquals(1, ticketService.ticketsMap.get(1).getId());
        Assert.assertEquals("Test Subject", ticketService.ticketsMap.get(1).getSubject());
        Assert.assertEquals("[awesome, great, good]", ticketService.ticketsMap.get(1).getTags().toString());
        Assert.assertTrue(ticketService.checkIfExists(1));
    }

    @Test
    public void testIfSubjectNotPassed(){
        this.subject = "";
        boolean create = ticketService.createTicket(subject,agent,tags);
        System.out.println("Checking Ticket creation on blank subject");
        Assert.assertFalse(create);
    }

    @Test
    public void testIfAgentNotPassed(){
        this.agent = "";
        boolean create = ticketService.createTicket(subject,agent,tags);
        System.out.println("Checking Ticket creation on blank agent");
        Assert.assertFalse(create);
    }

    /**
     * Update Ticket Test Case Functions
     */

    @Test
    public void testUpdateTicket() throws Exception {
        boolean create2 = ticketService.createTicket(this.subject, this.agent, this.tags);
        Assert.assertTrue(create2);
        boolean check = ticketService.checkIfExists(2);
        boolean update;
        if (check){
            update = ticketService.updateTicket(2, "agent", "newAgent1");
            Assert.assertTrue(update);
            Assert.assertEquals("newAgent1", ticketService.ticketsMap.get(2).getAgentName());
            update = ticketService.updateTicket(2, "tags", "tag4,tag3");
            Assert.assertTrue(update);
            Assert.assertEquals("[tag4, tag3]", ticketService.ticketsMap.get(2).getTags().toString());
        }else{

            update = ticketService.updateTicket(1, "agent", "newAgent1");
            Assert.assertTrue(update);
            Assert.assertEquals("newAgent1", ticketService.ticketsMap.get(1).getAgentName());
            update = ticketService.updateTicket(1, "tags", "tag4,tag3");
            Assert.assertTrue(update);
            Assert.assertEquals("[tag4, tag3]", ticketService.ticketsMap.get(1).getTags().toString());
        }
    }

    @Test
    public void testUpdateTicketWithBlankTypeValuesForAgent(){
        boolean create2 = ticketService.createTicket(this.subject, this.agent, this.tags);
        Assert.assertTrue(create2);
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

        boolean create2 = ticketService.createTicket(this.subject, this.agent, this.tags);
        Assert.assertTrue(create2);
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
        boolean create2 = ticketService.createTicket(this.subject, this.agent, this.tags);
        Assert.assertTrue(create2);
        boolean check = ticketService.removeTicketById(1);
        Assert.assertTrue(check);
    }

    @Test
    public void testRemoveTicketByIdAsIdIncorrect() throws Exception {
        boolean create2 = ticketService.createTicket(this.subject, this.agent, this.tags);
        Assert.assertTrue(create2);
        boolean check = ticketService.removeTicketById(2);
        check = ticketService.removeTicketById(0);
        Assert.assertFalse(check);
    }

    /**
     * Get Ticket by Id Test Case functions
     * @throws Exception
     */
    @Test
    public void testGetTicketById() throws Exception {
        boolean create2 = ticketService.createTicket(this.subject, this.agent, this.tags);
        Assert.assertTrue(create2);

        this.subject = "sub2";
        this.agent = "agent2";
        this.tags = "tag2,tag3";
        ticketService.createTicket(this.subject, this.agent, this.tags);

        create2 = ticketService.checkIfExists(2);
        Assert.assertTrue(create2);
        Ticket ticket = ticketService.getTicketById(2);
        Assert.assertNotNull(ticket);
        Assert.assertEquals(2, ticket.getId());
        Assert.assertEquals("agent2", ticket.getAgentName());
        Assert.assertEquals("[tag2, tag3]", ticket.getTags().toString());
    }

    @Test
    public void testGetTicketIdWithWrongId(){

        boolean create2 = ticketService.createTicket(this.subject, this.agent, this.tags);
        Assert.assertTrue(create2);
        Assert.assertFalse(ticketService.ticketsMap.containsKey(3));
    }

    /**
     * get Tickets with Agent name Test cases
     * @throws Exception
     */
    @Test
    public void testGetTicketsByAgentName() throws Exception {

        List<Ticket> checkTickets = new ArrayList<>();
        boolean create2 = ticketService.createTicket(this.subject, this.agent, this.tags);
        Assert.assertTrue(create2);

        this.subject = "sub2";
        this.agent = "agent2";
        this.tags = "tag2,tag3";
        ticketService.createTicket(this.subject, this.agent, this.tags);

        this.subject = "sub3";
        this.agent = "agent1";
        this.tags = "tag4,tag3";
        ticketService.createTicket(this.subject, this.agent, this.tags);

        checkTickets.add(ticketService.ticketsMap.get(3));
        checkTickets.add(ticketService.ticketsMap.get(1));
        List actual = ticketService.getTicketsByAgentName("agent1");
        Assert.assertEquals(checkTickets, actual);
    }

    @Test
    public void testGetTicketsByAgentNameWithWrongValue() throws Exception {

        List<Ticket> checkTickets = new ArrayList<>();
        boolean create2 = ticketService.createTicket(this.subject, this.agent, this.tags);
        Assert.assertTrue(create2);

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
        ticketService.createTicket(this.subject, this.agent, this.tags);
        ticketService.createTicket(this.subject, this.agent, this.tags);
        expected.add(ticketService.ticketsMap.get(2));
        expected.add(ticketService.ticketsMap.get(1));
        Assert.assertEquals(2, ticketService.ticketsMap.size());
        Assert.assertEquals(expected, ticketService.getAllTickets());
    }

    @Test
    public void testGetAllTicketsIfNone(){
        List l = new ArrayList<>();
        Assert.assertEquals(l, ticketService.getAllTickets());
    }

    /**
     * Test case function for Checking if Id exists in the system
     * @throws Exception
     */
    @Test
    public void testCheckIfExists() throws Exception {

        ticketService.createTicket(this.subject, this.agent, this.tags);
        Assert.assertTrue(ticketService.checkIfExists(1));
    }

    @Test
    public void testCheckIfExists2() throws Exception {

        ticketService.createTicket(this.subject, this.agent, this.tags);
        Assert.assertFalse(ticketService.checkIfExists(3));
    }


    @Test
    public void testGetTicketsGroupByAgent() throws Exception {
        Map<String, Integer> expected  = new HashMap<>();
        ticketService.createTicket(this.subject, this.agent, this.tags);
        ticketService.createTicket(this.subject, this.agent, this.tags);

        this.agent = "agent2";
        ticketService.createTicket(this.subject, this.agent, this.tags);

        expected.put("agent1", 2);
        expected.put("agent2", 1);
        Assert.assertEquals(expected, ticketService.getTicketsGroupByAgent());
    }

    /**
     * get all Tickets by Tag name Test case functions
     * @throws Exception
     */
    @Test
    public void testGetAllTicketsByTag() throws Exception {
        Set expected = new HashSet<>();
        ticketService.createTicket(this.subject, this.agent, this.tags);
        ticketService.createTicket(this.subject, this.agent, this.tags);
        expected.add(ticketService.ticketsMap.get(1));
        expected.add(ticketService.ticketsMap.get(2));
        Assert.assertEquals(expected, ticketService.getAllTicketsByTag("great"));
        expected.clear();
        Assert.assertEquals(expected, ticketService.getAllTicketsByTag("sdfsdf"));
    }

    @After
    public void clearTicketData(){
        ticketService.ticketsMap = null;
        ticketService = null;
    }
}
