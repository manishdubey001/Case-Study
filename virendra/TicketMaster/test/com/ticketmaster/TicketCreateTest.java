package com.ticketmaster;

import com.ticketmaster.exceptions.TicketNotFoundException;
import com.ticketmaster.helpers.TicketService;
import com.ticketmaster.models.Ticket;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertNull;

/**
 * Created by root on 19/1/16.
 */
public class TicketCreateTest {

    @Before
    public void beforeMethodStart(){
        Main.collectionChoice = 1; //using HashMap to setup test
    }

    /**
     * createTicketWithNoData test method
     * does not supply any data to ticket model
     */
    @Test
    public void createTicketWithNoData() {

        TicketService service = new TicketService();
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
     * createTicketWithSubject test method
     * supply only subject
     */
    @Test
    public void createTicketWithSubject() {

        TicketService service = new TicketService();
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
     * createTicketWithAgent test method
     * supply only agent value
     */
    @Test
    public void createTicketWithAgent() {

        TicketService service = new TicketService();
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
     * createTicketWithTags test method
     * supply only tags
     */
    @Test
    public void createTicketWithTags() {

        TicketService service = new TicketService();
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
     * createTicketWithAllInput test method
     * supply all input data
     */
    @Test
    public void createTicketWithAllInput() {

        TicketService service = new TicketService();
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



}
