package com.services;

import com.customexceptions.UserInputException;
import com.model.Ticket;
import com.util.UserConsoleInput;

import java.io.File;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by root on 30/1/16.
 */
// Lokesh: Not all reporting stuffs are implemented.
// Lokesh: This class only does is operations on Ticket data, why not these are part of TicketOperations class?
    /**Deepak:
     * Report is our different service. which based on ticket service.
     * */
public class TicketReports {

    private Map<Long, Ticket> ticketHashMap;

    TicketReports(){
        /*TicketOperations objTicketOperations = new TicketOperations();
        ticketHashMap = objTicketOperations.getAllTicket();*/
        File file = TicketSerializedClass.createFile("tickets.ser");
        ticketHashMap = TicketSerializedClass.readTicketsFromFile(file);
    }

    public long countNoOfTicketInSystem(){
        return ticketHashMap.size();
    }

    public Ticket getOldestTicket() throws UserInputException {

        if(ticketHashMap.isEmpty())
            throw new UserInputException("No ticket found!");

        return ticketHashMap.values().stream()
                                    .sorted((Ticket t1, Ticket t2) -> t1.getCreated()
                                        .compareTo(t2.getCreated())).findFirst().get();
        // Lokesh: Even for returning Simple single Ticket object, creating Map?
        /*Map<Long, Ticket> tempMap = new HashMap<>();

        tempMap.put(ticket.getId(), ticket);

        return tempMap;*/
    }

    public void oldestTicket(){
        Ticket ticket = null;
        try {
            ticket = getOldestTicket();
        } catch (UserInputException e) {
            System.out.println(e.getMessage());
        }

        System.out.println(ticket);
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


    public void displayTagTicketCount(){
        Map<String, List<Ticket>> tagCountMap = getTicketCountByTag();
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


    public void getTicketsByDays(){
        try {
            Map<Long, Ticket> ticketMap = getTicketOlderByDays();
            if (ticketMap.isEmpty()) {
                System.out.println("No Ticket found!");
                return;
            }
            ticketMap.values().stream().sorted((Ticket t1, Ticket t2) -> t2.getModified().compareTo(t1.getModified()))
                    .forEach(System.out::println);

        } catch (UserInputException e) {
            System.out.println(e.getMessage());
        }

    }


    public Map<Long, Ticket> getTicketOlderByDays() throws UserInputException {
        String dateString = UserConsoleInput.acceptString("Please enter date in form of dd-mm-yyyy");
        String []dateArray = dateString.split("-");

        if(dateArray.length != 3){
            throw new UserInputException("Please enter date in proper format!");
        }

        for (String str : dateArray) {
            try {
                Integer.parseInt(str);
            }
            catch (NumberFormatException ne){
                System.out.println("Date is not proper number!");
                return new HashMap<>();
            }
        }

        String strFormat = Integer.parseInt(dateArray[2])+"-"+ Integer.parseInt(dateArray[1]) +"-"+ Integer.parseInt(dateArray[0]) +" "+ 0+":"+0;

        try{
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-d H:m");
            LocalDateTime dateTime = LocalDateTime.parse(strFormat, formatter); // to validate date
            LocalDateTime uptoDate = LocalDateTime.of(Integer.parseInt(dateArray[2]), Integer.parseInt(dateArray[1]), Integer.parseInt(dateArray[0]), 0, 0);

            return ticketOlderByDays(uptoDate);
        }
        catch (DateTimeException de){
            System.out.println("Invalid date provide! Please provide date in proper format");
            return new HashMap<>();
        }
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
