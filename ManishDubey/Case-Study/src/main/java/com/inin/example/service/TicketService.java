package com.inin.example.service;

import com.inin.example.factory.TicketFactory;
import com.inin.example.model.Ticket;
import com.inin.example.util.TicketSerializationUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by root on 31/12/15.
 */
public class TicketService {

//    private Map<Integer,Ticket> tickets = null;

    public TicketService(){
//        tickets = new HashMap<>();
    }
    /**
     * Create new ticket by taking user input
     */
    public Ticket create(String subject, String agentName, HashSet<String> tags)
    {
        if(subject == null || subject.isEmpty() || agentName == null || agentName.isEmpty())
            return null;
        Ticket ticket = TicketFactory.newInstance(subject, agentName, tags);
        Map<Integer,Ticket>  tickets = new HashMap<>();
        tickets.put(ticket.getId(),ticket );
        TicketSerializationUtil.serializedTickets(tickets, true);
        return ticket.copy(ticket);
    }



    /**
     * Delete Ticket by id
     */
    public boolean delete(int id){
        Map<Integer,Ticket> tickets = TicketSerializationUtil.deserializedTickets();
        Ticket ticket = tickets.remove(id);
        if(ticket !=null)
        {
            TicketSerializationUtil.serializedTickets(tickets, false);
            return true;
        }
        return false;
    }

    /**
     * Update ticket by id
     */
    public Ticket update(int id,String agentName, HashSet<String> tags)
    {
        Map<Integer,Ticket> tickets = TicketSerializationUtil.deserializedTickets();
        Ticket ticket = tickets.get(id);
        boolean modified = false;
        if (ticket != null) {
            if (agentName != null && !agentName.isEmpty()) {
                ticket.setAgentName(agentName);
                modified = true;
            }
            if (tags != null) {
                ticket.setTags(tags);
                modified = true;
            }
            if(modified)
                TicketSerializationUtil.serializedTickets(tickets, false);
        }
        return ticket.copy(ticket);
    }

    /**
     * Display single ticket by id
     */
    public Ticket ticket(int id)
    {
        Map<Integer,Ticket> tickets = TicketSerializationUtil.deserializedTickets();
        System.out.println(tickets);
        System.out.println(tickets.get(id));
        if(tickets.get(id) != null)
         return tickets.get(id).copy(tickets.get(id));
        return null;
    }

    /**
     * Display all ticket on console
     */
    public List<Ticket> tickets()
    {
        Map<Integer,Ticket> tickets = TicketSerializationUtil.deserializedTickets();
        return Collections.unmodifiableList(tickets.values()
                .stream()
                .sorted((Ticket o1, Ticket o2) -> o2.getModified().compareTo(o1.getModified()))
                .collect(Collectors.toList()));
    }

    /**
     * Return the Ticket By Agent
     * @param agentName
     * @return List<Ticket>
     */
    public List<Ticket> ticketsByAgent(String agentName){
        Map<Integer,Ticket> tickets = TicketSerializationUtil.deserializedTickets();
        return Collections.unmodifiableList(tickets.values()
                .stream()
                .filter(ticket -> ticket.getAgentName().toLowerCase().equals(agentName.toLowerCase()))
                .sorted((Ticket o1, Ticket o2) -> o2.getModified().compareTo(o1.getModified()))
                .collect(Collectors.toList()));
    }

    /**
     * Return the Ticket By Tag
     * @param tag
     * @return List<Ticket>
     */
    public List<Ticket> ticketsByTag(String tag){
        Map<Integer,Ticket> tickets = TicketSerializationUtil.deserializedTickets();
        return Collections.unmodifiableList(tickets.values()
                .stream()
                .filter(ticket -> ticket.getTags().contains(tag.toLowerCase()))
                .sorted((Ticket o1, Ticket o2) -> o2.getModified().compareTo(o1.getModified()))
                .collect(Collectors.toList()));
    }

    /**
     * Return Ticket group by Agent
     * @return
     */
    public Map<String,List<Ticket>> ticketsGroupByAgent()
    {
        Map<Integer,Ticket> tickets = TicketSerializationUtil.deserializedTickets();
        return Collections.unmodifiableMap(new TreeMap<>(
                    tickets.values()
                    .stream()
                    .collect(Collectors.groupingBy(Ticket::getAgentName))));
    }

    /**
     * Check provided id exists or not
     * @param id
     * @return boolean
     */
    public boolean isTicketExist(int id)
    {
        Map<Integer,Ticket> tickets = TicketSerializationUtil.deserializedTickets();
        return tickets.containsKey(id);
    }


    /**
     * Load dummy ticket
     * @param noOfTickets
     */
    public void loadDummyTickets(int noOfTickets){
        System.out.println("This feature is not implemented");
    }
}
