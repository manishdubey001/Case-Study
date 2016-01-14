package com.example.ticketcrud.factory;

import com.example.ticketcrud.model.Ticket;
import com.example.ticketcrud.util.TicketUtil;

import java.util.Date;
import java.util.HashSet;

/**
 * Created by root on 31/12/15.
 */
public class TicketFactory {

    private static int currentTicketId = 0;
    /**
     * Create new Ticket object
     * @param subject
     * @param agentName
     * @param tags
     * @return Ticket
     */
    public static Ticket newInstance(String subject, String agentName, HashSet<String> tags)
    {
        int id = getNextTicketId();
        return new Ticket(id,subject,agentName,tags);
    }

    /**
     * Return the next ticket Id
     * @return int
     */
    public static int getNextTicketId()
    {
        return ++currentTicketId;
    }
}
