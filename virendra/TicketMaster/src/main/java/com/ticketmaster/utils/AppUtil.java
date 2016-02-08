package com.ticketmaster.utils;

import com.ticketmaster.exceptions.TicketNotFoundException;
import com.ticketmaster.helpers.TicketService;
import com.ticketmaster.models.Ticket;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.*;

/**
 * Created by root on 31/12/15.
 */
public class AppUtil {

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
        menu.put(8,"Ticket marked with selected Tag\n" +
                "=============== Reporting Menu ===============");
        menu.put(9,"Total Number of tickets in System");
        menu.put(10,"Oldest Ticket in the System");
        menu.put(11,"Ticket older than days");
        menu.put(12,"List of Tags present in System");
        menu.put(13,"No. of Tickets with tag\n" +
                "=============== General Menu ===============");
        menu.put(0,"End Application (Quit)");
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
        TicketService service = new TicketService();

        service.setTicketList((Map<Integer, Ticket>) util.readFromFile());
        service.initTags();
        service.initAgents();


    }

    public static void main(String[] args) {

        long time1, time2;
        time1 = System.currentTimeMillis();
        time2 = LocalDateTime.now(ZoneId.of("UTC")).toInstant(ZoneOffset.UTC).toEpochMilli();

        System.out.println(new Date(time1) + "==>"+time1);

        System.out.println(new Date(time2) + "==>"+time2);
    }

}
