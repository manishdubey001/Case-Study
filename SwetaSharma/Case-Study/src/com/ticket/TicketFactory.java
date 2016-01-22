package com.ticket;

import java.util.HashSet;

/**
 * Created by root on 22/1/16.
 */
public class TicketFactory{
    /**
     * Create new ticket object
     * @param ticketId
     * @param subject
     * @param agentName
     * @param setOfTags
     * @return
     */
    public static TicketModel newInstance(int ticketId, String subject, String agentName, HashSet<String> setOfTags){
        return new TicketModel(ticketId, subject, agentName, setOfTags);
    }
}
