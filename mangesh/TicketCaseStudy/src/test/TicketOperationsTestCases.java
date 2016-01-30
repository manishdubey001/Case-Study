import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import service.TicketService;

import java.util.*;

/**
 * Created by root on 18/1/16.
 */
public class TicketOperationsTestCases {

    int id = 100;
    String subject = "First model.Ticket";
    String agent = "ININ";
    String tags = "mumbai,delhi,pune";
    List<String> tagList = Arrays.asList(tags.split(","));

    TicketService ticketService ;
    @Before
    public void beforeTestCasesExecution() {
        ticketService = new TicketService();
    }

    @Test
    public void testCreateTicketWithEmptySubject() {
        System.out.println("Start - testCreateTicketWithEmptySubject");
        String subject = "";
        int returnValue = ticketService.createTicket(this.id, subject,this.agent, this.tagList);
        Assert.assertEquals(100, id);
        Assert.assertEquals(0, returnValue);
        System.out.println("End - testCreateTicketWithEmptySubject");
    }

    @Test
    public void testCreateTicketWithEmptyAgentName() {
        System.out.println("Start - testCreateTicketWithEmptyAgentName");
        String agentName = "";
        int returnValue = ticketService.createTicket(this.id, this.subject,agentName, this.tagList);
        Assert.assertEquals(100, id);
        Assert.assertEquals(0, returnValue);
        System.out.println("End - testCreateTicketWithEmptyAgentName");
    }

    @Test
    public void testCreateTicketWithEmptyAgentAndSubject() {
        System.out.println("Start - testCreateTicketWithEmptyAgentAndSubject");
        String subject = "";
        String agentName = "";
        int returnValue = ticketService.createTicket(this.id, subject, agentName, this.tagList);
        Assert.assertEquals(100, id);
        Assert.assertEquals(0, returnValue);
        System.out.println("End - testCreateTicketWithEmptyAgentAndSubject");
    }

    @Test
    public void testCreateTicket() {
        System.out.println("Start - createTicketTestCase");
        int returnValue = ticketService.createTicket(this.id, this.subject,this.agent, this.tagList);
        HashMap ticketDetails = ticketService.getTicketDetailsById(returnValue);
        Assert.assertEquals(100, ticketDetails.get("id"));
        Assert.assertEquals("First model.Ticket", ticketDetails.get("subject"));
        Assert.assertEquals("ININ", ticketDetails.get("agentName"));
        Assert.assertEquals(tagList, ticketDetails.get("tags"));
        // delete dummy ticket
        ticketService.deleteTicket(returnValue);
        System.out.println("End - createTicketTestCase");
    }

    // test cases for update

    @Test
    public void testUpdateTicketWithEmptyAgent() {
        System.out.println("Start - testUpdateTicketWithEmptyAgent");
        int returnId = ticketService.createTicket(this.id, this.subject,this.agent, this.tagList);
        Assert.assertEquals(100, returnId);
        String agentName = "";
        boolean returnValue = ticketService.updateTicketAgent(returnId, agentName);
        Assert.assertEquals(false, returnValue);
        // delete dummy ticket
        ticketService.deleteTicket(returnId);
        System.out.println("End - testUpdateTicketWithEmptyAgent");
    }

    @Test
    public void testUpdateTicketWithAgent() {
        System.out.println("Start - testUpdateTicketWithAgent");
        int ticketId = ticketService.createTicket(this.id, this.subject,this.agent, this.tagList);
        Assert.assertEquals(100,ticketId);
        String agentName = "Admin";
        boolean response = ticketService.updateTicketAgent(ticketId, agentName);
        Assert.assertEquals(true, response);
        HashMap ticketDetails = ticketService.getTicketDetailsById(ticketId);
        Assert.assertEquals(100, ticketDetails.get("id"));
        Assert.assertEquals("First model.Ticket", ticketDetails.get("subject"));
        Assert.assertEquals("Admin", ticketDetails.get("agentName"));
        Assert.assertEquals(this.tagList, ticketDetails.get("tags"));
        // delete dummy ticket
        ticketService.deleteTicket(ticketId);
        System.out.println("End - testUpdateTicketWithAgent");
    }

    // test cases for delete

    @Test
    public void testDeleteTicketWithInvalidTicketId() {
        System.out.println("Start - testDeleteTicketWithInvalidTicketId");
        int ticketId = ticketService.createTicket(this.id, this.subject,this.agent, this.tagList);
        Assert.assertEquals(100,ticketId);
        boolean isTicketExist = ticketService.isTicketExist(102);
        Assert.assertEquals(false, isTicketExist);
        // delete dummy ticket
        ticketService.deleteTicket(ticketId);
        System.out.println("End - testDeleteTicketWithInvalidTicketId");
    }

