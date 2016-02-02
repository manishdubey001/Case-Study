package com.ticket.factory;

import com.ticket.model.Ticket;

import java.util.Set;

/**
 * Created by root on 19/1/16.
 */
public class TicketFactory {
    private static int counter;
    public static Ticket createNewInstance(String subject, String name, Set tags){
        int ticketId = getTicketId();
        return new Ticket(ticketId, subject, name, tags);
    }
    public static int getTicketId(){
        return ++counter;
    }
}
