package com.inin.example.factory;

import com.inin.example.model.Ticket;
import com.inin.example.util.TicketSerializationUtil;

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
        int id = getNextTicketId();
        return new Ticket(id,subject,agentName,tags);
    }
    /**
     * Return the next ticket Id
     * @return int
     */
    // this is an interesting problem to solve. One way to approach is: Any time a ticket is deserialzed,
    // set the next counter to (max known ticket ID) + 1
    public static int getNextTicketId()
    {
        Map<Integer,Ticket> ticketList = TicketSerializationUtil.deserializedTickets();
        if(ticketList.size() > 0 ) {
            int maxTicketId = ticketList.values()
                    .parallelStream()
                    .max((Ticket t1, Ticket t2) -> Integer.valueOf(t1.getId()).compareTo(Integer.valueOf(t2.getId())))
                    .get()
                    .getId();
            return ++maxTicketId;
        }else
            return 1;
    }
}
