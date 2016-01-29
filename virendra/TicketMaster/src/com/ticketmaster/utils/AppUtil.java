package com.ticketmaster.utils;

import com.ticketmaster.Main;
import com.ticketmaster.exceptions.TicketNotFoundException;
import com.ticketmaster.helpers.TicketService;
import com.ticketmaster.models.Ticket;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

/**
 * Created by root on 31/12/15.
 */
public class AppUtil {

    public static String baseFolder = "files";
    public static String propertyFile = "conf.properties";

    /**
     * getMenu method contains the list of methods which will be displayed in the console.
     * @return Map
     */
    public static Map getMenu(){
        Map<Integer, String> menu = new LinkedHashMap<>(8);// Linked HashMap is been used so that
        // the insertion order in intact

        menu.put(1,"Create Ticket");
        menu.put(2,"Update Ticket (By Id)");
        menu.put(3,"Delete Ticket (By Id)");
        menu.put(4,"Get Ticket detail (By Id)");
        menu.put(5,"Retrieve all tickets");
        menu.put(6,"Select ticket Assigned to agent");
        menu.put(7,"Number of Ticket assigned to each Agents");
        menu.put(8,"Ticket marked with selected Tag");
        menu.put(9,"End (Quit)");
        return menu;
    }

    /**
     * logTime method is used to log processing time in milliseconds
     *
     * @param type String
     * @param hasReturn boolean
     * @param time long
     * @return log value based on the flag hasReturn
     */
    public static long logTime(String type , boolean hasReturn, long time ){
        long diff, s;

        if (type.equals("start")){
            s = System.currentTimeMillis();
            System.out.println("... Process Start: "+s);
        }else {
            s = System.currentTimeMillis();
            diff = s - time;
            System.out.println("... Process End: "+s+"\t\t|\t Time taken: "+(diff/1000.0)+" second(s)");
        }

        return hasReturn ? s : 0;
    }

    public static void initializeApp()
            throws ClassNotFoundException,TicketNotFoundException, IOException{

        SerializerUtil util = new SerializerUtil();
        Ticket.setMasterId(Integer.parseInt(util.readProperty("id")));
        TicketService.setTicketList((Map<Integer, Ticket>) util.readFromFile());


        //set ticket List
        TicketService service = new TicketService();


    }

    public static void initializeApp(final int size, final int agents)
            throws ClassNotFoundException,TicketNotFoundException, IOException{

        Set<String> tags = new HashSet<>();

        for (int i=0;i<size;i++){

            int tSize = (int) ( Math.random() * 10)/2, j = 0;

            Set<String> tTags = new HashSet<>();
            while (j<tSize){
                tTags.add("tag-"+j);
                j++;
            }

            if (!tTags.isEmpty()){
                tags.addAll(tTags);
                if (Ticket.tagList == null) { Ticket.tagList = new HashSet(); }
                Ticket.tagList.addAll(tTags);
            }

            new Ticket.TicketBuilder()
                    .withSubject("subject-"+(i+1))
                    .withAgent("a-"+((i+1)%agents != 0 ? (i+1)%agents:agents))
                    .withTags(tTags)
                    .build().save();

        }
    }


}
