package com.ticketmaster;

import com.ticketmaster.exceptions.TicketNotFoundException;
import com.ticketmaster.models.Ticket;
import com.ticketmaster.utils.AppUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Main class
 * Used to run the new instance of application
 * This application contains the latest modified code with
 * different set of collection classes (HashMap,LinkedHashMap,TreeMap)
 */
public class Main {
//    private static final int size = 100;
    private static int size = 10000; // check with 10,000 initial records
    private static int agents = 30;
    public static int collectionChoice = 1;

    public static void main(String[] args) throws ClassNotFoundException,TicketNotFoundException, IOException, InterruptedException {

        System.out.println("Initializing program...");
        System.out.println("Enter initial ticket list size (integer) : ");
        BufferedReader b = new BufferedReader(new InputStreamReader(System.in));
        size = Integer.parseInt(b.readLine()); //this takes value from user to add initial tickets

        System.out.println("Enter agent size (integer) : ");
        agents = Integer.parseInt(b.readLine()); //this takes the size to agents to start application

        System.out.println("Select Collection for storage of ticket (1:HashMap 2:TreeMap 3:LinkedHashMap) : ");
        collectionChoice = Integer.parseInt(b.readLine()); //this takes the size to agents to start application


        long s = AppUtil.logTime("start",true, 0);

        //initialization of program
        Set<String> tags = new HashSet<>();
//        Map<Integer, Object> lst = new HashMap<>();

        for (int i=0;i<size;i++){
            Map<String,Object> m = new HashMap();
            m.put("subject","subject-"+(i+1));
            m.put("agent","a-"+((i+1)%agents != 0 ? (i+1)%agents:agents));
            int tSize = (int) ( Math.random() * 10)/2, j;
            j = 0;
            Set<String> tTags = new HashSet<>();
            while (j<tSize){
                tTags.add("tag-"+j);
                j++;
            }
            m.put("tags",tTags);
            m.get("tags");
            if (m.get("tags") != null){
                tags.addAll(tTags);
                if (Ticket.tagList == null) {
                    Ticket.tagList = new HashSet();
                }
                Ticket.tagList.addAll(tTags);
            }

            Ticket h = new Ticket();
            h.setValues(m);
//            Thread.currentThread().sleep(50); //make thread sleep
            h.save();

        }

        /*System.out.println("after test");
        Set s = Ticket.ticketList.entrySet();
        Iterator it = s.iterator();

        while (it.hasNext()){
            Map.Entry mp = (Map.Entry) it.next();
            System.out.println("Index: "+mp.getKey());
            Ticket t = (Ticket) mp.getValue();
            System.out.println(t.getId());
        }
        System.exit(0);
        */

        System.out.println("Initialized application with "+size+" values...");
        AppUtil.logTime("end",false, s);

        try{
            AppRunner.app().start();
        }catch (TicketNotFoundException | IOException   e){
            System.out.println("Exception occurred in application");
            System.out.println(e.getMessage());
            System.out.println("Closing Application. Thank you.");

            if (b!=null) b.close();

        }

    }
}
