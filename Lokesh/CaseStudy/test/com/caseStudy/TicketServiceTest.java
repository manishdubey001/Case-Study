package com.caseStudy;

import com.casestudy.Services;
import org.junit.Before;
import org.junit.Test;
import org.junit.*;

import java.util.*;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static junit.framework.TestCase.*;

/**
 * Created by lokesh on 19/1/16.
 */
public class TicketServiceTest {
    /*final ArrayList<List<Object>> list= Arrays.asList(
            Arrays.asList("test-subject","test-agent", "tag1,tag2" )
    ) ;*/

//    final List list = Arrays.asList(1,2);
    static ArrayList<List<Object>> list = new ArrayList<>();
    Services s = new Services();
    @BeforeClass
    public static void setData(){


        List<Object> hs = new ArrayList<>();

        hs.add("subject1");
        hs.add("agent1");
        HashSet<String> hh = new HashSet<>();
        hh.add("t1");
        hh.add("t2");
        hs.add(hh);
        list.add(hs);

        hs = new ArrayList<>();
        hs.add("");
        hs.add("a2");
        hh = new HashSet<>();
        hh.add("t2");
        hh.add("t3");
        hs.add(hh);
        list.add(hs);
//        hs.clear();

        hs = new ArrayList<>();
        hs.add("sub2");
        hs.add("");
        hh= new HashSet<>();
        hh.add("t4");
        hh.add("t5");
        hs.add(hh);
        list.add(hs);
//        hs.clear();

        hs = new ArrayList<>();
        hs.add("sub3");
        hs.add("a3");
        hs.add(new HashSet<String>());
        list.add(hs);
    }


    @Test
    public void testCreateWithFullDataSet(){
        //This is single function to test against full data set; what values are passed and what would be expected result to get pass the test case.
        list.forEach((obj)->{
            if((String)obj.get(0) == null || ((String)obj.get(0)).length() == 0 || (String)obj.get(1) == null || ((String)obj.get(1)).length() == 0)
                assertNull(s.createTicket((String) obj.get(0),(String)obj.get(1),(HashSet<String>)obj.get(2)));
            else
                assertNotNull(s.createTicket((String) obj.get(0),(String)obj.get(1),(HashSet<String>)obj.get(2)));
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
}
