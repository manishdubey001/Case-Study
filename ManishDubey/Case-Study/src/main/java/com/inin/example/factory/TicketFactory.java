package com.inin.example.factory;

import com.inin.example.model.Ticket;
import com.inin.example.util.TicketUtil;

import java.util.Set;

/**
 * Created by root on 31/12/15.
 */
public class TicketFactory {

    /**
     * Create new Ticket object
     * @param subject
     * @param agentName
     * @param tags
     * @return Ticket
     */
    public static Ticket newInstance(String subject, String agentName, Set<String> tags)
    {
        int id = getNextId();
        return new Ticket(id,subject,agentName,tags);
    }

    public static int getNextId()
    {
        int currentTicketId = Integer.valueOf(TicketUtil.getProperty("ticketId"));
        TicketUtil.setProperty("ticketId",String.valueOf(++currentTicketId));
        return currentTicketId;

    }

    /**
     * Create copy of ticket
     * @param ticket
     * @return Ticket
     */
    public static Ticket newInstance(Ticket ticket)
    {
        return new Ticket(ticket);
    }
}
