package com.caseStudy;

import com.casestudy.Services;
import com.casestudy.Tickets;
import org.junit.Test;
import org.junit.*;
import org.junit.runners.MethodSorters;

import java.util.*;

import static junit.framework.Assert.*;
//import static junit.framework.Assert

/**
 * Created by lokesh on 19/1/16.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TicketServiceTest {
    /*final ArrayList<List<Object>> list= Arrays.asList(
            Arrays.asList("test-subject","test-agent", "tag1,tag2" )
    ) ;*/

//    final List list = Arrays.asList(1,2);
    static ArrayList<List<Object>> createList = new ArrayList<>(); //Holds List of objects to create Ticket with different values of attributes for each Ticket
    static ArrayList<List<Object>> updateList = new ArrayList<>(); //Holds List of objects to update ticket with different values of attributes for each Ticket
    Services s = new Services();
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

        //do not update anything, but called update request ,updated time will be changed
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

        //
    }


    @Test
    public void testCreateWithFullDataSet(){
        //This is single function to test against full data set; what values are passed and what would be expected result to get pass the test case.
        createList.forEach((obj)->{
            Tickets t = s.createTicket((String) obj.get(0),(String)obj.get(1),(HashSet<String>)obj.get(2));
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
                assertTrue(t.getTag().containsAll((HashSet<String>)obj.get(2)));
            }
        });
    }

    /*
// Below this section contains separate functions to test against individual data set one at a time.
    @Test
    public void testCreateWithEmptySubject(){
        List lst = list.get(1);
        assertNull(s.createTicket((String) lst.get(0),(String)lst.get(1),(HashSet<String>)lst.get(2)));
    }

    @Test
    public void testCreateWithEmptyAgent(){
        List lst = list.get(2);
        assertNull(s.createTicket((String) lst.get(0),(String)lst.get(1),(HashSet<String>)lst.get(2)));
    }

    @Test
    public void testCreateWithEmptyTag(){
        List lst = list.get(3);
        assertNotNull(s.createTicket((String) lst.get(0),(String)lst.get(1),(HashSet<String>)lst.get(2)));
    }

    @Test
    public void testCreateWithAllFields(){
        List lst = list.get(0);
        assertNotNull(s.createTicket((String) lst.get(0),(String)lst.get(1),(HashSet<String>)lst.get(2)));
    }
*/

    @Test
    public void testUpdateWithFullDataSet(){
        updateList.forEach((obj)->{
            Tickets t = s.updateTicket(Integer.parseInt((String)obj.get(0)),(String)obj.get(1),(String)obj.get(2),(HashSet<String>) obj.get(3));
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
                if(obj.get(2) != null && ((String)obj.get(2)).length() > 0 /*&& obj.get(3) != null && ((HashSet<String>)obj.get(3)).size() > 0*/)
                {
                    if(obj.get(2).equals("A"))
                        assertTrue(t.getTag().containsAll((HashSet<String>)obj.get(3)));
                    if(obj.get(2).equals("R"))
                        ((HashSet<String>)obj.get(3)).forEach((oo)->assertFalse(t.getTag().contains(oo)));
                }
            }
        });
    }



}
