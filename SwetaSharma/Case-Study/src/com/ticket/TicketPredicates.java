package com.ticket;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by root on 2/2/16.
 */
public class TicketPredicates {

    public static Predicate<Ticket> isAgentPresent(String agentName){
        return ticket -> ticket.getAgentName().equalsIgnoreCase(agentName);
    }

    public static Predicate<Ticket> isTagPresent(String tag){
        return ticket -> ticket.getTags().contains(tag);

    }

    public static List<Ticket> filterTickets(List<Ticket> arrTickets, Predicate<Ticket> predicate){
        return arrTickets.stream().sorted().filter( predicate ).collect(Collectors.<Ticket>toList());
    }
}
