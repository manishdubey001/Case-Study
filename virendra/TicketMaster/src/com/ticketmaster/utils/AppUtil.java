package com.ticketmaster.utils;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

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
}
