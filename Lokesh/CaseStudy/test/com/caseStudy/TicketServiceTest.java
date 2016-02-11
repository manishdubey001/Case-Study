package com.caseStudy;

import com.casestudy.Service;
import com.casestudy.Ticket;
//import org.junit.Test;
import org.junit.*;
import org.junit.runners.MethodSorters;

import java.security.InvalidParameterException;
import java.util.*;

import static junit.framework.Assert.*;
//import static junit.framework.Assert

/**
 * Created by lokesh on 19/1/16.
 * Test File to unit test Core Service.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TicketServiceTest {
    /*final ArrayList<List<Object>> list= Arrays.asList(
            Arrays.asList("test-subject","test-agent", "tag1,tag2" )
    ) ;*/

//    final List list = Arrays.asList(1,2);
    static ArrayList<List<Object>> createList = new ArrayList<>(); //Holds List of objects to create Ticket with different values of attributes for each Ticket
    static ArrayList<List<Object>> updateList = new ArrayList<>(); //Holds List of objects to update ticket with different values of attributes for each Ticket
    static ArrayList<Integer> deleteList = new ArrayList<>(); // Holds the Integer ID's of Ticket to be deleted
    static ArrayList<Integer> getDetail = new ArrayList<>(); // Holds Integer ID's of Tickets to get Details Operation
    static ArrayList<String> getAgentTickets = new ArrayList<>(); // Holds Strings for Agent Name for which Ticket List to be fetched
    static ArrayList<String> tagReport = new ArrayList<>(); // Holds Strings of Tags for which Report is asked
//    Service s = new Service("resources/","data.txt");
    Service s = new Service("resources/","test.txt");
    @BeforeClass
    public static void setData(){

        List<Object> hs = new ArrayList<>(); //Holds object for createTicket, object contains data in order as: subject,agent,tags(HashSet)
        HashSet<String> hh = new HashSet<>(); //Tag Holder, to be added in "hs" for createTicket

        //Setting createList Data
        //All data for ticket, ticket created
        hs.add("subject1");
        hs.add("agent1");
        hh.add("t1");
        hh.add("t2");
        hs.add(hh);
        createList.add(hs);

        //Subject not available, ticket not created
        hs = new ArrayList<>();
        hs.add("");
        hs.add("a2");
        hh = new HashSet<>();
        hh.add("t2");
        hh.add("t3");
        hs.add(hh);
        createList.add(hs);

        //Agent not available, ticket not created
        hs = new ArrayList<>();
        hs.add("sub2");
        hs.add("");
        hh= new HashSet<>();
        hh.add("t4");
        hh.add("t5");
        hs.add(hh);
        createList.add(hs);

        //Tags not available, ticket created
        hs = new ArrayList<>();
        hs.add("sub3");
        hs.add("a3");
        hs.add(new HashSet<String>());
        createList.add(hs);

        //Setting updateList Data
        //hs= Holds object for update ticket, object contains data in order: id,new agent,updateTag("A" for Add "R" for remove), tags(HashSet) for A/R tags

        //Update agent and add new tags
        hs = new ArrayList<>();
        hs.add("1");
        hs.add("newAgentName");
        hs.add("A");
        hh = new HashSet<>();
        hh.add("newTag1");
        hh.add("newTag2");
        hs.add(hh);
        updateList.add(hs);

        //update agent and Remove tags
        hs = new ArrayList<>();
        hs.add("1");
        hs.add("newAgentName1");
        hs.add("R");
        hh = new HashSet<>();
        hh.add("newTag1");
        hh.add("newTag2");
        hs.add(hh);
        updateList.add(hs);

        //wrong/no id passed, nothing to update, return null
        hs = new ArrayList<>();
        hs.add("1234");
        hs.add("newAgentName");
        hs.add("A");
        hh = new HashSet<>();
        hh.add("newTag1");
        hh.add("newTag2");
        hs.add(hh);
        updateList.add(hs);

        // do not update agent, add new tags only
        hs = new ArrayList<>();
        hs.add("1");
        hs.add("");
        hs.add("A");
        hh = new HashSet<>();
        hh.add("newTag3");
        hh.add("newTag4");
        hs.add(hh);
        updateList.add(hs);

        //do not update agent, remove tags only
        hs = new ArrayList<>();
        hs.add("1");
        hs.add("");
        hs.add("R");
        hh = new HashSet<>();
        hh.add("newTag3");
        hh.add("newTag4");
        hs.add(hh);
        updateList.add(hs);

        //update agent, do not update tags
        hs = new ArrayList<>();
        hs.add("1");
        hs.add("newAgentName");
        hs.add("N");
        hh = new HashSet<>();
        hs.add(hh);
        updateList.add(hs);

        //do not update anything, but called update request ,updated time not changed
        hs = new ArrayList<>();
        hs.add("1");
        hs.add("");
        hs.add("N");
        hh = new HashSet<>();
        hs.add(hh);
        updateList.add(hs);

        //update with Same agent as assigned right now, do not update ticket
        hs = new ArrayList<>();
        hs.add("1");
        hs.add("newAgentName");
        hs.add("N");
        hh = new HashSet<>();
        hs.add(hh);
        updateList.add(hs);

        //add same tag applied right now, do not update agent
        hs = new ArrayList<>();
        hs.add("1");
        hs.add("");
        hs.add("A");
        hh = new HashSet<>();
        hh.add("newTag3");
        hh.add("newTag4");
        hs.add(hh);
        updateList.add(hs);

        //remove tags which are not applied, do not update agent name
        hs = new ArrayList<>();
        hs.add("1");
        hs.add("");
        hs.add("R");
        hh = new HashSet<>();
        hh.add("unavailableTag");
        hs.add(hh);
        updateList.add(hs);

        // Data Set for delete operation
        deleteList.add(1);
        deleteList.add(999);

        //Data for get ticket details
        getDetail.add(1); // This will be already deleted in delete test - case when coming to getDetail test case, so it will represent a wrong ID passed for getting detail.
        getDetail.add(2);

        //Data for Agents Ticket
        getAgentTickets.add("a3"); // This will be already deleted in delete test - case when coming to getDetail test case, so it will represent a wrong agent name passed.
        getAgentTickets.add("agent1");

        //Data for Tags report
        tagReport.add("t1");
        tagReport.add("invalid tag");
    }

