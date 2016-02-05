package service;

import Serialization.TicketSerialization;
import com.sun.istack.internal.NotNull;
import factory.TicketFactory;
import model.Ticket;
import util.Util;

import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class TicketService {
    public Ticket createTicket(String subject, String agentName, HashSet<String> tagSet){
        Ticket ticket = TicketFactory.newInstanceWithData(subject, agentName, tagSet);
        Util.writeTicketId(ticket.getId());
        Map<Integer, Ticket> masterTicketsData = new HashMap<>();
        masterTicketsData.put(ticket.getId(),ticket);
        TicketSerialization.serialize(masterTicketsData, true);
        return ticket;
    }

    public boolean deleteTicket(int id) {
        Map<Integer, Ticket> masterTicketsData = TicketSerialization.deserialize();
        if(masterTicketsData.isEmpty())     return false;
        Ticket ticket = masterTicketsData.remove(id);
        return (ticket != null) ? true : false;
    }

    public Ticket getTicketDetails(int id){
        Map<Integer, Ticket> masterTicketsData = TicketSerialization.deserialize();
        Ticket ticket = masterTicketsData.get(id);
        if(ticket == null) throw new InvalidParameterException();
        return ticket;
    }

    public List<Ticket> getTicketsByAgentName(String agentName){
        Map<Integer, Ticket> masterTicketsData = TicketSerialization.deserialize();
        if (masterTicketsData.isEmpty())  return new ArrayList<>();
        return Collections.unmodifiableList(masterTicketsData.values()
                .stream()
                .filter(ticket -> ticket.getAgentName().toLowerCase().equals(agentName.toLowerCase()))
                .collect(Collectors.toList()));
    }

    public List<Ticket> getTicketsByTag(String tag){
        Map<Integer, Ticket> masterTicketsData = TicketSerialization.deserialize();
        if(masterTicketsData.isEmpty())  return new ArrayList<>();
        return Collections.unmodifiableList(masterTicketsData.values()
                .stream()
                .filter(ticket -> ticket.getTags().contains(tag.toLowerCase()))
                .collect(Collectors.toList()));
    }

    public Map<String , List<Ticket>> getTicketCounts(){
        Map<Integer, Ticket> masterTicketsData = TicketSerialization.deserialize();
        if(masterTicketsData.isEmpty())    return new HashMap<>();
        return Collections.unmodifiableMap(masterTicketsData.values().stream().collect(Collectors.groupingBy(Ticket::getAgentName)));
    }

    public boolean isTicketExist(int id){
        Map<Integer, Ticket> masterTicketsData = TicketSerialization.deserialize();
        if(masterTicketsData.isEmpty()) return false;
        return masterTicketsData.containsKey(id);
    }

    public Ticket update(int id, @NotNull String agentName, HashSet<String> tags, String action) {
        Map<Integer, Ticket> masterTicketsData = TicketSerialization.deserialize();
        boolean modifiedFlag = false;
        Ticket ticket = masterTicketsData.get(id);
        if (!agentName.isEmpty()){
            ticket.setAgentName(agentName);
            modifiedFlag = true;
        }

        if(action.equals("A")){  // Adding new  tags
            tags.addAll(ticket.getTags());
            ticket.setTags(tags);
            modifiedFlag = true;
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
            modifiedFlag = true;
        }

        if(modifiedFlag)
            TicketSerialization.serialize(masterTicketsData, false);
        return ticket;
    }

    public Map<Integer, Ticket> getAllTickets(){
        return Collections.unmodifiableMap(TicketSerialization.deserialize());
    }

    public int getTotalTicketCounts() {
        Map<Integer, Ticket> masterTicketsData = TicketSerialization.deserialize();
        if(masterTicketsData.isEmpty()) return 0;
        else
            return TicketSerialization.deserialize().size();
    }

    public Ticket getOldestTicket() {
        Map<Integer, Ticket> masterTicketsData = TicketSerialization.deserialize();
        if (masterTicketsData.isEmpty()) throw  new InvalidParameterException();
        return masterTicketsData.values().stream().sorted((Ticket t1, Ticket t2) -> t1.getCreated().compareTo(t2.getCreated())).findFirst().get();
       }

    public List<Ticket> getTicketOlderByDays(int day){
        Map<Integer, Ticket> masterTicketsData = TicketSerialization.deserialize();
        if(masterTicketsData.isEmpty()) return new ArrayList<>();
        LocalDateTime date = LocalDateTime.now().minusDays(day);
        return Collections.unmodifiableList(masterTicketsData.values().stream().filter(ticket -> date.compareTo(ticket.getCreated())>=0).collect(Collectors.toList()));
    }

    public Map<String, List<Ticket>> getTicketCountByTags() {
        Map<Integer, Ticket> masterTicketsData = TicketSerialization.deserialize();

        if(masterTicketsData.isEmpty()) return new HashMap<>();

        Map<String,List<Ticket>> ticketByTag = new HashMap<>();
        masterTicketsData.values()
                .stream()
                .forEach(ticket -> ticket.getTags().forEach(tag -> {
                    if(ticketByTag.containsKey(tag))
                        ticketByTag.get(tag).add(ticket);
                    else {
                        List<Ticket> list = new ArrayList<>();
                        list.add(ticket);
                        ticketByTag.put(tag, list);
                    }
                }));
        return Collections.unmodifiableMap(ticketByTag);
    }
}
