package service;

import com.sun.istack.internal.NotNull;
import factory.TicketFactory;
import model.Ticket;

import java.security.InvalidParameterException;
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

    public boolean deleteTicket(@NotNull int id) {
        if(masterTicketsData.isEmpty())     return false;
        Ticket ticket = masterTicketsData.remove(id);
        return (ticket != null) ? true : false;
    }

    public Ticket getTicketDetails(int id){
        // Lokesh: It may return Null. It forces callers for Null check. Rather then returning null, through an exception. -- Done
        Ticket ticket = masterTicketsData.get(id);
        if(ticket == null) throw new InvalidParameterException();
        return ticket;
    }

    public List<Ticket> getTicketsByAgentName(String agentName){
        // Lokesh: it may return null, return empty collection or throw exception in place of null -- Done
        if (masterTicketsData.isEmpty())  return new ArrayList<>();

        return Collections.unmodifiableList(masterTicketsData.values()
                                    .stream()
                                    .filter(ticket -> ticket.getAgentName().toLowerCase().equals(agentName.toLowerCase()))
                                    .collect(Collectors.toList()));
        }

    public List<Ticket> getTicketsByTag(String tag){
        if(masterTicketsData.isEmpty())  return new ArrayList<>();

        return Collections.unmodifiableList(masterTicketsData.values()
                                        .stream()
                                        .filter(ticket -> ticket.getTags().contains(tag.toLowerCase()))
                                        .collect(Collectors.toList()));
        }

    public Map<String , List<Ticket>> getTicketCounts(){
        if(masterTicketsData.isEmpty())    return new HashMap<>();
        return Collections.unmodifiableMap(masterTicketsData.values().stream().collect(Collectors.groupingBy(Ticket::getAgentName)));
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

        return ticket;
    }

//    Lokesh: Return Set(Map) interface instead of concrete HashMap. Applied some where, missed here.
    public Map<Integer, Ticket> getAllTickets(){
        return Collections.unmodifiableMap(masterTicketsData);
    }
}
