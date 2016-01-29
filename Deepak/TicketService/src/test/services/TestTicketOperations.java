package test.services;

import com.model.Ticket;
import com.services.TicketOperations;
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
        Ticket createdTicket = objTicketOperation.createTicket(subject,agent_name, hashTags);

        assertNotNull(createdTicket);
        assertNotEquals(0, createdTicket.getId());

        assertEquals(agent_name, createdTicket.getAgentName());
        assertEquals(subject, createdTicket.getSubject());
        assertEquals(hashTags, createdTicket.getTags());
    }


    @Test
    public void testCreateTicketEmptySubject(){
        subject = "";
        TicketOperations objTicketOperation = new TicketOperations();
        Ticket createdTicket = objTicketOperation.createTicket(subject,agent_name, hashTags);

        assertNull(createdTicket);
    }


    @Test
    public void testCreateTicketEmptyAgent(){
        agent_name = "";
        TicketOperations objTicketOperation = new TicketOperations();
        Ticket createdTicket = objTicketOperation.createTicket(subject,agent_name, hashTags);

        assertNull(createdTicket);
    }


    @Test
    public void testCreateTicketNullSubject(){
        subject = null;
        TicketOperations objTicketOperation = new TicketOperations();
        Ticket createdTicket = objTicketOperation.createTicket(subject,agent_name, hashTags);

        assertNull(createdTicket);
    }


    @Test
    public void testCreateTicketNullAgent(){
        agent_name = null;
        TicketOperations objTicketOperation = new TicketOperations();
        Ticket createdTicket = objTicketOperation.createTicket(subject,agent_name, hashTags);

        assertNull(createdTicket);
    }


    @Test
    public void testCreateTicketEmptyTag(){
        hashTags.clear();
        TicketOperations objTicketOperation = new TicketOperations();
        Ticket createdTicket = objTicketOperation.createTicket(subject,agent_name, hashTags);

        assertNotNull(createdTicket);
        assertNotEquals(0, createdTicket.getId());

        assertEquals(agent_name, createdTicket.getAgentName());
        assertEquals(subject, createdTicket.getSubject());
        assertEquals(hashTags, createdTicket.getTags());

        assertEquals(0, createdTicket.getTags().size());
    }


    @Test
    public void testCreateTicketNullTag(){
        hashTags = null;
        TicketOperations objTicketOperation = new TicketOperations();
        Ticket createdTicket = objTicketOperation.createTicket(subject,agent_name, hashTags);

        assertNotNull(createdTicket);
        assertNotEquals(0, createdTicket.getId());

        assertEquals(agent_name, createdTicket.getAgentName());
        assertEquals(subject, createdTicket.getSubject());
        assertTrue(createdTicket.getTags().isEmpty());

        assertEquals(0, createdTicket.getTags().size());
    }


    @Test
    public void testCreateTicketWithDuplicateTags(){
        TicketOperations objTicketOperation = new TicketOperations();
        hashTags = new HashSet<>(Arrays.asList("Tag1","Tag2", "Tag1"));  // add duplicate tags
        Ticket createdTicket = objTicketOperation.createTicket(subject,agent_name, hashTags);

        assertNotNull(createdTicket);
        assertNotEquals(0, createdTicket.getId());

        assertNotNull(createdTicket);
        assertEquals(agent_name, createdTicket.getAgentName());
        assertEquals(subject, createdTicket.getSubject());
        assertEquals(hashTags, createdTicket.getTags());

        assertEquals(2, createdTicket.getTags().size());
    }


    @Test
    public void testTicketUpdate(){
        TicketOperations objTicketOperation = new TicketOperations();
        Ticket createdTicket = objTicketOperation.createTicket(subject,agent_name, hashTags);

        assertNotNull(createdTicket);
        assertNotEquals(0, createdTicket.getId());

        String agent_name = "Updated Agent";
        hashTags.clear();
        hashTags.add("New_Tag1");
        hashTags.add("New_Tag2");

        Ticket ticket = objTicketOperation.updateTicket((int) createdTicket.getId(), agent_name, hashTags);
        assertNotNull(ticket);
        assertEquals(agent_name, ticket.getAgentName());
        assertEquals(hashTags, ticket.getTags());

    }


    @Test
    public void testTicketUpdateEmptyAgentName(){
        TicketOperations objTicketOperation = new TicketOperations();
        Ticket createdTicket = objTicketOperation.createTicket(subject,agent_name, hashTags);

        assertNotNull(createdTicket);
        assertNotEquals(0, createdTicket.getId());

        String agent_name = "";
        hashTags.clear();
        hashTags.add("New_Tag1");
        hashTags.add("New_Tag2");

        Ticket ticket = objTicketOperation.updateTicket((int) createdTicket.getId(), agent_name, hashTags);
        assertNull(ticket);
    }


    @Test
    public void testTicketUpdateNullAgentName(){
        TicketOperations objTicketOperation = new TicketOperations();
        Ticket createdTicket = objTicketOperation.createTicket(subject,agent_name, hashTags);

        assertNotNull(createdTicket);
        assertNotEquals(0, createdTicket.getId());

        String agent_name = null;
        hashTags.clear();
        hashTags.add("New_Tag1");
        hashTags.add("New_Tag2");

        Ticket ticket = objTicketOperation.updateTicket((int) createdTicket.getId(), agent_name, hashTags);
        assertNull(ticket);
    }


    @Test
    public void testTicketUpdateEmptyTag(){
        TicketOperations objTicketOperation = new TicketOperations();
        Ticket createdTicket = objTicketOperation.createTicket(subject,agent_name, hashTags);

        assertNotNull(createdTicket);
        assertNotEquals(0, createdTicket.getId());

        hashTags.clear();   // empty the tags set

        Ticket ticket = objTicketOperation.updateTicket((int) createdTicket.getId(), agent_name, hashTags);
        assertNotNull(ticket);
        assertEquals(agent_name, ticket.getAgentName());
        assertEquals(hashTags, ticket.getTags());
    }


    @Test
    public void testTicketUpdateWithInvalidId(){
        TicketOperations objTicketOperation = new TicketOperations();
        Ticket createdTicket = objTicketOperation.createTicket(subject,agent_name, hashTags);

        assertNotNull(createdTicket);
        assertNotEquals(0, createdTicket.getId());

        Ticket ticket = objTicketOperation.updateTicket(0, agent_name, hashTags);
        assertNull(ticket);
    }


    @Test
    public void testTicketReadById(){
        TicketOperations objTicketOperation = new TicketOperations();
        Ticket createdTicket = objTicketOperation.createTicket(subject,agent_name, hashTags);

        assertNotNull(createdTicket);
        assertNotEquals(0, createdTicket.getId());

        Map<Long, Ticket> getTicket = objTicketOperation.getTicketById((int) createdTicket.getId());
        assertNotNull(getTicket);
        assertEquals(createdTicket.getId(), getTicket.get(createdTicket.getId()).getId());
        assertEquals(agent_name, getTicket.get(createdTicket.getId()).getAgentName());
        assertEquals(subject, getTicket.get(createdTicket.getId()).getSubject());
        assertEquals(hashTags, getTicket.get(createdTicket.getId()).getTags());
    }


    @Test
    public void testTicketReadByInvalidId(){
        TicketOperations objTicketOperation = new TicketOperations();
        Ticket createdTicket = objTicketOperation.createTicket(subject,agent_name, hashTags);

        assertNotNull(createdTicket);
        assertNotEquals(0, createdTicket.getId());

        Map<Long, Ticket> getTicket = objTicketOperation.getTicketById(0);
        assertTrue(getTicket.isEmpty());
    }

    @Test
    public void testTicketDeleteById(){
        TicketOperations objTicketOperation = new TicketOperations();
        Ticket createdTicket = objTicketOperation.createTicket(subject,agent_name, hashTags);

        assertNotNull(createdTicket);
        assertNotEquals(0, createdTicket.getId());

        objTicketOperation.deleteTicketById((int) createdTicket.getId());

        Map<Long, Ticket> getTicket = objTicketOperation.getTicketById((int)createdTicket.getId());
        assertTrue(getTicket.isEmpty());

    }


    @Test
    public void testTicketDeleteByInvalidId(){
        TicketOperations objTicketOperation = new TicketOperations();
        Ticket createdTicket = objTicketOperation.createTicket(subject,agent_name, hashTags);

        assertNotNull(createdTicket);
        assertNotEquals(0, createdTicket.getId());

        objTicketOperation.deleteTicketById(0);

        Map<Long, Ticket> getTicket = objTicketOperation.getTicketById((int)createdTicket.getId());
        assertFalse(getTicket.isEmpty());  // Not deleted
    }


    @Test
    public void testTicketSerachByAgentName(){
        TicketOperations objTicketOperation = new TicketOperations();
        Ticket createdTicket = objTicketOperation.createTicket(subject,agent_name, hashTags);

        assertNotNull(createdTicket);
        assertNotEquals(0, createdTicket.getId());

        Map<Long, Ticket> searchedTickets = objTicketOperation.searchTicketsWithAgent("Agent1");
        assertNotNull(searchedTickets);
        assertFalse(searchedTickets.isEmpty());
        assertEquals("Agent1", searchedTickets.get(createdTicket.getId()).getAgentName());
    }


    @Test
    public void testTicketSerachByInvalidAgentName(){
        TicketOperations objTicketOperation = new TicketOperations();
        Ticket createdTicket = objTicketOperation.createTicket(subject,agent_name, hashTags);

        assertNotNull(createdTicket);
        assertNotEquals(0, createdTicket.getId());

        Map<Long, Ticket> searchedTickets = objTicketOperation.searchTicketsWithAgent("Agent5");
        assertNotNull(searchedTickets);
        assertTrue(searchedTickets.isEmpty());

        if(!searchedTickets.isEmpty())
            assertNotEquals("Agent1", searchedTickets.get(0).getAgentName());
    }


    @Test
    public void testTicketsSearchByTag(){
        TicketOperations objTicketOperation = new TicketOperations();
        Ticket createdTicket = objTicketOperation.createTicket(subject,agent_name, hashTags);

        assertNotNull(createdTicket);
        assertNotEquals(0, createdTicket.getId());

        Set<String> searchTag = new HashSet<>(Arrays.asList("Tag1","Tag2"));

        Map<Long, Ticket> searchedTickets = objTicketOperation.searchTicketsWithTags(searchTag);

        assertNotNull(searchedTickets);
        assertFalse(searchedTickets.isEmpty());
        assertTrue(searchedTickets.get(createdTicket.getId()).getTags().contains("Tag1"));

    }


    @Test
    public void testTicketsSearchByInvalidTag(){
        TicketOperations objTicketOperation = new TicketOperations();
        Ticket createdTicket = objTicketOperation.createTicket(subject,agent_name, hashTags);

        assertNotNull(createdTicket);
        assertNotEquals(0, createdTicket.getId());

        Set<String> searchTag = new HashSet<>(Arrays.asList("InvalidTag"));

        Map<Long, Ticket> searchedTickets = objTicketOperation.searchTicketsWithTags(searchTag);

        assertNotNull(searchedTickets);
        assertTrue(searchedTickets.isEmpty());

    }


    @Test
    public void testAgentsTicketCount(){
        TicketOperations objTicketOperation = new TicketOperations();
        Ticket createdTicket = objTicketOperation.createTicket(subject,agent_name, hashTags);

        assertNotNull(createdTicket);
        assertNotEquals(0, createdTicket.getId());

        Map<String, List<Ticket>> agentTicketsCount = objTicketOperation.calculateAgentTicketCount();

        assertNotNull(agentTicketsCount);
        assertTrue(agentTicketsCount.containsKey("Agent1"));
        assertEquals(agentTicketsCount.values().size(), agentTicketsCount.get("Agent1").size());
    }


    @Test
    public void testgetAllTickets(){
        TicketOperations objTicketOperation = new TicketOperations();
        Ticket createdTicket = objTicketOperation.createTicket(subject,agent_name, hashTags);

        assertNotNull(createdTicket);
        assertNotEquals(0, createdTicket.getId());

        Map<Long, Ticket> tickets= objTicketOperation.getAllTicket();

        assertNotNull(tickets);
        assertTrue(tickets.get(createdTicket.getId()) instanceof Ticket);
    }

}