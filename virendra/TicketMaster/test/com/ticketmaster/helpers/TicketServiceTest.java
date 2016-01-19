package com.ticketmaster.helpers;

import com.ticketmaster.Main;
import com.ticketmaster.exceptions.TicketNotFoundException;
import com.ticketmaster.models.Ticket;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static junit.framework.Assert.*;
import static junit.framework.TestCase.assertNull;

/**
 * Created by root on 19/1/16.
 */
public class TicketServiceTest {

    TicketService service;

    @Before
    public void beforeMethodStart(){
        Main.collectionChoice = 1; //using HashMap to setup test
        service = new TicketService();

    }


    /* ========================= Create Ticket Test cases ========================= */

    /**
     * testCreateTicketWithNoData test method
     * does not supply any data to ticket model
     */
    @Test
    public void testCreateTicketWithNoData() {

        Ticket object = null;
        final String txtSubject = null;
        final String txtAgent   = null;
        final Set tagsSet       = null;

        try{
            object = service.createTicket(txtSubject, txtAgent, tagsSet);
        }catch (TicketNotFoundException | IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
        //adding asserts
        //must return null
        assertNull(object);
    }

    /**
     * testCreateTicketWithSubject test method
     * supply only subject
     */
    @Test
    public void testCreateTicketWithSubject() {

        Ticket object = null;
        final String txtSubject = "testSubject";
        final String txtAgent   = null;
        final Set tagsSet       = null;

        try{
            object = service.createTicket(txtSubject, txtAgent, tagsSet);
        }catch (TicketNotFoundException | IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
        //adding asserts
        //must return null
        assertNull(object);
    }

    /**
     * testCreateTicketWithAgent test method
     * supply only agent value
     */
    @Test
    public void testCreateTicketWithAgent() {

        Ticket object = null;
        final String txtSubject = null;
        final String txtAgent   = "testAgent";
        final Set tagsSet       = null;

        try{
            object = service.createTicket(txtSubject, txtAgent, tagsSet);
        }catch (TicketNotFoundException | IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
        //adding asserts
        //must return null
        assertNull(object);
    }

    /**
     * testCreateTicketWithTags test method
     * supply only tags
     */
    @Test
    public void testCreateTicketWithTags() {

        Ticket object = null;
        final String txtSubject = null;
        final String txtAgent   = null;
        Set tagsSet       = new HashSet();
        tagsSet.add("Tag1");
        tagsSet.add("Tag2");


        try{
            object = service.createTicket(txtSubject, txtAgent, tagsSet);
        }catch (TicketNotFoundException | IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
        //adding asserts
        //must return null
        assertNull(object);
    }

    /**
     * testCreateTicketWithSubjectAgent test method
     * supply subject and agent
     */
    @Test
    public void testCreateTicketWithSubjectAgent() {

        Ticket object = null;
        final String txtSubject = "testSubject";
        final String txtAgent   = "testAgent";
        final Set tagsSet       = null;

        try{
            object = service.createTicket(txtSubject, txtAgent, tagsSet);
        }catch (TicketNotFoundException | IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
        //adding asserts
        //must return ticket object
        assertNotNull(object);
        assertEquals(txtSubject, object.getSubject());
        assertEquals(txtAgent, object.getAgent());
        assertTrue(object.tags.isEmpty());

        //after all details are verified
        //delete crated ticket entry
        object.deleteTicket(object.getId());
    }

    /**
     * testCreateTicketWithAgentTags test method
     * supply agent and tag
     */
    @Test
    public void testCreateTicketWithAgentTags() {

        Ticket object = null;
        final String txtSubject = null;
        final String txtAgent   = "testAgent";
        Set tagsSet       = new HashSet();
        tagsSet.add("Tag1");
        tagsSet.add("Tag2");

        try{
            object = service.createTicket(txtSubject, txtAgent, tagsSet);
        }catch (TicketNotFoundException | IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
        //adding asserts
        //must return null
        assertNull(object);
    }

    /**
     * testCreateTicketWithSubjectTags test method
     * supply subject and tag
     */
    @Test
    public void testCreateTicketWithSubjectTags() {

        Ticket object = null;
        final String txtSubject = "testSubject";
        final String txtAgent   = null;
        Set tagsSet       = new HashSet();
        tagsSet.add("Tag1");
        tagsSet.add("Tag2");

        try{
            object = service.createTicket(txtSubject, txtAgent, tagsSet);
        }catch (TicketNotFoundException | IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
        //adding asserts
        //must return null
        assertNull(object);
    }

    /**
     * testCreateTicketWithAllInput test method
     * supply all input data
     */
    @Test
    public void testCreateTicketWithAllInput() {

        Ticket object = null;
        final String txtSubject = "testSubject";
        final String txtAgent   = "testAgent";
        Set tagsSet       = new HashSet();
        tagsSet.add("Tag1");
        tagsSet.add("Tag2");

        try{
            object = service.createTicket(txtSubject, txtAgent, tagsSet);
        }catch (TicketNotFoundException | IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
        //adding asserts
        //must return ticket object
        assertNotNull(object);
        assertEquals(txtSubject, object.getSubject());
        assertEquals(txtAgent, object.getAgent());
        assertTrue(object.tags.contains("Tag1"));
        //after all details are verified
        //delete crated ticket entry
        object.deleteTicket(object.getId());

    }
    /**
     * testCreateTicketWithTreeMapAllData test method with TreeMap
     * supply all data
     */
    @Test
    public void testCreateTicketWithTreeMapAllData(){

        Main.collectionChoice = 2;
        Ticket object = null;
        final String txtSubject = "testSubject";
        final String txtAgent   = "testAgent";
        Set tagsSet       = new HashSet();
        tagsSet.add("testTag");

        try{
            object = service.createTicket(txtSubject, txtAgent, tagsSet);
        }catch (TicketNotFoundException | IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
        //adding asserts
        //must return ticket object
        assertNotNull(object);
        assertEquals(txtSubject, object.getSubject());
        assertEquals(txtAgent, object.getAgent());
        assertTrue(object.tags.contains("testTag"));
        //after all details are verified
        //delete crated ticket entry
        object.deleteTicket(object.getId());
    }
    /**
     * testCreateTicketWithLinkedHashMapAllData test method with LinkedHashMap
     * supply all data
     */
    @Test
    public void testCreateTicketWithLinkedHashMapAllData(){

        Main.collectionChoice = 3;
        Ticket object = null;
        final String txtSubject = "testSubject";
        final String txtAgent   = "testAgent";
        Set tagsSet       = new HashSet();
        tagsSet.add("Tag1");
        tagsSet.add("Tag2");

        try{
            object = service.createTicket(txtSubject, txtAgent, tagsSet);
        }catch (TicketNotFoundException | IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
        //adding asserts
        //must return ticket object
        assertNotNull(object);
        assertEquals(txtSubject, object.getSubject());
        assertEquals(txtAgent, object.getAgent());
        assertTrue(object.tags.contains("Tag1"));
        //after all details are verified
        //delete crated ticket entry
        object.deleteTicket(object.getId());
    }

    /* ========================= Update Ticket Test cases ========================= */
    /**
     * testUpdateTicketWithNoData test method to test update
     * no data is supplied
     */
    @Test
    public void testUpdateTicketWithNoData(){

        Ticket object = null,
                result = null;
        final String txtAgent   = null;
        final Set tagsSet       = null;

        //create dummy entry
        try{
            object = service.createTicket("test-subject", "test-agent",null);

            //now try to update the record with no data and collect output
            result = service.updateTicket(object, txtAgent, tagsSet);

        }catch (TicketNotFoundException | IOException | ClassNotFoundException e){
            e.printStackTrace();
        }

        //adding asserts
        //must return null
        assertNull(result);
        assertFalse(object.equals(result));

        //after test is done delete the dummy ticket
        object.deleteTicket(object.getId());

    }

    /**
     * testUpdateTicketWithAgent test method to test update
     * agent name is supplied
     */
    @Test
    public void testUpdateTicketWithAgent(){

        Ticket object = null,
                result = null;
        final String txtAgent   = "new-agent name";
        final Set tagsSet       = null;

        //create dummy entry
        try{
            object = service.createTicket("test-subject", "test-agent",null);
            //now try to update the record with no data and collect output
            result = service.updateTicket(object, txtAgent, tagsSet);

        }catch (TicketNotFoundException | IOException | ClassNotFoundException e){
            e.printStackTrace();
        }

        //adding asserts
        //must return updated ticket object

        assertNotNull(result);
        assertEquals(object.hashCode(), result.hashCode()); // compare hash

        assertEquals(object.getId(), result.getId());
        assertEquals(txtAgent, result.getAgent()); //check if tag is updated

        //after test is done delete the dummy ticket
        object.deleteTicket(object.getId());

    }
    /**
     * testUpdateTicketWithTags test method
     * supply tags
     */
    @Test
    public void testUpdateTicketWithTags(){

        Ticket object = null,
                result = null;
        final String txtAgent   = "new-agent name";
        Set tagsSet = new HashSet<>();
        tagsSet.add("testTag");

        //create dummy entry
        try{
            object = service.createTicket("test-subject", "test-agent",null);
            //now try to update the record with no data and collect output
            result = service.updateTicket(object, txtAgent, tagsSet);

        }catch (TicketNotFoundException | IOException | ClassNotFoundException e){
            e.printStackTrace();
        }

        //adding asserts
        //must return updated ticket object

        assertNotNull(result);
        assertEquals(object.hashCode(), result.hashCode()); // compare hash

        assertEquals(object.getId(), result.getId());
        assertEquals(txtAgent, result.getAgent()); //check if agent is updated
        assertTrue(result.tags.contains( ("testTag" ) ) ); //check if tag is updated

        //after test is done delete the dummy ticket
        object.deleteTicket(object.getId());

    }
    /**
     * testUpdateTicketWithAgentAndTags test method
     * supply both agent and tags
     */
    @Test
    public void testUpdateTicketWithAgentAndTags(){

        Ticket object = null,
                result = null;
        final String txtAgent   = null;
        Set tagsSet = new HashSet<>();
        tagsSet.add("testTag");

        //create dummy entry
        try{
            object = service.createTicket("test-subject", "test-agent",null);
            //now try to update the record with no data and collect output
            result = service.updateTicket(object, txtAgent, tagsSet);

        }catch (TicketNotFoundException | IOException | ClassNotFoundException e){
            e.printStackTrace();
        }

        //adding asserts
        //must return updated ticket object

        assertNotNull(result);
        assertEquals(object.hashCode(), result.hashCode()); // compare hash

        assertEquals(object.getId(), result.getId());
        assertTrue(result.tags.contains( ("testTag" ) ) ); //check if agent is updated

        //after test is done delete the dummy ticket
        object.deleteTicket(object.getId());

    }

    /* ========================= Get Ticket Test cases ========================= */
    /**
     * testGetTicketWIthInvalidId test method
     * supply random invalid id
     */
    @Test
    public void testGetTicketWIthInvalidId(){

        Ticket object = null,
                result = null;
        int randomId= 0;


        //create dummy entry
        try{
            object = service.createTicket("test-subject", "test-agent",null);
            //now try to get the value from list with random id
            randomId = object.getId()+(int)(Math.random()*10);
            result = service.getTicketDetail(randomId);

        }catch (TicketNotFoundException | IOException | ClassNotFoundException e){
            e.printStackTrace();
        }

        //adding asserts
        //must return updated ticket object

        assertNull(result);
        //after test is done delete the dummy ticket
        object.deleteTicket(object.getId());

    }

}
