package service;

import com.sun.istack.internal.NotNull;
import factory.TicketFactory;
import model.Ticket;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


public class TicketService {
    private HashMap<Integer, Ticket> masterTicketsData = null;
    int ticketId = 100;
    public TicketService(){
        masterTicketsData = new HashMap<Integer, Ticket>();
    }

    public Ticket createTicket(String subject, String agentName, HashSet<String> tagSet){
        Ticket ticket = TicketFactory.newInstanceWithData(ticketId, subject, agentName, tagSet);
        masterTicketsData.put(ticketId,ticket);
        ticketId++;
        return ticket;
    }

    public boolean deleteTicket(int id) {
        Ticket ticket = masterTicketsData.remove(id);
        return (ticket != null) ? true : false;
    }

    public Ticket getTicketDetails(int id){
        return masterTicketsData.get(id);
    }

    public List<Ticket> getTicketsByAgentName(String agentName){
        return masterTicketsData.values().stream().filter(ticket -> ticket.getAgentName().toLowerCase().equals(agentName.toLowerCase())).collect(Collectors.toList());
    }

    public List<Ticket> getTicketsByTag(String tag){
        return masterTicketsData.values().stream().filter(ticket -> ticket.getTags().contains(tag.toLowerCase())).collect(Collectors.toList());
    }

    public Map<String , List<Ticket>> getTicketCounts(){
        return masterTicketsData.values().stream().collect(Collectors.groupingBy(Ticket::getAgentName));
    }

    public boolean isTicketExist(int id){
        return masterTicketsData.containsKey(id);
    }

    public Ticket update(int id, @NotNull String agentName, HashSet<String> tags, String action){
        Ticket ticket = masterTicketsData.get(id);
        if(!agentName.isEmpty())
            ticket.setAgentName(agentName);

        if(action.equals("A")){  // Adding new  tags
            tags.addAll(ticket.getTags());
            ticket.setTags(tags);
        }
        else if(action.equals("R")){  // remove tags
            HashSet<String> oldTags = new HashSet<>();
            oldTags.addAll(ticket.getTags());
            ticket.getTags().forEach((tag)->{
                    if(tags.contains(tag)){
                        oldTags.remove(tag);
                }
            });
            ticket.setTags(oldTags);
        }
        ticket.setModified(LocalDateTime.now());

        return ticket;
    }

    public HashMap<Integer, Ticket> getAllTickets(){
        return masterTicketsData;
    }
}
