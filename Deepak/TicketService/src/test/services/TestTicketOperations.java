package test.services;

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


    @Test
    public void testCreateTicketNullSubject(){
        subject = null;

        TicketOperations objTicketOperation = new TicketOperations();
        long created = objTicketOperation.createTicket(subject,agent_name, hashTags);

        assertEquals(0, created);
    }


    @Test
    public void testCreateTicketNullAgent(){
        agent_name = null;
        TicketOperations objTicketOperation = new TicketOperations();
        long created = objTicketOperation.createTicket(subject,agent_name, hashTags);

        assertEquals(0, created);
    }


    @Test
    public void testCreateTicketWithDuplicateTags(){
        TicketOperations objTicketOperation = new TicketOperations();
        hashTags = new HashSet<>(Arrays.asList("Tag1","Tag2", "Tag1"));  // add duplicate tags

        long created = objTicketOperation.createTicket(subject,agent_name, hashTags);

        // assertEquals(1, created);
        assertNotNull(created);
        assertNotEquals(0, created);

        List<Ticket> createdTicket = objTicketOperation.showTicketById((int) created);
        assertNotNull(createdTicket);
        assertEquals(createdTicket.get(0).getId(), created);
        assertEquals(createdTicket.get(0).getAgent_name(), agent_name);
        assertEquals(createdTicket.get(0).getSubject(), subject);
        assertEquals(createdTicket.get(0).getTags2(), hashTags);
        assertEquals(createdTicket.get(0).getTags2().size(), 2);
    }


    @Test
    public void testTicketUpdate(){
        TicketOperations objTicketOperation = new TicketOperations();
        long created = objTicketOperation.createTicket(subject,agent_name, hashTags);

        // assertEquals(1, created);
        assertNotNull(created);
        assertNotEquals(0, created);
        String agent_name = "Updated Agent";
        hashTags.clear();
        hashTags.add("New_Tag1");
        hashTags.add("New_Tag2");

        int updateId = objTicketOperation.updateTicket((int) created, agent_name, hashTags);
        assertNotNull(updateId);
        assertNotEquals(0, updateId);

        List<Ticket> createdTicket = objTicketOperation.showTicketById((int) updateId);
        assertNotNull(createdTicket);
        assertEquals(createdTicket.get(0).getId(), created);
        assertEquals(createdTicket.get(0).getAgent_name(), agent_name);
        assertEquals(createdTicket.get(0).getSubject(), subject);
        assertEquals(createdTicket.get(0).getTags2(), hashTags);
    }


    @Test
    public void testTicketUpdateEmptyAgentName(){
        TicketOperations objTicketOperation = new TicketOperations();
        long created = objTicketOperation.createTicket(subject,agent_name, hashTags);

        // assertEquals(1, created);
        assertNotNull(created);
        assertNotEquals(0, created);


        String agent_name = "";
        hashTags.clear();
        hashTags.add("New_Tag1");
        hashTags.add("New_Tag2");

        int updateId = objTicketOperation.updateTicket((int) created, agent_name, hashTags);
        assertNotNull(updateId);
        assertEquals(0, updateId);
    }


    @Test
    public void testTicketUpdateNullAgentName(){
        TicketOperations objTicketOperation = new TicketOperations();
        long created = objTicketOperation.createTicket(subject,agent_name, hashTags);

        // assertEquals(1, created);
        assertNotNull(created);
        assertNotEquals(0, created);


        String agent_name = null;
        hashTags.clear();
        hashTags.add("New_Tag1");
        hashTags.add("New_Tag2");

        int updateId = objTicketOperation.updateTicket((int) created, agent_name, hashTags);
        assertNotNull(updateId);
        assertEquals(0, updateId);
    }


    @Test
    public void testTicketReadById(){
        TicketOperations objTicketOperation = new TicketOperations();
        long created = objTicketOperation.createTicket(subject,agent_name, hashTags);

        // assertEquals(1, created);
        assertNotNull(created);
        assertNotEquals(0, created);

        List<Ticket> createdTicket = objTicketOperation.showTicketById((int) created);
        assertNotNull(createdTicket);
        assertEquals(createdTicket.get(0).getId(), created);
        assertEquals(createdTicket.get(0).getAgent_name(), agent_name);
        assertEquals(createdTicket.get(0).getSubject(), subject);
        assertEquals(createdTicket.get(0).getTags2(), hashTags);
    }


    @Test
    public void testTicketDelete(){
        TicketOperations objTicketOperation = new TicketOperations();
        long created = objTicketOperation.createTicket(subject,agent_name, hashTags);

        assertNotNull(created);
        assertNotEquals(0, created);

        objTicketOperation.deleteTicketById((int) created);

        List<Ticket> createdTicket = objTicketOperation.showTicketById((int) created);
        assertTrue(createdTicket.isEmpty());

    }


    @Test
    public void testTicketDeleteFailed(){
        TicketOperations objTicketOperation = new TicketOperations();
        long created = objTicketOperation.createTicket(subject,agent_name, hashTags);

        assertNotNull(created);
        assertNotEquals(0, created);

        objTicketOperation.deleteTicketById((int) created +1);

        List<Ticket> createdTicket = objTicketOperation.showTicketById((int) created);
        assertFalse(createdTicket.isEmpty());  // Not deleted
    }
}