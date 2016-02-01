package com.services;

import com.model.Ticket;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by root on 30/1/16.
 */
public class TicketReports {

    private Map<Long, Ticket> ticketHashMap = null;

    TicketReports(){
        TicketOperations objTicketOperations = new TicketOperations();
        ticketHashMap = objTicketOperations.getAllTicket();
    }

    public long countNoOfTicketInSystem(){
        if(ticketHashMap == null || ticketHashMap.isEmpty())
            return 0;

        return ticketHashMap.size();
    }

    public Map<Long, Ticket> getOldestTicket(){

        Ticket ticket = ticketHashMap.values().stream()
                                    .sorted((Ticket t1, Ticket t2) -> Long.valueOf(t2.getCreated())
                                        .compareTo(Long.valueOf( t1.getCreated()))).findFirst().get();
        Map<Long, Ticket> tempMap = new HashMap<>();

        tempMap.put(ticket.getId(), ticket);

        return tempMap;
    }

}