    @Test
    public void testDeleteTicket() {
        System.out.println("Start - testDeleteTicket");
        int ticketId = ticketService.createTicket(this.id, this.subject,this.agent, this.tagList);
        Assert.assertEquals(100,ticketId);
        boolean isTicketExist = ticketService.isTicketExist(ticketId);
        Assert.assertEquals(true, isTicketExist);
        boolean deleteResponse = ticketService.deleteTicket(ticketId);
        Assert.assertEquals(true, deleteResponse);
        isTicketExist = ticketService.isTicketExist(ticketId);
        Assert.assertEquals(false, isTicketExist);
        // delete dummy ticket
        ticketService.deleteTicket(ticketId);
        System.out.println("End - testDeleteTicket");
    }

    //test cases for get ticket details

    @Test
    public void testGetTicketDetailsWithInvalidTicketID() {
        System.out.println("Start - testGetTicketDetailsWithInvalidTicketID");
        int ticketId = ticketService.createTicket(this.id, this.subject,this.agent, this.tagList);
        Assert.assertEquals(100,ticketId);
        Assert.assertEquals(null, ticketService.getTicketDetailsById(102251));
        // delete dummy ticket
        ticketService.deleteTicket(100);
        System.out.println("End - testGetTicketDetailsWithInvalidTicketID");
    }

    @Test
    public void testGetTicketDetailsById(){
        System.out.println("Start - testGetTicketDetails");
        int ticketId = ticketService.createTicket(this.id, this.subject,this.agent, this.tagList);
        Assert.assertEquals(100,ticketId);
        HashMap ticketDetails = ticketService.getTicketDetailsById(ticketId);
        Assert.assertEquals(100, ticketDetails.get("id"));
        Assert.assertEquals("First model.Ticket", ticketDetails.get("subject"));
        Assert.assertEquals("ININ", ticketDetails.get("agentName"));
        Assert.assertEquals(this.tagList, ticketDetails.get("tags"));
        // delete dummy ticket
        ticketService.deleteTicket(100);
        System.out.println("End - testGetTicketDetails");
    }

    @Test
    public void testGetTicketDetailsByInvalidAgentName() {
        System.out.println("Start - testGetTicketDetailsByInvalidAgentName");
        int ticketId = ticketService.createTicket(this.id, this.subject,this.agent, this.tagList);
        Assert.assertEquals(100,ticketId);
        List agentTickets = ticketService.getTicketByAgentName("154215");
        Assert.assertEquals(0,agentTickets.size());
        // delete dummy ticket
        ticketService.deleteTicket(100);
        System.out.println("End - testGetTicketDetailsByInvalidAgentName");
    }

    @Test
    public void testGetTicketDetailsByValidAgentName() {
        System.out.println("Start - testGetTicketDetailsByValidAgentName");
        int ticketId = ticketService.createTicket(this.id, this.subject,this.agent, this.tagList);
        Assert.assertEquals(100,ticketId);
        List agentTickets = ticketService.getTicketByAgentName(this.agent);
        Assert.assertNotEquals(0, agentTickets.size());
        // delete dummy ticket
        ticketService.deleteTicket(100);
        System.out.println("End - testGetTicketDetailsByValidAgentName");
    }

    @Test
    public void testIsTicketExistByInvalidId(){
        System.out.println("Start - testIsTicketExistByInvalidId");
        int ticketId = ticketService.createTicket(this.id, this.subject,this.agent, this.tagList);
        Assert.assertEquals(100,ticketId);
        boolean isTicketExist = ticketService.isTicketExist(15421545);
        Assert.assertEquals(false, isTicketExist);
        // delete dummy ticket
        ticketService.deleteTicket(100);
        System.out.println("End - testIsTicketExistByInvalidId");
    }

    @Test
    public void testTicketExistByValidId() {
        System.out.println("Start - testIsTicketExistByInvalidId");
        int ticketId = ticketService.createTicket(this.id, this.subject, this.agent, this.tagList);
        Assert.assertEquals(100, ticketId);
        boolean isTicketExist = ticketService.isTicketExist(ticketId);
        Assert.assertEquals(true, isTicketExist);
        // delete dummy ticket
        ticketService.deleteTicket(100);
        System.out.println("End - testIsTicketExistByInvalidId");
    }

    @Test
    public void testGetTicketDetailsByInvalidTagName(){
        System.out.println("Start - testGetTicketDetailsByInvalidTagName");
        int ticketId = ticketService.createTicket(this.id, this.subject, this.agent, this.tagList);
        Assert.assertEquals(100, ticketId);
        String tag = "12151";
        HashSet tagTicketIds = ticketService.searchTicketsByTagName(tag);
        Assert.assertEquals(0, tagTicketIds.size());
        // delete dummy ticket
        ticketService.deleteTicket(100);
        System.out.println("End - testGetTicketDetailsByInvalidTagName");
    }

    @Test
    public void testGetTicketDetailsByValidTagName(){
        System.out.println("Start - testGetTicketDetailsByValidTagName");
        int ticketId = ticketService.createTicket(this.id, this.subject, this.agent, this.tagList);
        Assert.assertEquals(100, ticketId);
        String tag = "delhi";
        HashSet tagTicketIds = ticketService.searchTicketsByTagName(tag);
        Assert.assertNotEquals(0, tagTicketIds.size());
        // delete dummy ticket
        ticketService.deleteTicket(100);
        System.out.println("End - testGetTicketDetailsByValidTagName");
    }
}
