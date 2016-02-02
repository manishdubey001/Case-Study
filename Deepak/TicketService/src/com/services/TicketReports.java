package com.services;

import com.customexceptions.UserInputException;
import com.model.Ticket;
import com.util.UserConsoleInput;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

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
                                    .sorted((Ticket t1, Ticket t2) -> t2.getCreated()
                                        .compareTo(t1.getCreated())).findFirst().get();
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

    /**
     * Older function */
/*    public Map<Long, Ticket> getTicketOlderByDays(int noOfDays){
        Long olderTime = (System.currentTimeMillis() - (noOfDays * 24 * 3600 * 1000)) / 1000L;

        Map<Long, Ticket> tempTicketMap = new HashMap<>();

        for (Ticket ticket : ticketHashMap.values()) {
            if(ticket.getCreated() < olderTime)
                tempTicketMap.put(ticket.getId(), ticket);
        }

        return tempTicketMap;
    }

    public Map<Long, Ticket> ticketOlderByDays(){
        int noOfdays = UserConsoleInput.acceptNumber();
        return getTicketOlderByDays(noOfdays);
    }*/

    public Map<Long, Ticket> getTicketOlderByDays(){
        System.out.println("Please enter date in form of dd/mm/yyyy");
        String dateString= "";
        String []dateArray = null;
        try {
           dateString = UserConsoleInput.acceptString();
            dateArray = dateString.split("/");
            if(dateArray.length > 3 || dateArray.length < 0){
                throw new UserInputException("Please enter date in proper format!");
            }
            else {
                for (String str : dateArray) {
                    try {
                        Integer.parseInt(str);
                    }
                    catch(NumberFormatException e){
                        System.out.println("Date is not in proper format!");
                    }
                }
            }
        } catch (UserInputException e) {
            System.out.println(e.getMessage());
        }

        LocalDateTime uptoDate = LocalDateTime.of(Integer.parseInt(dateArray[2]), Integer.parseInt(dateArray[1]), Integer.parseInt(dateArray[0]), 0, 0);

        return ticketOlderByDays(uptoDate);
    }


    public Map<Long, Ticket> ticketOlderByDays(LocalDateTime uptoDate){
        Map<Long, Ticket>tempTicketMap = new HashMap<>();
        if(uptoDate == null) {
            System.out.println("Date enter is invalid!");
            return tempTicketMap;
        }
            tempTicketMap = ticketHashMap.values()
                    .stream()
                    .filter(ticket -> uptoDate.compareTo(ticket.getCreated()) >= 0)
                        .collect(Collectors.toMap(Ticket::getId, Function.identity()));
        return tempTicketMap;
    }

}