// Below this section contains separate functions to test against individual data set one at a time.
    @Test (expected = InvalidParameterException.class)
    public void test111CreateWithEmptySubject(){
        List data = createList.get(1);
        assertNull(s.createTicket((String) data.get(0),(String)data.get(1),(HashSet<String>)data.get(2)));
    }

    @Test (expected = InvalidParameterException.class)
    public void test112CreateWithEmptyAgent(){
        List data = createList.get(2);
        assertNull(s.createTicket((String) data.get(0),(String)data.get(1),(HashSet<String>)data.get(2)));
    }

    @Test
    public void test113CreateWithEmptyTag(){
        List data = createList.get(3);
        Ticket t = s.createTicket((String) data.get(0),(String)data.get(1),(HashSet<String>)data.get(2));
//        System.out.println("First ticket" + t.toString());
        assertNotNull(t);
        assertEquals((String)data.get(0),t.getSubject());
        assertEquals((String)data.get(1),t.getAgent());
        assertTrue(t.getTags().containsAll((HashSet<String>)data.get(2)));
    }

    @Test
    public void test114CreateWithAllFields(){
        List data = createList.get(0);
        Ticket t = s.createTicket((String) data.get(0),(String)data.get(1),(HashSet<String>)data.get(2));
//        System.out.println("Second ticket" + t.toString());
        assertNotNull(t);
        assertEquals((String)data.get(0),t.getSubject());
        assertEquals((String)data.get(1),t.getAgent());
        assertTrue(t.getTags().containsAll((HashSet<String>)data.get(2)));
    }

    //Update agent and add new tags
    @Test
    public void test115UpdateWithAgentTagAdd(){
        List data = updateList.get(0);
        Ticket t = s.updateTicket(Integer.parseInt((String)data.get(0)),(String)data.get(1),(String)data.get(2),(HashSet<String>) data.get(3));
//        System.out.println("After first Update" + t.toString());
        assertNotNull(t);
        assertEquals(Integer.parseInt((String) data.get(0)),t.getId());
        assertNotNull(t.getAgent());
        assertEquals((String)data.get(1),t.getAgent());
        assertTrue(t.getTags().containsAll((HashSet<String>)data.get(3)));
    }

    //update agent and Remove tags
    @Test
    public void test116UpdateWithAgentTagRemove(){
        List data = updateList.get(1);
        Ticket t = s.updateTicket(Integer.parseInt((String)data.get(0)),(String)data.get(1),(String)data.get(2),(HashSet<String>) data.get(3));
//        System.out.println("After second Update" + t.toString());
        assertNotNull(t);
        assertEquals(Integer.parseInt((String) data.get(0)),t.getId());
        assertNotNull(t.getAgent());
        assertEquals((String)data.get(1),t.getAgent());
        ((HashSet<String>)data.get(3)).forEach((oo)->assertFalse(t.getTags().contains(oo)));
    }

    //wrong/no id passed, nothing to update, return null
    @Test (expected = InvalidParameterException.class)
    public void test117UpdateWithWrongId(){
        List data = updateList.get(2);
        Ticket t = s.updateTicket(Integer.parseInt((String)data.get(0)),(String)data.get(1),(String)data.get(2),(HashSet<String>) data.get(3));
        assertNull(t);
    }

    // do not update agent, add new tags only
    @Test
    public void test118UpdateTagAdd(){
        List data = updateList.get(3);
        Ticket t = s.updateTicket(Integer.parseInt((String)data.get(0)),(String)data.get(1),(String)data.get(2),(HashSet<String>) data.get(3));
//        System.out.println("After third Update" + t.toString());
        assertNotNull(t);
        assertEquals(Integer.parseInt((String) data.get(0)),t.getId());
        assertNotNull(t.getAgent());
        assertTrue(t.getTags().containsAll((HashSet<String>)data.get(3)));
    }

    //do not update agent, remove tags only
    @Test
    public void test119UpdateTagRemove(){
        List data = updateList.get(4);
        Ticket t = s.updateTicket(Integer.parseInt((String)data.get(0)),(String)data.get(1),(String)data.get(2),(HashSet<String>) data.get(3));
//        System.out.println("After forth Update" + t.toString());
        assertNotNull(t);
        assertEquals(Integer.parseInt((String) data.get(0)),t.getId());
        assertNotNull(t.getAgent());
        ((HashSet<String>)data.get(3)).forEach((oo)->assertFalse(t.getTags().contains(oo)));
    }

    //update agent, do not update tags
    @Test
    public void test120UpdateWithAgent(){
        List data = updateList.get(5);
        Ticket t = s.updateTicket(Integer.parseInt((String)data.get(0)),(String)data.get(1),(String)data.get(2),(HashSet<String>) data.get(3));
//        System.out.println("After fifth Update" + t.toString());
        assertNotNull(t);
        assertEquals(Integer.parseInt((String) data.get(0)),t.getId());
        assertNotNull(t.getAgent());
        assertEquals((String)data.get(1),t.getAgent());
    }

    //do not update anything, but called update request ,updated time not changed
    @Test
    public void test121UpdateWithNoting(){
        List data = updateList.get(6);
        Ticket old_t = s.getTicketById(Integer.parseInt((String)data.get(0)));
        Ticket t = s.updateTicket(Integer.parseInt((String)data.get(0)),(String)data.get(1),(String)data.get(2),(HashSet<String>) data.get(3));
//        System.out.println("After sixth Update" + t.toString());
        assertNotNull(t);
        assertNotNull(t.getAgent());
        assertEquals(old_t.getUpdated(),t.getUpdated());
    }

    //update with Same agent as assigned right now, do not update ticket
    @Test
    public void test122UpdateWithSameAgent(){
        List data = updateList.get(7);
        Ticket t = s.updateTicket(Integer.parseInt((String)data.get(0)),(String)data.get(1),(String)data.get(2),(HashSet<String>) data.get(3));
//        System.out.println("After seventh Update" + t.toString());
        assertNotNull(t);
        assertNotNull(t.getAgent());
        assertEquals((String)data.get(1),t.getAgent());
    }

    //add same tag applied right now, do not update agent
    @Test
    public void test123UpdateWithSameTagAdd(){
        List data = updateList.get(8);
        Ticket t = s.updateTicket(Integer.parseInt((String)data.get(0)),(String)data.get(1),(String)data.get(2),(HashSet<String>) data.get(3));
//        System.out.println("After eighth Update" + t.toString());
        assertNotNull(t);
        assertEquals(Integer.parseInt((String) data.get(0)),t.getId());
        assertNotNull(t.getAgent());
        assertTrue(t.getTags().containsAll((HashSet<String>)data.get(3)));
    }

    //remove tags which are not applied, do not update agent name
    @Test
    public void test124UpdateWithUnknownTagRemove(){
        List data = updateList.get(9);
        Ticket t = s.updateTicket(Integer.parseInt((String)data.get(0)),(String)data.get(1),(String)data.get(2),(HashSet<String>) data.get(3));
//        System.out.println("After ninth Update" + t.toString());
        assertNotNull(t);
        assertEquals(Integer.parseInt((String) data.get(0)),t.getId());
        assertNotNull(t.getAgent());
        ((HashSet<String>)data.get(3)).forEach((oo)->assertFalse(t.getTags().contains(oo)));
    }

    @Test(expected = InvalidParameterException.class)
    public void test125DeleteWithWrongId(){
        Integer data = deleteList.get(1);
        Ticket t = s.deleteTicket(data);
        assertNull(t);
    }

    @Test
    public void test126DeleteWithValidId(){
        Integer data = deleteList.get(0);
        Ticket t = s.deleteTicket(data.intValue());
//        System.out.println("Deleted Ticket" + t.toString());
        assertNotNull(t);
        assertEquals(data.intValue(),t.getId());
    }

    @Test (expected = InvalidParameterException.class)
    public void test127GetDetailWithWrongId(){
        Integer data = getDetail.get(0);
        Ticket t = s.getTicketById(data.intValue());
        assertNull(t);
    }

    @Test
    public void test128GetDetailWithCorrectId(){
        Integer data = getDetail.get(1);
        Ticket t = s.getTicketById(data.intValue());
//        System.out.println("get detail ticket: " + t.toString());
        assertNotNull(t);
        assertEquals(data.intValue(),t.getId());
    }

    @Test
    public void test129GetAllTickets(){
        List<Ticket> list = s.getAllTickets();
//        System.out.println("All tickets: " + list);
        assertEquals(1,list.size());
        assertEquals(2,list.get(0).getId());
    }

    @Test
    public void test130GetByAgentWithWrongAgent(){
        String data = getAgentTickets.get(0);
        List<Ticket>  list = s.ticketsOfAgent(data);
        assertEquals(0,list.size());

    }

    @Test
    public void test131GetByAgentWithCorrectAgent(){
        String data = getAgentTickets.get(1);
        List<Ticket> list = s.ticketsOfAgent(data);
        assertEquals(1,list.size());
        list.forEach(ticket -> assertEquals(data,ticket.getAgent()));
    }

    @Test
    public void test132GetByTagWithCorrectTag(){
        assertEquals(1,s.ticketsCountForTag(tagReport.get(0)));
    }

    @Test
    public void test133GetByTagWithInvalidTag(){
        assertEquals(0,s.ticketsCountForTag(tagReport.get(1)));
    }

    @Test
    public void test134OlderThanDaysReportWithInvalid(){
        assertEquals(0,s.olderThanDaysReport(5).size());
    }

    @Test
    public void test135OlderThanDaysReportWithValid(){
        List<Ticket> list = s.olderThanDaysReport(0);
        assertEquals(1,list.size());
        assertEquals(2,list.get(0).getId());
    }

    @Test
    public void test136OldestTicketsReport(){
        List<Ticket> list = s.oldestTicketsReport();
        assertEquals(1,list.size());
        assertEquals(2,list.get(0).getId());
    }

    @Test
    public void test137CountReport(){
        assertEquals(1,s.countReport());
    }


