package com.inin.example.factory;

import com.inin.example.model.Ticket;
import com.inin.example.util.TicketUtil;

import java.util.*;

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
    public static Ticket newInstance(String subject, String agentName, HashSet<String> tags)
    {
        int id = getNextId();
        return new Ticket(id,subject,agentName,tags);
    }
    /**
     * Return the next ticket Id
     * @return int
     */
    public static int getNextId()
    {
        int currentTicketId = Integer.valueOf(TicketUtil.getProperty("ticketId"));
        TicketUtil.setProperty("ticketId",String.valueOf(++currentTicketId));
        return currentTicketId;

    }
}
