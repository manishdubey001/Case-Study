import com.sun.xml.internal.ws.policy.AssertionSet;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by root on 18/1/16.
 */
public class TicketOperationsTestCases {

    int id = 100;
    String subject = "First Ticket";
    String agent = "ININ";
    String tags = "mumbai,delhi,pune";
    List<String> tagList = Arrays.asList(tags.split(","));

    @Test
    public void testCreateTicketWithEmptySubject() {
        System.out.println("Start - testCreateTicketWithEmptySubject");
        TicketService ticketService = new TicketService();

        String subject = "";

        int returnValue = ticketService.createTicket(this.id, subject,this.agent, this.tagList);

        Assert.assertEquals(100, id);
        Assert.assertEquals(0, returnValue);
        System.out.println("End - testCreateTicketWithEmptySubject");
    }

    @Test
    public void testCreateTicketWithEmptyAgentName() {
        System.out.println("Start - testCreateTicketWithEmptyAgentName");

        TicketService ticketService = new TicketService();

        String agentName = "";

        int returnValue = ticketService.createTicket(this.id, this.subject,agentName, this.tagList);

        Assert.assertEquals(100, id);
        Assert.assertEquals(0, returnValue);

        System.out.println("End - testCreateTicketWithEmptyAgentName");
    }

    @Test
    public void testCreateTicketWithEmptyAgentAndSubject() {
        System.out.println("Start - testCreateTicketWithEmptyAgentAndSubject");
        TicketService ticketService = new TicketService();
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
        TicketService ticketservice = new TicketService();

        int returnValue = ticketservice.createTicket(this.id, this.subject,this.agent, this.tagList);

        HashMap ticketDetails = ticketservice.getTicketDetailsById(returnValue);

        Assert.assertEquals(100, ticketDetails.get("id"));
        Assert.assertEquals("First Ticket", ticketDetails.get("subject"));
        Assert.assertEquals("ININ", ticketDetails.get("agentName"));
        Assert.assertEquals(tagList, ticketDetails.get("tags"));
        System.out.println("End - createTicketTestCase");
    }
}
