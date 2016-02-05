package com.ticket.service;
import com.ticket.util.ConsoleScan;
import com.ticket.factory.TicketFactory;
import com.ticket.model.Ticket;
import com.ticket.util.Utility;

import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


/**
 * Created by root on 14/1/16.
 */
public class TicketService {
    private final Map<Integer, Ticket> ticketsMap = new HashMap<>();

    /**
     * function to create new Ticket in the system
     * @param sub
     * @param agent
     * @param tags
     * @return
     */
    public Ticket createTicket(String sub, String agent, String tags) throws InvalidParameterException{
        if(Utility.isStringValid(sub) && Utility.isStringValid(agent) && Utility.isStringValid(tags))
            throw new InvalidParameterException();

        String[] parsedTags = tags.split(",");
        Set<String> newTags = new HashSet<>(Arrays.asList(parsedTags));
        Ticket ticket = TicketFactory.createNewInstance(sub,agent, newTags);
        ticketsMap.put(ticket.getId(),ticket);
        return ticket;

    }

    /**
     * function to update a ticket in the system w.r.t Id
     * @param id
     * @param type
     * @param val
     * @return
     */
    public boolean updateTicket(int id, String type, String val){
        boolean updated = false;
        Ticket ticket = ticketsMap.get(id);
        if (Utility.isObjNull(ticket)){
            if (type.equals("agent") && Utility.isStringValid(val)) {
                ticket.setAgentName(val);
                updated = true;
            } else if (type.equals("tags") && Utility.isStringValid(val)) {
                String [] parsedTags = val.split(",");
                Set set1 = new HashSet<>(Arrays.asList(parsedTags));
                ticket.setTags(set1);
                updated = true;
            }
        }
        return updated;
    }

    /**
     * function to remove a ticket from the system
     * @param id
     * @return
     */
    public boolean removeTicketById(int id){
        if (id > 0){
            ticketsMap.remove(id);
            return true;
        }
        return false;
    }

    /**
     * function to fetch Ticket w.r.t Id
     * @param id
     * @return
     */
    public Ticket getTicketById(int id) throws InvalidParameterException{
        if (!Utility.isObjNull(ticketsMap.get(id)))
            throw new InvalidParameterException();

        return ticketsMap.get(id);
    }


    /**
     * function to get Tickets w.r.t Agent name
     * @param name
     * @return List of tickets
     */
    public List<Ticket> getTicketsByAgentName(String name){
        return Collections.unmodifiableList(ticketsMap.values().stream()
                .filter(l2 -> l2.getAgentName().equals(name))
                .sorted((Ticket t1 , Ticket t2) -> t2.getCreated().compareTo(t1.getCreated()))
                .collect(Collectors.toList()));
    }

    /**
     * function to fetch all Tickets in the system
     * @return List of tickets
     */
    public List<Ticket> getAllTickets(){
        return Collections.unmodifiableList(ticketsMap.values().stream()
                .sorted((Ticket t1, Ticket t2) -> t2.getModified().compareTo(t1.getModified()))
                .collect(Collectors.toList()));
    }

    /**
     * function to fetch count of ticket with respect to its agent.
     * Data is grouped by Agent name in ascending order.
     */
    public Map<String,List<Ticket>> getTicketsGroupByAgent(){
        return Collections.unmodifiableMap(new TreeMap<>(ticketsMap.values().stream()
                .collect(Collectors.groupingBy(Ticket::getAgentName))));

    }

    /**
     * function to display Ticket Map data
     */
    public void printTicketMap(Map<String, List<Ticket>> ticketsMap) {
        ticketsMap.forEach((String agent, List<Ticket> tickets)-> System.out.println(agent+" : "+tickets.size()));
    }

    /**
     * Function to fetch all ticket with respect to Tag.
     */
    public List<Ticket> getAllTicketsByTag(String tag){
        return Collections.unmodifiableList(ticketsMap.values().stream()
                .filter(list -> list.getTags().contains(tag))
                .collect(Collectors.toList()));
    }

    /**
     * function to display or print tickets
     * @param tickets
     */
    public void display(List<Ticket> tickets){
        System.out.println(ConsoleScan.ACT_TABLE_HEADER);
        tickets.stream().forEach(ticket -> System.out.println(ticket));
    }

    public boolean checkIfExists(int id){
        return ticketsMap.containsKey(id);
    }

    /**
     * function to create Single Ticket dummy date to write to file
     * @return Ticket
     */
    public Ticket prepareTicketDataForFile(){
        String sub = "Test Subject";
        String agent = "agent1";
        Set<String> set = new HashSet<String>();
        set.add("Tag1");
        set.add("Tag2");

        Ticket ticket = TicketFactory.createNewInstance(sub,agent,set);
        return ticket;
    }

    /**
     * function to create dummy date of multiple tickets to write to file
     * @return List of Ticket objects
     */
    public List<Ticket> prepareMultipleTicketDataForFile(Ticket ticket){
        List<Ticket> listTickets = new ArrayList<>();
        for (int i = 1; i <=5; i++){
            Set<String> set = new HashSet<String>();
            String sub = "Test Subject"+i;
            String agent = "agent"+i;

                if (i > 3)
                    set.add("Tag"+i);
                else
                    set.add("Tag");

            listTickets.add(new Ticket(i,sub,set,agent,true));
        }
        return listTickets;
    }

    /**
     * function to return Total number of Tickets in the System
     * @param list
     */
    public void getNumberOfTicketsInSystem(List<Ticket> list){
        System.out.println("Total Tickets present are :"+list.size());
    }

    /**
     * function to fetch the Oldest ticket in the system
     * @param list
     */
    public void getOldestTicketInSystem(List<Ticket> list){
        Comparator<Ticket> sortByAscTickets = (e1, e2) -> e1.getModified().compareTo(e2.getModified());
        Ticket ticket = list.stream()
            .sorted(sortByAscTickets).findFirst().get();

        System.out.println("\nOldest Ticket in the System:");
        System.out.println(ticket);
    }

    /**
     * function to fetch count of each tag used by tickets in the system
     * @param list
     */
    public void getCountOfTags(List<Ticket> list){
        Map<String, Integer> map = new TreeMap<>();
        int i = 1;
        for (Ticket t: list) {
            Set<String> tags = t.getTags();
            for (String s : tags) {
                if (map.containsKey(s)) {
                    i = map.get(s)+1;
                    map.put(s, i);
                } else {
                    map.put(s, 1);
                }
            }
        }
        System.out.println("\nTotal count of Tags used in tickets:");
        map.forEach((String tag, Integer count) -> System.out.println(tag+" : "+count));

    }


    /**
     * function to fetch tickets older than certain number of days.
     * @param noOfDays
     * @param ticketList
     */
    public List<Ticket> getDateDiff(int noOfDays, List<Ticket> ticketList){

        return ticketList.stream()
                .filter(ticket1 -> ticket1.getCreated().isBefore(LocalDateTime.now().minusDays(noOfDays)))
                .collect(Collectors.toList());
    }
}
