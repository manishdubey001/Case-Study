package ticketservicetest;

import com.model.Ticket;
import com.services.TicketOperations;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;       // This is static import, thus we can use all the static members and method directly without Name of Class.

/**
 * Created by root on 18/1/16.
 */
public class TestTicketOperations {

    public static String subject = null;
    public static String agent_name = null;
    public static Set<String> hashTags = null;


    @Before
    public void setParams(){
        subject = "Subject1";
        agent_name = "Agent1";
        hashTags = new HashSet<>(Arrays.asList("Tag1", "Tag2", "Tag3"));
    }


    @Test
    public void testCreateTicket(){
        TicketOperations objTicketOperation = new TicketOperations();
        long created = objTicketOperation.createTicket(subject,agent_name, hashTags);

        // assertEquals(1, created);
        assertNotNull(created);
        assertNotEquals(0, created);

        List<Ticket> createdTicket = objTicketOperation.showTicketById((int) created);
        assertNotNull(createdTicket);
        assertEquals(createdTicket.get(0).getAgent_name(), agent_name);
        assertEquals(createdTicket.get(0).getSubject(), subject);
        assertEquals(createdTicket.get(0).getTags2(), hashTags);

    }


    @Test
    public void testCreateTicketEmptySubject(){
        subject = "";

        TicketOperations objTicketOperation = new TicketOperations();
        long created = objTicketOperation.createTicket(subject,agent_name, hashTags);

        assertEquals(0, created);
    }


    @Test
    public void testCreateTicketEmptyAgent(){
        agent_name = "";
        TicketOperations objTicketOperation = new TicketOperations();
        long created = objTicketOperation.createTicket(subject,agent_name, hashTags);

        assertEquals(0, created);
    }

}