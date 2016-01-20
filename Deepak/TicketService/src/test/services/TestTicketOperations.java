package test.services;

import com.model.Ticket;
import com.services.TicketOperations;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

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
        assertEquals(agent_name, createdTicket.get(0).getAgent_name());
        assertEquals(subject, createdTicket.get(0).getSubject());
        assertEquals(hashTags, createdTicket.get(0).getTags2());
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
    public void testCreateTicketEmptyTag(){
        hashTags.clear();
        TicketOperations objTicketOperation = new TicketOperations();
        long created = objTicketOperation.createTicket(subject,agent_name, hashTags);

        assertNotNull(created);
        assertNotEquals(0, created);

        List<Ticket> createdTicket = objTicketOperation.showTicketById((int) created);
        assertNotNull(createdTicket);
        assertEquals(agent_name, createdTicket.get(0).getAgent_name());
        assertEquals(subject, createdTicket.get(0).getSubject());
        assertEquals(hashTags, createdTicket.get(0).getTags2());

        assertEquals(0, createdTicket.get(0).getTags2().size());
    }


    @Test
    public void testCreateTicketNullTag(){
        hashTags = null;
        TicketOperations objTicketOperation = new TicketOperations();
        long created = objTicketOperation.createTicket(subject,agent_name, hashTags);

        assertNotNull(created);
        assertNotEquals(0, created);

        List<Ticket> createdTicket = objTicketOperation.showTicketById((int) created);
        assertNotNull(createdTicket);
        assertEquals(agent_name, createdTicket.get(0).getAgent_name());
        assertEquals(subject, createdTicket.get(0).getSubject());
        assertEquals(hashTags, createdTicket.get(0).getTags2());

        assertNull(createdTicket.get(0).getTags2());
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
        assertEquals(created, createdTicket.get(0).getId());
        assertEquals(agent_name, createdTicket.get(0).getAgent_name());
        assertEquals(subject, createdTicket.get(0).getSubject());
        assertEquals(hashTags, createdTicket.get(0).getTags2());
        assertEquals(2, createdTicket.get(0).getTags2().size());
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
        assertEquals(created, createdTicket.get(0).getId());
        assertEquals(agent_name, createdTicket.get(0).getAgent_name());
        assertEquals(subject, createdTicket.get(0).getSubject());
        assertEquals(hashTags, createdTicket.get(0).getTags2());
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
    public void testTicketUpdateEmptyTag(){
        TicketOperations objTicketOperation = new TicketOperations();
        long created = objTicketOperation.createTicket(subject,agent_name, hashTags);

        // assertEquals(1, created);
        assertNotNull(created);
        assertNotEquals(0, created);

        hashTags.clear();   // empty the tags set

        int updateId = objTicketOperation.updateTicket((int) created, agent_name, hashTags);
        assertNotNull(updateId);
        assertNotEquals(0, updateId);
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
        assertEquals(created, createdTicket.get(0).getId());
        assertEquals(agent_name, createdTicket.get(0).getAgent_name());
        assertEquals(subject, createdTicket.get(0).getSubject());
        assertEquals(hashTags, createdTicket.get(0).getTags2());
    }


    @Test
    public void testTicketReadByInvalidId(){
        TicketOperations objTicketOperation = new TicketOperations();
        long created = objTicketOperation.createTicket(subject,agent_name, hashTags);

        // assertEquals(1, created);
        assertNotNull(created);
        assertNotEquals(0, created);

        List<Ticket> createdTicket = objTicketOperation.showTicketById(((int) created +1));
        assertTrue(createdTicket.isEmpty());
    }

    @Test
    public void testTicketDeleteById(){
        TicketOperations objTicketOperation = new TicketOperations();
        long created = objTicketOperation.createTicket(subject,agent_name, hashTags);

        assertNotNull(created);
        assertNotEquals(0, created);

        objTicketOperation.deleteTicketById((int) created);

        List<Ticket> createdTicket = objTicketOperation.showTicketById((int) created);
        assertTrue(createdTicket.isEmpty());

    }


    @Test
    public void testTicketDeleteByInvalidId(){
        TicketOperations objTicketOperation = new TicketOperations();
        long created = objTicketOperation.createTicket(subject,agent_name, hashTags);

        assertNotNull(created);
        assertNotEquals(0, created);

        objTicketOperation.deleteTicketById((int) created +1);

        List<Ticket> createdTicket = objTicketOperation.showTicketById((int) created);
        assertFalse(createdTicket.isEmpty());  // Not deleted
    }


    @Test
    public void testTicketSerachByAgentName(){
        TicketOperations objTicketOperation = new TicketOperations();
        long created = objTicketOperation.createTicket(subject,agent_name, hashTags);

        assertNotNull(created);
        assertNotEquals(0, created);

        List<Ticket> searchedTickets = objTicketOperation.searchTicketsWithAgent("Agent1");
        assertNotNull(searchedTickets);
        assertFalse(searchedTickets.isEmpty());
        assertEquals("Agent1", searchedTickets.get(0).getAgent_name());
    }

    @Test
    public void testTicketSerachByInvalidAgentName(){
        TicketOperations objTicketOperation = new TicketOperations();
        long created = objTicketOperation.createTicket(subject,agent_name, hashTags);

        assertNotNull(created);
        assertNotEquals(0, created);

        List<Ticket> searchedTickets = objTicketOperation.searchTicketsWithAgent("Agent2");
        assertNotNull(searchedTickets);
        assertTrue(searchedTickets.isEmpty());

        if(!searchedTickets.isEmpty())
            assertNotEquals("Agent1", searchedTickets.get(0).getAgent_name());
    }


    @Test
    public void testTicketsSearchByTag(){
        TicketOperations objTicketOperation = new TicketOperations();
        long created = objTicketOperation.createTicket(subject,agent_name, hashTags);

        assertNotNull(created);
        assertNotEquals(0, created);

        Set<String> searchTag = new HashSet<>(Arrays.asList("Tag1"));

        List<Ticket> searchedTickets = objTicketOperation.searchTicketsWithTags(searchTag);

        assertNotNull(searchedTickets);
        assertFalse(searchedTickets.isEmpty());
        assertTrue(searchedTickets.get(0).getTags2().contains("Tag1"));

    }


    @Test
    public void testTicketsSearchByInvalidTag(){
        TicketOperations objTicketOperation = new TicketOperations();
        long created = objTicketOperation.createTicket(subject,agent_name, hashTags);

        assertNotNull(created);
        assertNotEquals(0, created);

        Set<String> searchTag = new HashSet<>(Arrays.asList("TagNotValid"));

        List<Ticket> searchedTickets = objTicketOperation.searchTicketsWithTags(searchTag);

        assertNotNull(searchedTickets);
        assertTrue(searchedTickets.isEmpty());

    }


    @Test
    public void testAgentsTicketCount(){
        TicketOperations objTicketOperation = new TicketOperations();
        long created = objTicketOperation.createTicket(subject,agent_name, hashTags);

        assertNotNull(created);
        assertNotEquals(0, created);

        Map<String, Integer> agentTicketsCount = objTicketOperation.calculateAgentTicketCount();

        assertNotNull(agentTicketsCount);
        assertTrue(agentTicketsCount.containsKey("Agent1"));
        assertEquals(Integer.valueOf(1), agentTicketsCount.get("Agent1"));
    }
}