package com.Tickets.UnitTests;

import com.Tickets.Sout;
import com.Tickets.Ticket;
import com.Tickets.TicketServiceComponent;
import com.Tickets.TicketWareHouse;
import com.sun.org.apache.xml.internal.dtm.ref.DTMDefaultBaseIterators;
import com.sun.org.apache.xpath.internal.NodeSetDTM;
import org.junit.*;

import java.util.*;

/**
 * Created by root on 18/1/16.
*/
public class TicketsTestClass {
    TicketServiceComponent ticketServiceComponent = new TicketServiceComponent();
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
        ticketServiceComponent.print = false;
    }

    @Test
    public void testCreateTicket(){
        System.out.println("Checking Ticket create with dummy values");
        boolean create = ticketServiceComponent.createTicket(subject,agent,tags);
        Assert.assertTrue(create);
        Assert.assertEquals(1, ticketServiceComponent.thm.get(1).getId());
        Assert.assertEquals("Test Subject", ticketServiceComponent.thm.get(1).subject);
        Assert.assertEquals("[awesome, great, good]",ticketServiceComponent.thm.get(1).tags.toString());
        Assert.assertTrue(ticketServiceComponent.checkIfExists(1));
    }

    @Test
    public void testIfSubjectNotPassed(){
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

    /**
     * Update Ticket Test Case Functions
     */

    @Test
    public void testUpdateTicket() throws Exception {
        boolean create2 = ticketServiceComponent.createTicket(this.subject, this.agent, this.tags);
        Assert.assertTrue(create2);
        boolean check = ticketServiceComponent.checkIfExists(2);
        boolean update;
        if (check){
            update = ticketServiceComponent.updateTicket(2, "agent", "newAgent1");
            Assert.assertTrue(update);
            Assert.assertEquals("newAgent1", ticketServiceComponent.thm.get(2).agent_name);
            update =ticketServiceComponent.updateTicket(2, "tags", "tag4,tag3");
            Assert.assertTrue(update);
            Assert.assertEquals("[tag4, tag3]", ticketServiceComponent.thm.get(2).tags.toString());
        }else{

            update = ticketServiceComponent.updateTicket(1, "agent", "newAgent1");
            Assert.assertTrue(update);
            Assert.assertEquals("newAgent1", ticketServiceComponent.thm.get(1).agent_name);
            update =ticketServiceComponent.updateTicket(1, "tags", "tag4,tag3");
            Assert.assertTrue(update);
            Assert.assertEquals("[tag4, tag3]", ticketServiceComponent.thm.get(1).tags.toString());
        }
    }

    @Test
    public void testUpdateTicketWithBlankTypeValuesForAgent(){
        boolean create2 = ticketServiceComponent.createTicket(this.subject, this.agent, this.tags);
        Assert.assertTrue(create2);
        boolean check2 = ticketServiceComponent.checkIfExists(2);
        boolean update1;
        if (check2){
            update1 = ticketServiceComponent.updateTicket(2, null, "newAgent1");
            Assert.assertFalse(update1);
            update1 =ticketServiceComponent.updateTicket(2, "agent", null);
            Assert.assertFalse(update1);
        }else{
            update1 = ticketServiceComponent.updateTicket(1, null, "newAgent1");
            Assert.assertFalse(update1);
            update1 =ticketServiceComponent.updateTicket(1, "agent", null);
            Assert.assertFalse(update1);
        }
    }

    @Test
    public void testUpdateTicketWithBlankTypeValuesForTags(){

        boolean create2 = ticketServiceComponent.createTicket(this.subject, this.agent, this.tags);
        Assert.assertTrue(create2);
        boolean check2 = ticketServiceComponent.checkIfExists(2);
        boolean update2;
        if (check2){
            update2 = ticketServiceComponent.updateTicket(11, null, "tag1,tag2");
            Assert.assertFalse(update2);
            update2 =ticketServiceComponent.updateTicket(11, "tags", null);
            Assert.assertFalse(update2);
        }else{
            update2 = ticketServiceComponent.updateTicket(12, null, "tags3,tag4");
            Assert.assertFalse(update2);
            update2 =ticketServiceComponent.updateTicket(12, "tags", null);
            Assert.assertFalse(update2);
        }
    }

    @Test
    public void testUpdateTicketWithBlankId(){
        boolean check3 = ticketServiceComponent.updateTicket(0, "agent", "agent2");
        Assert.assertFalse(check3);
    }

    @Test
    public void testUpdateTicketWithAllBlank(){
        boolean check3 = ticketServiceComponent.updateTicket(0, null, null);
        Assert.assertFalse(check3);
    }

    /**
     * Remove Ticket Test Case Functions
     * @throws Exception
     */
    @Test
    public void testRemoveTicketById() throws Exception {
        boolean create2 = ticketServiceComponent.createTicket(this.subject, this.agent, this.tags);
        Assert.assertTrue(create2);
        boolean check = ticketServiceComponent.removeTicketById(1);
        Assert.assertTrue(check);
    }

    @Test
    public void testRemoveTicketByIdAsIdIncorrect() throws Exception {
        boolean create2 = ticketServiceComponent.createTicket(this.subject, this.agent, this.tags);
        Assert.assertTrue(create2);
        boolean check = ticketServiceComponent.removeTicketById(2);
        check = ticketServiceComponent.removeTicketById(0);
        Assert.assertFalse(check);
    }

    /**
     * Get Ticket by Id Test Case functions
     * @throws Exception
     */
    @Test
    public void testGetTicketById() throws Exception {
        boolean create2 = ticketServiceComponent.createTicket(this.subject, this.agent, this.tags);
        Assert.assertTrue(create2);

        this.subject = "sub2";
        this.agent = "agent2";
        this.tags = "tag2,tag3";
        ticketServiceComponent.createTicket(this.subject, this.agent, this.tags);

        create2 = ticketServiceComponent.checkIfExists(2);
        Assert.assertTrue(create2);
        Ticket ticket = ticketServiceComponent.getTicketById(2);
        Assert.assertNotNull(ticket);
        Assert.assertEquals(2, ticket.getId());
        Assert.assertEquals("agent2", ticket.getAgent_name());
        Assert.assertEquals("[tag2, tag3]", ticket.getTags().toString());
    }

    @Test
    public void testGetTicketIdWithWrongId(){

        boolean create2 = ticketServiceComponent.createTicket(this.subject, this.agent, this.tags);
        Assert.assertTrue(create2);
        Assert.assertFalse(ticketServiceComponent.thm.containsKey(3));
    }

    /**
     * get Tickets with Agent name Test cases
     * @throws Exception
     */
    @Test
    public void testGetTicketsByAgentName() throws Exception {

        List<Ticket> checkTickets = new ArrayList<>();
        boolean create2 = ticketServiceComponent.createTicket(this.subject, this.agent, this.tags);
        Assert.assertTrue(create2);

        this.subject = "sub2";
        this.agent = "agent2";
        this.tags = "tag2,tag3";
        ticketServiceComponent.createTicket(this.subject, this.agent, this.tags);

        this.subject = "sub3";
        this.agent = "agent1";
        this.tags = "tag4,tag3";
        ticketServiceComponent.createTicket(this.subject, this.agent, this.tags);

        checkTickets.add(ticketServiceComponent.thm.get(3));
        checkTickets.add(ticketServiceComponent.thm.get(1));
        List actual = ticketServiceComponent.getTicketsByAgentName("agent1");
        Assert.assertEquals(checkTickets, actual);
    }

    @Test
    public void testGetTicketsByAgentNameWithWrongValue() throws Exception {

        List<Ticket> checkTickets = new ArrayList<>();
        boolean create2 = ticketServiceComponent.createTicket(this.subject, this.agent, this.tags);
        Assert.assertTrue(create2);

        List actual = ticketServiceComponent.getTicketsByAgentName("agent3");
        Assert.assertEquals(checkTickets, actual);
    }

    /**
     * Get All Tickets in the System Test Case functions
     * @throws Exception
     */
    @Test
    public void testGetAllTickets() throws Exception {
        List<Ticket> expected = new ArrayList<>();
        ticketServiceComponent.createTicket(this.subject, this.agent, this.tags);
        ticketServiceComponent.createTicket(this.subject, this.agent, this.tags);
        expected.add(ticketServiceComponent.thm.get(2));
        expected.add(ticketServiceComponent.thm.get(1));
        Assert.assertEquals(2, ticketServiceComponent.thm.size());
        Assert.assertEquals(expected, ticketServiceComponent.getAllTickets());
    }

    @Test
    public void testGetAllTicketsIfNone(){
        List l = new ArrayList<>();
        Assert.assertEquals(l, ticketServiceComponent.getAllTickets());
    }

    /**
     * Test case function for Checking if Id exists in the system
     * @throws Exception
     */
    @Test
    public void testCheckIfExists() throws Exception {

        ticketServiceComponent.createTicket(this.subject, this.agent, this.tags);
        Assert.assertTrue(ticketServiceComponent.checkIfExists(1));
    }

    @Test
    public void testCheckIfExists2() throws Exception {

        ticketServiceComponent.createTicket(this.subject, this.agent, this.tags);
        Assert.assertFalse(ticketServiceComponent.checkIfExists(3));
    }


    @Test
    public void testGetTicketsGroupByAgent() throws Exception {
        Map<String, Integer> expected  = new HashMap<>();
        ticketServiceComponent.createTicket(this.subject, this.agent, this.tags);
        ticketServiceComponent.createTicket(this.subject, this.agent, this.tags);

        this.agent = "agent2";
        ticketServiceComponent.createTicket(this.subject, this.agent, this.tags);

        expected.put("agent1", 2);
        expected.put("agent2", 1);
        Assert.assertEquals(expected, ticketServiceComponent.getTicketsGroupByAgent());
    }

    /**
     * get all Tickets by Tag name Test case functions
     * @throws Exception
     */
    @Test
    public void testGetAllTicketsByTag() throws Exception {
        Set expected = new HashSet<>();
        ticketServiceComponent.createTicket(this.subject, this.agent, this.tags);
        ticketServiceComponent.createTicket(this.subject, this.agent, this.tags);
        expected.add(ticketServiceComponent.thm.get(1));
        expected.add(ticketServiceComponent.thm.get(2));
        Assert.assertEquals(expected, ticketServiceComponent.getAllTicketsByTag("great"));
        expected.clear();
        Assert.assertEquals(expected,ticketServiceComponent.getAllTicketsByTag("sdfsdf"));
    }

    @After
    public void clearTicketData(){
        ticketServiceComponent.thm = null;
        ticketServiceComponent = null;
    }
}
