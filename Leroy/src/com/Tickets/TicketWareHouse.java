package com.Tickets;

import java.util.Set;

/**
 * Created by root on 19/1/16.
 */
// "Factory" is a more common name for this kind of thing
public class TicketWareHouse {
    private static int counter;

    public static Ticket getInstance(String subject, Set tags, String name){
        int ticketId = getTicketId();
        return new Ticket(ticketId, subject, tags, name);
    }
    public static int getTicketId(){
        return ++counter;
    }
}
