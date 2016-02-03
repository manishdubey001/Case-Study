package service;

import Serialization.TicketSerialization;
import com.sun.istack.internal.NotNull;
import factory.TicketFactory;
import model.Ticket;
import util.Util;

import java.util.*;
import java.util.stream.Collectors;


public class TicketService {
    public TicketService(){}

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
        Ticket ticket = masterTicketsData.remove(id);
        if(ticket!=null) {
            TicketSerialization.serialize(masterTicketsData, false);
            return true;
        }
        return false;
    }

    public Ticket getTicketDetails(int id){
        Map<Integer, Ticket> masterTicketsData = TicketSerialization.deserialize();
        return masterTicketsData.get(id);
    }

    public List<Ticket> getTicketsByAgentName(String agentName){
        Map<Integer, Ticket> masterTicketsData = TicketSerialization.deserialize();
        return masterTicketsData.values().stream().filter(ticket -> ticket.getAgentName().toLowerCase().equals(agentName.toLowerCase())).collect(Collectors.toList());
    }

    public List<Ticket> getTicketsByTag(String tag){
        Map<Integer, Ticket> masterTicketsData = TicketSerialization.deserialize();
        return masterTicketsData.values().stream().filter(ticket -> ticket.getTags().contains(tag.toLowerCase())).collect(Collectors.toList());
    }

    public Map<String , List<Ticket>> getTicketCounts(){
        Map<Integer, Ticket> masterTicketsData = TicketSerialization.deserialize();
        return masterTicketsData.values().stream().collect(Collectors.groupingBy(Ticket::getAgentName));
    }

    public boolean isTicketExist(int id){
        Map<Integer, Ticket> masterTicketsData = TicketSerialization.deserialize();
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
        return TicketSerialization.deserialize();
    }
}
