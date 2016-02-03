package com.inin.example.service;

import com.inin.example.exception.TicketNotFound;
import com.inin.example.factory.TicketFactory;
import com.inin.example.model.Ticket;
import com.inin.example.util.TicketSerializationUtil;
import com.inin.example.util.TicketUtil;

import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by root on 31/12/15.
 */
public class TicketService {

    private Map<Integer,Ticket> tickets = new HashMap<>();

    /**
     * Create new ticket by taking user input
     */
    public Ticket create(String subject, String agentName, Set<String> tags) throws InvalidParameterException
    {
        if(!TicketUtil.isValidString(subject) || !TicketUtil.isValidString(agentName))
            throw new InvalidParameterException();
        tags = tags != null ? new HashSet<>(tags): new HashSet<>();
        Ticket ticket = TicketFactory.newInstance(subject, agentName, tags);
        tickets.put(ticket.getId(),ticket );
        return TicketFactory.newInstance(ticket);
    }
    /**
     * Delete Ticket by id
     */
    public boolean delete(int id){
        return tickets.remove(id) != null ? true : false;
    }

    /**
     * Update ticket by id
     */
    public Ticket update(int id,String agentName, Set<String> tags) throws InvalidParameterException
    {
        Ticket ticket = tickets.get(id);
        if(ticket == null ||( !TicketUtil.isValidString(agentName) && !TicketUtil.isValidCollection(tags)))
            throw new InvalidParameterException();

        if (TicketUtil.isValidString(agentName))
            ticket.setAgentName(agentName);
        if (TicketUtil.isValidCollection(tags)) {
            ticket.getTags().clear();
            ticket.getTags().addAll(tags);
        }

        return TicketFactory.newInstance(ticket);
    }

    /**
     * Display single ticket by id
     */
    public Ticket ticket(int id) throws InvalidParameterException
    {
        Ticket ticket = tickets.get(id);
        if(ticket == null)
            throw new InvalidParameterException();
        return ticket;
    }

    /**
     * Return Ticket List
     */
    public List<Ticket> tickets()
    {
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
        return tickets.containsKey(id);
    }

    /**
     * Return the total count of Tickets
     * @return int
     */
    public int totalTicketCount()
    {
        return tickets.size();
    }

    /**
     * Return the oldest Ticket
     * @return Ticket
     */
    public Ticket oldestTicket()
    {
        if(tickets.isEmpty())
            throw new TicketNotFound("No Ticket In the system");

        return TicketFactory.newInstance(
                tickets.values()
                .stream()
                .sorted((Ticket t1, Ticket t2) -> t1.getCreated().compareTo(t2.getCreated()))
                .findFirst()
                .get()
            );
    }

    /**
     * Return Ticket By Date
     * @param date
     * @return List<Ticket>
     */
    public List<Ticket> ticketOlderByDate(LocalDateTime date){
        return Collections.unmodifiableList(tickets.values()
                .stream()
                .filter(ticket -> date.compareTo(ticket.getCreated()) >= 0 )
                .collect(Collectors.toList()));
    }

    /**
     * Return Ticket group by tags
     * @return Map<String, List<Ticket>>
     */
    public Map<String, Integer> ticketsGroupByTag(){
        Map<String,Integer> ticketByTag = new HashMap<>();
        tickets.values()
                .stream()
                .forEach(ticket -> {
                    ticket.getTags().forEach( tag -> {
                        if(ticketByTag.containsKey(tag))
                            ticketByTag.put(tag,ticketByTag.get(tag)+1);
                        else {
                            List<Ticket> list = new ArrayList<>();
                            list.add(ticket);
                            ticketByTag.put(tag, 1);
                        }
                    });
                });
        return Collections.unmodifiableMap(ticketByTag);

    }

    /**
     * Load dummy ticket
     * @param noOfTickets
     */
    public void loadDummyTickets(int noOfTickets){
        Random rd = new Random();
        for (int i = 1;i <= noOfTickets; i++){
            Set<String > tags = new HashSet<>();
            tags.add("tag"+rd.nextInt(100));
            tags.add("tag"+rd.nextInt(100));
            Ticket ticket = TicketFactory.newInstance("Subject"+rd.nextInt(100),"Agent"+rd.nextInt(100),tags);
            tickets.put(ticket.getId(),ticket);
        }
    }
}
