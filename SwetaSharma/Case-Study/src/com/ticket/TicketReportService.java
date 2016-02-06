package com.ticket;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by root on 4/2/16.
 */
public class TicketReportService {
    /**
     * get the total number of tickets present in the system
     */
    public void getNumberOfTicketsPresentInSystem(){
        long ticketCount = calTicketsCountInSystem();
        if(ticketCount == 0){
            System.out.println("No ticket present in the system");
        }else{
            System.out.println("Number of tickets present in system are: "+ ticketCount);
        }
    }

    /**
     * Calculate total ticket count present in the system
     * @return count
     */
    public long calTicketsCountInSystem(){
        return TicketService.ticketList.size();
    }

    /**
     * tags in use of tickets
     */
    public void getTagsInUseOfTickets(){
        Map<String, Integer> tagsCount = calTagsCountInTicket();
        if(tagsCount.isEmpty()){
            System.out.println("No tags present in the system");
        }else {
            printTagsCount(tagsCount);
        }
    }

    /**
     * Print count of all tags used in tickets
     * @param hashMap
     */
    public void printTagsCount(Map<String, Integer> hashMap){
        hashMap.forEach((tag, count) -> System.out.println("Tag: "+tag + " Count: "+count));
    }

    /**
     * Calculate count of all tags used in tickets
     * @return
     */
    public Map<String, Integer> calTagsCountInTicket(){
        Map<String, Integer> hashMap = new HashMap<>();
        for (Ticket ticket : TicketService.ticketList.values()){
            for (String tags : ticket.getTags()){
                if(hashMap.containsKey(tags)){
                    hashMap.put(tags, hashMap.get(tags) +1);
                }else {
                    hashMap.put(tags, 1);
                }
            }
        }
        return hashMap;
    }

    /**
     * Get list of tickets older than x number of days
     */
    public void getTicketsOlderThanXDays(){
        System.out.println("Please enter the number of days");
        try {
            Scanner read = new Scanner(System.in);
            int days = read.nextInt();
            if(days > 0){
                List<Ticket> tickets = calTicketsOlderThanXDays(days);
                if(tickets.isEmpty()){
                    System.out.println("No tickets available");
                }else {
                    printTicketListValues(tickets);
                }
            }else{
                System.out.println("Invalid input... Days should be greater than zero");
            }
        }catch (InputMismatchException ime){
            System.out.println("Please enter valid number of days");
        }
    }

    /**
     * Calculate list of tickets older than x number of days
     * @param days
     * @return List of tickets
     */
    public List<Ticket> calTicketsOlderThanXDays(int days){
            LocalDateTime olderDays = LocalDateTime.now().minus(days, ChronoUnit.DAYS);
            return TicketService.ticketList.values().stream().filter(ticket -> ticket.getCreated().isBefore(olderDays)).collect(Collectors.toList());
    }

    /**
     * print list of tickets
     * @param ticketList
     */
    public void printTicketListValues(List<Ticket> ticketList){
        ticketList.forEach(System.out::println);
    }

    /**
     * Get oldest ticket in the system
     */
    public void getOldestTicketInSystem(){
        List<Ticket> ticketList = new ArrayList<>(TicketService.ticketList.values());
        Collections.sort(ticketList, new CreatedDateComparator());
        System.out.println("Oldest ticket in the system is "+ticketList.get(0));

        /*Optional<Ticket> oldestTicket = TicketService.ticketList.values().stream().sorted(new CreatedDateComparator()).findFirst();
        if(oldestTicket.isPresent()){
            System.out.println(oldestTicket.get());
        }else {
            System.out.println("List is empty");
        }*/
    }
}
