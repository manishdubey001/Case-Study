package test.factory;
/*

import com.customexceptions.UserInputException;
import com.factory.TicketFactory;
import com.model.Ticket;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

*/
/**
 * Created by root on 20/1/16.
 *//*

public class TestTicketFactory {

    @Test
    public void testgetTickretInstance(){
        Set<String> tagsSet = new HashSet<>(Arrays.asList("Tag1", "Tag2"));
        Ticket ticket = null;
        try {
            ticket = TicketFactory.createTicketInstance("Subject1", "Agent1", tagsSet);
        } catch (UserInputException e) {
            e.printStackTrace();
        }

        assertNotNull(ticket);
        assertTrue(ticket instanceof Ticket);
        assertEquals("Agent1", ticket.getAgentName());
        assertEquals("Subject1", ticket.getSubject());
        assertEquals(tagsSet, ticket.getTags());
    }
}
*/
