package com.services;

import com.model.Tag;
import com.model.Ticket;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by root on 30/1/16.
 */
// Lokesh: Not all reporting stuffs are implemented.
// Lokesh: This class only does is operations on Ticket data, why not these are part of TicketOperations class?
public class TicketReports {

    private Map<Long, Ticket> ticketHashMap = null;

    TicketReports(){
        /*TicketOperations objTicketOperations = new TicketOperations();
        ticketHashMap = objTicketOperations.getAllTicket();*/
        ticketHashMap = TicketSerializedClass.readTicketsFromFile();
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
        // Lokesh: Even for returning Simple single Ticket object, creating Map?
        Map<Long, Ticket> tempMap = new HashMap<>();

        tempMap.put(ticket.getId(), ticket);

        return tempMap;
    }

    public Map<String, List<Ticket>> getTicketCountByTag(){
        Map<String, List<Ticket>> tagCountMap = new HashMap<>();
        ticketHashMap.values().stream()
                .forEach(ticket -> {
                    ticket.getTags().forEach(tag ->{
                        if(tagCountMap.containsKey(tag))
                            tagCountMap.get(tag).add(ticket);
                        else{
                            List<Ticket> list = new ArrayList<>();
                            list.add(ticket);
                            tagCountMap.put(tag, list);
                        }

                    });
                });

        return tagCountMap;
    }


    public void displayTagTicketCount(Map<String, List<Ticket>> tagCountMap){
        tagCountMap.forEach((String tagName,List<Ticket> ticketList)-> System.out.println(tagName+"   :   "+ticketList.size()));
    }
}
