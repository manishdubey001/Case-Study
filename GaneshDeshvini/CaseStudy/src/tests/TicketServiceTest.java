package tests;

import data.Repository;
import factory.TicketServiceFactory;
import helpers.Util;
import model.TicketModel;
import org.junit.runners.MethodSorters;
import service.TicketService;

import org.junit.*;

import java.util.*;

import static org.junit.Assert.*;

/**
 * Created by root on 19/1/16.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TicketServiceTest {

    TicketService ts = TicketServiceFactory.getInstance();
    int id;
    String subject;
    String agent;
    String tags;
    TicketModel ticketModel;

    @Test
    public void test1CreateTicketValid() throws Exception {
        id = 1;
        subject = "sub";
        agent = "a1";
        tags = "a, b";
        HashSet<String> hsTags = new HashSet<>(Arrays.asList("a", "b"));
        assertTrue(ts.createTicket(id, subject, agent, tags));

        //validate data which has been saved
        ticketModel = ts.getTicketDetail(id);
        assertEquals(subject, ticketModel.getSubject());
        assertEquals(agent, ticketModel.getAgentName());
        assertEquals(hsTags, ticketModel.getTags());

        //cleanup
//        ts.deleteTicket(id);
    }

    @Test
    public void test2CreateTicketDuplicateId() throws Exception {
        //insert a dummy re
        id = 2;
        subject = "sub2";
        agent = "a2";
        tags = "d, b";
        assertTrue(ts.createTicket(id, subject, agent, tags));

        //duplicate id
        id = 2;
        subject = "sub3";
        agent = "a3";
        tags = "d, b";
        assertFalse(ts.createTicket(id, subject, agent, tags));

        //cleanup
//        ts.deleteTicket(id);
    }

    @Test
    public void test3CreateTicketNullSubject() throws Exception {
        id = 3;
        subject = null;
        agent = "a2";
        tags = "a";
        assertFalse(ts.createTicket(id, subject, agent, tags));

        //cleanup
//        ts.deleteTicket(id);
    }

    @Test
    public void test4CreateTicketNullAgentName() throws Exception {
        id = 4;
        subject = "sub";
        agent = null;
        tags = "";
        assertFalse(ts.createTicket(id, subject, agent, tags));

        //cleanup
//        ts.deleteTicket(id);
    }

    //
    @Test
    public void test5CreateTicketNullTags() throws Exception {
        id = 5;
        subject = "sub";
        agent = "a1";
        tags = null;
        assertTrue(ts.createTicket(id, subject, agent, tags));

        ticketModel = ts.getTicketDetail(id);
        assertNull(ticketModel.getTags());

        //cleanup
//        ts.deleteTicket(id);
    }

    @Test
    public void test6CreateTicketNegativeId() throws Exception {
        id = -1;
        subject = "sub";
        agent = "a1";
        tags = null;
        assertFalse(ts.createTicket(id, subject, agent, tags));

        //cleanup
//        ts.deleteTicket(id);
    }

    @Test
    public void test7UpdateTicketValid() throws Exception {
        id = 6;
        subject = "sub6";
        agent = "a6";
        tags = "a6, b6";
        HashSet<String> hsTags = new HashSet<>(Arrays.asList("a6", "b6"));
        assertTrue(ts.createTicket(id, subject, agent, tags));

        //validate data which has been saved
        ticketModel = ts.getTicketDetail(id);
        assertEquals(subject, ticketModel.getSubject());
        assertEquals(agent, ticketModel.getAgentName());
        assertEquals(hsTags, ticketModel.getTags());

        //test update ticket
        agent = "a6 updated";
        tags = "a6updated, b6updated";
        hsTags = new HashSet<>(Arrays.asList("a6updated", "b6updated"));
        assertTrue(ts.updateTicket(id, agent, tags));

        //validate data which has been saved
        ticketModel = ts.getTicketDetail(id);
        assertEquals(subject, ticketModel.getSubject());
        assertEquals(agent, ticketModel.getAgentName());
        assertEquals(hsTags, ticketModel.getTags());

        //cleanup
//        ts.deleteTicket(id);
    }

    @Test
    public void test8UpdateTicketInvalidId() throws Exception {
        id = 7;
        subject = "sub7";
        agent = "a7";
        tags = "a7, b7";
        HashSet<String> hsTags = new HashSet<>(Arrays.asList("a7", "b7"));
        assertTrue(ts.createTicket(id, subject, agent, tags));

        //validate data which has been saved
        ticketModel = ts.getTicketDetail(id);
        assertEquals(subject, ticketModel.getSubject());
        assertEquals(agent, ticketModel.getAgentName());
        assertEquals(hsTags, ticketModel.getTags());

        //test update ticket negative id
        id = -3;
        agent = "a7 updated";
        tags = "a7updated, b7updated";
        assertFalse(ts.updateTicket(id, agent, tags));

        //test update ticket invalid id
        id = 1000;
        agent = "a7 updated";
        tags = "a7updated, b7updated";
        assertFalse(ts.updateTicket(id, agent, tags));

        //cleanup
//        ts.deleteTicket(7);
    }

    @Test
    public void test90DeleteTicketValid() {
        id = 8;
        subject = "sub8";
        agent = "a8";
        tags = "a8, b8";
        HashSet<String> hsTags = new HashSet<>(Arrays.asList("a8", "b8"));
        assertTrue(ts.createTicket(id, subject, agent, tags));

        //validate data which has been saved
        ticketModel = ts.getTicketDetail(id);
        assertEquals(subject, ticketModel.getSubject());
        assertEquals(agent, ticketModel.getAgentName());
        assertEquals(hsTags, ticketModel.getTags());

        //delete ticket
        assertTrue(ts.deleteTicket(id));
        //cross check if deleted or not
        assertNull(ts.getTicketDetail(id));

//        //cleanup
//        ts.deleteTicket(id);
    }

    @Test
    public void test91DeleteTicketInvalidId() throws Exception {
        id = 9;
        subject = "sub7";
        agent = "a7";
        tags = "a7, b7";
        HashSet<String> hsTags = new HashSet<>(Arrays.asList("a7", "b7"));
        assertTrue(ts.createTicket(id, subject, agent, tags));

        //validate data which has been saved
        ticketModel = ts.getTicketDetail(id);
        assertEquals(subject, ticketModel.getSubject());
        assertEquals(agent, ticketModel.getAgentName());
        assertEquals(hsTags, ticketModel.getTags());

        //test update ticket negative id
        id = -3;
        agent = "a7 updated";
        tags = "a7updated, b7updated";
        assertFalse(ts.deleteTicket(id));

        //test update ticket invalid id
        id = 1000;
        agent = "a7 updated";
        tags = "a7updated, b7updated";
        assertFalse(ts.deleteTicket(id));

//        //cleanup
//        ts.deleteTicket(9);
    }

    @Test
    public void test92GetTicketDetailValid() throws Exception {
        id = 10;
        subject = "sub10";
        agent = "a10";
        tags = "a10, b10";
        HashSet<String> hsTags = new HashSet<>(Arrays.asList("a10", "b10"));
        assertTrue(ts.createTicket(id, subject, agent, tags));

        //validate data which has been saved
        ticketModel = ts.getTicketDetail(id);
        assertNotNull(ticketModel);

        assertEquals(id, ticketModel.getId());
        assertEquals(subject, ticketModel.getSubject());
        assertEquals(agent, ticketModel.getAgentName());
        assertEquals(hsTags, ticketModel.getTags());
//
//        //cleanup
//        ts.deleteTicket(id);
    }

    @Test
    public void test93GetTicketDetailInValidId() throws Exception {
        id = 11;
        subject = "sub10";
        agent = "a10";
        tags = "a10, b10";
        HashSet<String> hsTags = new HashSet<>(Arrays.asList("a10", "b10"));
        assertTrue(ts.createTicket(id, subject, agent, tags));

        //negative
        id = -1;
        assertNull(ts.getTicketDetail(id));

        //no record found
        id = 1000000;
        assertNull(ts.getTicketDetail(id));
//
//        //cleanup
//        ts.deleteTicket(11);
    }

    @Test
    public void test94GetTicketListValid() throws Exception {
        HashMap<Integer, HashMap<String, Object>> hmTicketData = new HashMap<Integer, HashMap<String, Object>>();
        Repository.getInstance().ticketData = new HashMap<Integer, TicketModel>();
        for (int i = 1; i <= 10; i++) {
            id = i;
            subject = "sub" + i;
            agent = "agent" + i;
            tags = "tag" + i;
            HashSet<String> hsTags = new HashSet<String>();
            hsTags.add(tags);

            HashMap<String, Object> hmData = new HashMap<String, Object>();
            hmData.put("id", id);
            hmData.put("subject", subject);
            hmData.put("agent", agent);
            hmData.put("tags", hsTags);
            hmTicketData.put(id, hmData);
            assertTrue(ts.createTicket(id, subject, agent, tags));
        }

        int cnt = 0;
        List<TicketModel> ls = ts.getTicketList();
        if (Util.isCollectionValid(ls)) {
            for (TicketModel tm : ls) {
                int tid = tm.getId();

                assertEquals(hmTicketData.get(tid).get("subject").toString(), tm.getSubject());
                assertEquals(hmTicketData.get(tid).get("agent").toString(), tm.getAgentName());
                assertEquals((HashSet<String>) hmTicketData.get(tid).get("tags"), tm.getTags());
                cnt++;
            }
        }
        assertEquals(10, cnt);
    }

    int getRandomNumber(int low, int high) {
        Random r = new Random();
        return r.nextInt(high - low) + low;
    }

    @Test
    public void test95GetTicketListAgentName() throws Exception {
        HashMap<String, HashSet<Integer>> hmTicketData = new HashMap<String, HashSet<Integer>>();
        Repository.getInstance().ticketData = new HashMap<Integer, TicketModel>();
        String chkAgentName = "";
        for (int i = 1; i <= 10; i++) {
            int randomNumber = getRandomNumber(1, 5);
            id = i;
            subject = "sub" + i;
            agent = "agent" + randomNumber;
            tags = "tag" + i;
            chkAgentName = agent;
            HashSet<String> hsTags = new HashSet<String>();
            hsTags.add(tags);

            HashSet<Integer> hs = hmTicketData.containsKey(agent) ? hmTicketData.get(agent) : new HashSet<Integer>();
            hs.add(id);
            hmTicketData.put(agent, hs);
            assertTrue(ts.createTicket(id, subject, agent, tags));
        }

        List<TicketModel> ls = ts.findAllTicketsByAgentName(chkAgentName);
        HashSet<Integer> hashSetActual = new HashSet<Integer>();
        if (Util.isCollectionValid(ls)) {
            for (TicketModel tm : ls) {
                int tid = tm.getId();
                hashSetActual.add(tid);
            }
        }

        HashSet<Integer> hashSetExpected = hmTicketData.get(chkAgentName);
        assertEquals(hashSetExpected, hashSetActual);
    }
}