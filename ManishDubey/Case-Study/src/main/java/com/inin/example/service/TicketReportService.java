package com.inin.example.service;

import com.inin.example.model.Ticket;
import com.inin.example.util.TicketSerializationUtil;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by root on 27/1/16.
 */
public class TicketReportService {


    /**
     * Return the total count of Tickets
     * @return int
     */
    public int totalTicketCount()
    {
        Map<Integer,Ticket> tickets = TicketSerializationUtil.deserializedTickets();
        return tickets.size();
    }

    /**
     * Return the oldest Ticket
     * @return Ticket
     */
    public Ticket oldestTicket()
    {
        Map<Integer,Ticket> tickets = TicketSerializationUtil.deserializedTickets();
        if(tickets.size() > 0) {
           Ticket ticket= tickets.values()
                    .stream()
                    .sorted((Ticket t1, Ticket t2) -> t1.getCreated().compareTo(t2.getCreated()))
                    .findFirst()
                    .get();
           return ticket.copy(ticket);
        }
        return null;
    }

    /**
     * Return Ticket By Date
     * @param date
     * @return List<Ticket>
     */
    public List<Ticket> ticketOlderByDate(LocalDateTime date){
        Map<Integer,Ticket> tickets = TicketSerializationUtil.deserializedTickets();
        return Collections.unmodifiableList(tickets.values()
                .stream()
                .filter(ticket -> date.compareTo(ticket.getCreated()) >= 0 )
                .collect(Collectors.toList()));
    }

    /**
     * Return Ticket group by tags
     * @return Map<String, List<Ticket>>
     */
    public Map<String, List<Ticket>> ticketsGroupByTag(){
        Map<Integer,Ticket> tickets = TicketSerializationUtil.deserializedTickets();
        Map<String,List<Ticket>> ticketByTag = new HashMap<>();
        tickets.values()
                .stream()
                .forEach(ticket -> {
                    ticket.getTags().forEach( tag -> {
                        if(ticketByTag.containsKey(tag))
                            ticketByTag.get(tag).add(ticket);
                        else {
                            List<Ticket> list = new ArrayList<>();
                            list.add(ticket);
                            ticketByTag.put(tag, list);
                        }
                    });
                });
        return Collections.unmodifiableMap(ticketByTag);

    }
}
