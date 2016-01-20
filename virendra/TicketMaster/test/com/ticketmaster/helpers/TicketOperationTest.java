package com.ticketmaster.helpers;

import com.ticketmaster.Main;
import com.ticketmaster.exceptions.TicketNotFoundException;
import com.ticketmaster.models.Ticket;
import com.ticketmaster.utils.AppUtil;
import org.junit.*;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

/**
 * TicketOperationTest class
 * Used to test the operations on the the Tickets
 * Created by Virendra on 20/1/16.
 */
public class TicketOperationTest {

    Ticket ticket;
    static TicketService service = null;
    public static final String subject = "test-subject";
    public static final String agent = "a-5";
    public static final String tagName = "testTag1";
    public static List tagSet = Arrays.asList(tagName,"testTag2");
    public static int size = 6;
    public static int agents = 6;

    @BeforeClass
    public static void setUp(){

        try{
            AppUtil.initializeApp(size, agents);
            service = new TicketService();
        } catch (TicketNotFoundException | IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }


    }

    @Before
    public void setUpBeforeMethod(){
        Main.collectionChoice = 1;
        Set<String> dummyTagSet =  new HashSet<>();
        dummyTagSet.addAll(tagSet);

        try{
            ticket = service.createTicket(subject, agent,dummyTagSet);
//        ticket = new Ticket.TicketBuilder().withSubject(subject).withAgent(agent).withTags(tagSet).build();
        } catch (TicketNotFoundException | IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    @After
    public void cleanUpAfterMethod(){
        try{
            service.deleteTicket(ticket.getId());
        } catch (TicketNotFoundException | IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    @AfterClass
    public static void cleanUp(){
        service.clearList();
    }


     /* ========================= Agent search Test cases ========================= */
    /**
     * testInvalidAgentSearch test method to test the agent in the ticket list
     */
    @Test
    public void testInvalidAgentSearch(){

        String invalidAgent = "testAgentName";
        List tempList = service.searchTicket("agent", invalidAgent);
        Set resultSet = new HashSet();

        //received result, get all agents from the list


        tempList.stream().forEach( e -> {
            Map temp = (HashMap) e;
            resultSet.add(temp.get("agent").toString());

        } );

        assertTrue(resultSet.size() == 0);

    }
    /**
     * testValidAgentSearch method to find the agent in the list
     */
    @Test
    public void testValidAgentSearch(){

        List tempList = service.searchTicket("agent", agent);
        //received result, get all agents from the list
        assertTrue(tempList.size() >=1);
    }


     /* ========================= Tag search Test cases ========================= */
    /**
     * testInvalidTagSearch test method to test the agent in the ticket list
     */
    @Test
    public void testInvalidTagSearch(){

        String invalidTag = "testTagName";

        List tempList = service.searchTicket("tags", invalidTag);
        Set resultSet = new HashSet();

        //received result, get all tags from the list

        tempList.stream().forEach( (e)-> {
            Map temp = (HashMap) e;
            resultSet.addAll((Set) temp.get("tags"));

        } );

        assertTrue(resultSet.size() == 0);

    }
    /**
     * testValidAgentSearch method to find the agent in the list
     */
    @Test
    public void testValidTagSearch(){

        List tempList = service.searchTicket("tags", tagName);

        assertTrue(tempList.size() >=1);
    }

    /* ========================= Ticket count by agent ========================= */

    /**
     * testTicketCountByAgents test method
     */
    @Test
    public void testTicketCountByAgents(){

        Map tempList = service.getTicketCount();

        long total = 0;
        Iterator it = tempList.entrySet().iterator();

        while (it.hasNext()){
            Map.Entry e =(Map.Entry) it.next();
            total += Integer.parseInt(e.getValue().toString());
        }
        assertTrue(tempList.size() >= agents); //size of agents would be total to number of agents resulted
        assertEquals(Ticket.getSize(), total); // check if total of agents is the test record added

    }
    /* ========================= Complete Ticket count ========================= */
    /**
     * testTicketCountAll test method
     */
    @Test
    public void testTicketCountAll(){

        List tempList = service.getTickets();

        assertEquals(tempList.size(), size +1); //size+1 is because one entry is made before test starts

    }

}
