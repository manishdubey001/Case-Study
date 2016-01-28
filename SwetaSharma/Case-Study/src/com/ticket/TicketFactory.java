package com.ticket;

import java.util.HashSet;

/**
 * Created by root on 22/1/16.
 */
// It's not really necessary to have a separate class to do this if you just need one method. You could
// implement this as a static method in TicketModel
public class TicketFactory {
    /**
     * Create new ticket object
     *
     * @param ticketId
     * @param subject
     * @param agentName
     * @param setOfTags
     * @return
     */
    public static TicketModel newInstance(int ticketId, String subject, String agentName, HashSet<String> setOfTags) {
        return new TicketModel(ticketId, subject, agentName, setOfTags);
    }
}