/*    @Test(expected = InvalidParameterException.class)
    public void test1CreateWithFullDataSet(){
        //This is single function to test against full data set; what values are passed and what would be expected result to get pass the test case.
        createList.forEach((obj)->{
            Ticket t = s.createTicket((String) obj.get(0),(String)obj.get(1),(HashSet<String>)obj.get(2));
            if(obj.get(0) == null || ((String)obj.get(0)).length() == 0 || obj.get(1) == null || ((String)obj.get(1)).length() == 0)
            {
                assertNull(t);
            }
            else
            {
                assertNotNull(t);
                assertEquals((String)obj.get(0),t.getSubject());
                assertEquals((String)obj.get(1),t.getAgent());
//                if(((HashSet<String>)obj.get(2)).size() > 0)
                assertTrue(t.getTags().containsAll((HashSet<String>)obj.get(2)));
            }
        });
        System.out.println(s.getAllTickets());
    }

    @Test(expected = InvalidParameterException.class)
    public void test2UpdateWithFullDataSet(){
//        test1CreateWithFullDataSet();
        s.readAllTicketsFromFile();
        updateList.forEach((obj)->{
            Ticket t = s.updateTicket(Integer.parseInt((String)obj.get(0)),(String)obj.get(1),(String)obj.get(2),(HashSet<String>) obj.get(3));
            if(obj.get(0) == null || ((String)obj.get(0)).length() == 0 || obj.get(0).equals("1234"))
            {
                assertNull(t);
            }
            else
            {
                assertNotNull(t);
                assertEquals(Integer.parseInt((String) obj.get(0)),t.getId());
                assertNotNull(t.getAgent());
                if(obj.get(1) != null && ((String)obj.get(1)).length() != 0)
                    assertEquals((String)obj.get(1),t.getAgent());
                if(obj.get(2) != null && ((String)obj.get(2)).length() > 0 *//*&& obj.get(3) != null && ((HashSet<String>)obj.get(3)).size() > 0*//*)
                {
                    if(obj.get(2).equals("A"))
                        assertTrue(t.getTags().containsAll((HashSet<String>)obj.get(3)));
                    if(obj.get(2).equals("R"))
                        ((HashSet<String>)obj.get(3)).forEach((oo)->assertFalse(t.getTags().contains(oo)));
                }
            }
        });
    }

    @Test(expected = InvalidParameterException.class)
    public void test3DeleteWithFullDataSet(){
//        test2UpdateWithFullDataSet();
        s.readAllTicketsFromFile();
        deleteList.forEach((id)->{
            Ticket t = s.deleteTicket(id);
            if(id.equals(999))
                assertNull(t);
            else
                assertNotNull(t);
        });
        System.out.println(s.getAllTickets());
    }

    @Test(expected = InvalidParameterException.class)
    public void test4GetDetailWithFullDataSet(){
//        test3DeleteWithFullDataSet();
        s.readAllTicketsFromFile();
        System.out.println(s.getAllTickets());
        deleteList.forEach((id)->{
            Ticket t = s.getTicketById(id);
            if(id.equals(1) || id.equals(999))
                assertNull(t);
            else
                assertNotNull(t);
        });
    }

    @Test
    public void test5GetAllTicketsWithFullDataSet(){
//        test4GetDetailWithFullDataSet();
        s.readAllTicketsFromFile();
        System.out.println(s.getAllTickets());
        List<Ticket> list = s.getAllTickets();
        assertEquals(1,list.size());
        assertEquals(2,list.get(0).getId());
    }

    @Test
    public void test6FindAssignedToAgentWithFullDataSet(){
//        test5GetAllTicketsWithFullDataSet();
        s.readAllTicketsFromFile();
        getAgentTickets.forEach((agent)->{
            List<Ticket> list = s.ticketsOfAgent(agent);
            if(agent.equals("a3"))
            {
                assertNotNull(list);
                assertEquals(1,list.size());
            }
            else
                assertEquals(0,list.size());
        });
    }*/


}
