package service;

import Serialization.TicketSerialization;
import model.Ticket;

import java.util.*;

public class TicketReportsService {


    public int getTotalTicketCounts() {
        return TicketSerialization.deserialize().size();
    }

    public Ticket getOldestTicket() {
        Map<Integer, Ticket> masterTicketsData = TicketSerialization.deserialize();
        if (masterTicketsData.size() > 0) {
            return masterTicketsData.values().stream().sorted((Ticket t1, Ticket t2) -> t1.getCreated().compareTo(t2.getCreated())).findFirst().get();
        }
        return null;
    }

    public Map<String, List<Ticket>> getTicketCountByTags() {
        Map<Integer, Ticket> masterTicketsData = TicketSerialization.deserialize();

        Map<String,List<Ticket>> ticketByTag = new HashMap<>();
        masterTicketsData.values()
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
