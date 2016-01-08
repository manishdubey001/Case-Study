package com.example.ticketcrud.factory;

import com.example.ticketcrud.model.Ticket;

import java.util.Date;
import java.util.HashSet;

/**
 * Created by root on 31/12/15.
 */
public class TicketFactory {
    /**
     * Create new Ticket object
     * @param id
     * @param subject
     * @param agentName
     * @param tags
     * @param created
     * @param modified
     * @return Ticket
     */
    public static Ticket newInstance(int id, String subject, String agentName, HashSet<String> tags, Date created, Date modified)
    {
        return new Ticket(id,subject,agentName,tags,created,modified);
    }
}
