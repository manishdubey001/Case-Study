package com.ticketmaster.helpers;

import com.ticketmaster.Main;
import com.ticketmaster.exceptions.TicketNotFoundException;
import com.ticketmaster.models.Ticket;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static junit.framework.TestCase.*;

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
    public void testCreateTicketWithSubjectAgent() throws IOException, ClassNotFoundException{

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
        object.delete();
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
    public void testCreateTicketWithAllInput() throws IOException, ClassNotFoundException{

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
        object.delete();

    }
    /**
     * testCreateTicketWithTreeMapAllData test method with TreeMap
     * supply all data
     */
    @Test
    public void testCreateTicketWithTreeMapAllData()throws IOException, ClassNotFoundException{

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
        object.delete();
    }
    /**
     * testCreateTicketWithLinkedHashMapAllData test method with LinkedHashMap
     * supply all data
     */
    @Test
    public void testCreateTicketWithLinkedHashMapAllData()throws IOException, ClassNotFoundException{
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
        object.delete();
    }

    /* ========================= Update Ticket Test cases ========================= */
    /**
     * testUpdateTicketWithNoData test method to test update
     * no data is supplied
     */
    @Test
    public void testUpdateTicketWithNoData()throws IOException, ClassNotFoundException{

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
        object.delete();

    }

    /**
     * testUpdateTicketWithAgent test method to test update
     * agent name is supplied
     */
    @Test
    public void testUpdateTicketWithAgent()throws IOException, ClassNotFoundException{

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
        object.delete();

    }
    /**
     * testUpdateTicketWithTags test method
     * supply tags
     */
    @Test
    public void testUpdateTicketWithTags()throws IOException, ClassNotFoundException{

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
        object.delete();

    }
    /**
     * testUpdateTicketWithAgentAndTags test method
     * supply both agent and tags
     */
    @Test
    public void testUpdateTicketWithAgentAndTags()throws IOException, ClassNotFoundException{

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
        object.delete();

    }

    /* ========================= Get Ticket Detail Test cases ========================= */
    /**
     * testGetTicketWIthInvalidId test method
     * supply random invalid id
     */
    @Test
    public void testGetTicketWithInvalidId()throws IOException, ClassNotFoundException{

        Ticket object = null;
        Map result = null;
        int randomId = 0;
        String exceptionMessage=null, expected= null;

        //create dummy entry
        try{
            object = service.createTicket("test-subject", "test-agent",null);
            //now try to get the value from list with random id
            randomId = -1;
            result = service.getTicket(randomId);

        }catch (TicketNotFoundException | IOException | ClassNotFoundException e){
            exceptionMessage = e.getMessage();
        }
        expected = "Record with id: "+randomId +" does not exists";

        //adding asserts
        //must return updated ticket object

        assertNull(result);
        assertTrue(exceptionMessage.equalsIgnoreCase(expected));
        //after test is done delete the dummy ticket
        object.delete();

    }

    /**
     * testGetTicketWithValidId test method
     * supply valid ticket id
     */
    @Test
    public void testGetTicketWithValidId()throws IOException, ClassNotFoundException{

        Ticket object = null;
        Map result = null;

        //create dummy entry
        try{
            object = service.createTicket("test-subject", "test-agent",null);
            //now try to get the value from list with random id
            result = service.getTicket(object.getId());

        }catch (TicketNotFoundException | IOException | ClassNotFoundException e){
            e.printStackTrace();
        }

        //adding asserts
        //must return updated ticket object

        assertNotNull(result);
        assertEquals(object.getAgent().toLowerCase(),result.get("agent"));
//        assertEquals(object.getSubject().toLowerCase(), result.getSubject().toLowerCase());
        assertEquals(object.getId(), result.get("id"));

        //after test is done delete the dummy ticket
        object.delete();

    }

    /**
     * testGetTicketObjectForId test method to check the result as ticket object
     */
    @Test
    public void testGetTicketObjectForId()throws IOException, ClassNotFoundException{

        Ticket object = null,result = null;
        int randomId = 0;
        String exceptionMessage=null;

        //create dummy entry
        try{
            object = service.createTicket("test-subject", "test-agent",null);
            //now try to get the value from list with random id
            randomId = object.getId();
            result = service.getTicketDetail(randomId);

        }catch (TicketNotFoundException | IOException | ClassNotFoundException e){
            exceptionMessage = e.getMessage();
        }

        //adding asserts
        //must return updated ticket object

        assertNotNull(result);
        assertEquals(object.hashCode(), result.hashCode());
        assertEquals(object.getSubject(), result.getSubject());
        //after test is done delete the dummy ticket
        object.delete();

    }
    /**
     * testGetTicketObjectForInvalidId test method
     */
    @Test
    public void testGetTicketObjectForInvalidId()throws IOException, ClassNotFoundException{

        Ticket object = null,result = null, result1= null;
        int randomId = 0;
        String exceptionMessage=null;

        //create dummy entry
        try{
            object = service.createTicket("test-subject", "test-agent",null);
            //now try to get the value from list with random id
            result = service.getTicketDetail(randomId); //fetch result for id 0

            randomId = -1;
            result1 = service.getTicketDetail(randomId); //fetch id for -1

        }catch (TicketNotFoundException | IOException | ClassNotFoundException e){
            exceptionMessage = e.getMessage();
        }

        assertNull(result);
        assertNull(result1);
        //after test is done delete the dummy ticket
        object.delete();

    }

    /* ========================= Delete Ticket Test cases ========================= */
    /**
     *
     */
    @Test
    public void testDeleteTicketForValidId(){

        Ticket object = null,result = null, result1 = null;
        int randomId = 0;
        String exceptionMessage=null;

        //create dummy entry
        try{
            object = service.createTicket("test-subject", "test-agent",null);
            //now try to get the value from list with random id
            randomId = object.getId();
            result = service.deleteTicket(randomId); //delete result

            //try to obtain the result from ticket list with same id
            result1 = service.getTicketDetail(randomId);

        }catch (TicketNotFoundException | IOException | ClassNotFoundException e){
            exceptionMessage = e.getMessage();
        }

        assertNull(result1);
        assertEquals(object.getId(), result.getId());
        assertEquals(object.hashCode(), result.hashCode());

    }

    /**
     *
     */
    @Test
    public void testDeleteTicketIdForInvalidId(){

        Ticket object = null,result  = null;
        int randomId = 0;
        String exceptionMessage=null, expected = null;

        //create dummy entry
        try{
            object = service.createTicket("test-subject", "test-agent",null);
            //now try to get the value from list with random id
            randomId = -1;
            result = service.deleteTicket(randomId); //fetch result for delete of id -1


        }catch (TicketNotFoundException | IOException | ClassNotFoundException e){
            exceptionMessage = e.getMessage();
        }
        expected = "Record with id: "+randomId +" does not exists";

        assertNull(result);
        assertTrue(exceptionMessage.equalsIgnoreCase(expected));

    }

    /**
     *
     */
    @Test
    public void testDeleteDeletedTicket(){

        Ticket object = null,result  = null, result1 = null;
        int randomId = 0;
        String exceptionMessage=null, expected = null;

        //create dummy entry
        try{
            object = service.createTicket("test-subject", "test-agent",null);
            //now try to get the value from list with random id
            randomId = object.getId();
            result = service.deleteTicket(randomId); //fetch result for delete of id

            //try to delete the same record again
            result1 = service.deleteTicket(randomId);


        }catch (TicketNotFoundException | IOException | ClassNotFoundException e){
            exceptionMessage = e.getMessage();
        }
        expected = "Record with id: "+randomId +" does not exists";

        assertNull(result1);
        assertTrue(exceptionMessage.equalsIgnoreCase(expected));
        assertNotNull(result);
        assertEquals(object.getId(), result.getId());
        assertEquals(object.hashCode(), result.hashCode());
    }


}
