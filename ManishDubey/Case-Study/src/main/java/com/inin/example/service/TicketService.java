package com.inin.example.service;

import com.inin.example.factory.TicketFactory;
import com.inin.example.model.Ticket;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by root on 31/12/15.
 */
public class TicketService {

    private HashMap<Integer,Ticket> tickets = null;

    public TicketService(){
        tickets = new HashMap<>();
    }
    /**
     * Create new ticket by taking user input
     */
    public Ticket create(String subject, String agentName, HashSet<String> tags)
    {
        if(subject == null || subject.isEmpty() || agentName == null || agentName.isEmpty())
            return null;
        Ticket ticket = TicketFactory.newInstance(subject, agentName, tags);
        tickets.put(ticket.getId(),ticket );
        return ticket;
    }



    /**
     * Delete Ticket by id
     */
    public boolean delete(int id){
        Ticket ticket = tickets.remove(id);
        return ticket != null ? true : false;
    }

    /**
     * Update ticket by id
     */
    public Ticket update(int id,String agentName, HashSet<String> tags)
    {
        Ticket ticket = tickets.get(id);
        if (ticket != null) {
            if (!agentName.isEmpty())
                ticket.setAgentName(agentName);
            if (tags != null)
                ticket.setTags(tags);
        }
        return ticket;
    }

    /**
     * Display single ticket by id
     */
    public Ticket getTicket(int id)
    {
        return tickets.get(id);
    }

    /**
     * Display all ticket on console
     */
    public HashMap<Integer,Ticket> getTickets()
    {
        return tickets;
    }

    /**
     * Return the Ticket By Agent
     * @param agentName
     * @return List<Ticket>
     */
    public List<Ticket> getTicketsByAgent(String agentName){
        return tickets.values()
                .stream()
                .filter(ticket -> ticket.getAgentName().toLowerCase().equals(agentName.toLowerCase()))
                .sorted((Ticket o1, Ticket o2) -> -o1.getModified().compareTo(o2.getModified()))
                .collect(Collectors.toList());
    }

    /**
     * Return the Ticket By Tag
     * @param tag
     * @return List<Ticket>
     */
    public List<Ticket> getTicketsByTag(String tag){
        return tickets.values()
                .stream()
                .filter(ticket -> ticket.getTags().contains(tag.toLowerCase()))
                .sorted((Ticket o1, Ticket o2) -> -o1.getModified().compareTo(o2.getModified()))
                .collect(Collectors.toList());
    }

    /**
     * Return Ticket group by Agent
     * @return
     */
    public Map<String,List<Ticket>> getTicketsGroupByAgent()
    {
        return tickets.values()
                    .stream()
                    .collect(Collectors.groupingBy(Ticket::getAgentName));
    }

    /**
     * Check provided id exists or not
     * @param id
     * @return boolean
     */
    public boolean isTicketExist(int id)
    {

        return tickets.containsKey(id);
    }

    /**
     * Load dummy ticket
     * @param noOfTickets
     */
    public void loadDummyTickets(int noOfTickets){
        Random rd = new Random();
        for (int i = 0; i < noOfTickets; i++){
            String agentName = "Agent"+rd.nextInt(1000);
            HashSet<String> tagsSet = new HashSet<>();
            tagsSet.add("tag"+rd.nextInt(1000));
            tagsSet.add("tag"+rd.nextInt(1000));
            Ticket ticket = TicketFactory.newInstance("Subject"+i, agentName , tagsSet);
            tickets.put(ticket.getId(),ticket);
        }
    }

}
