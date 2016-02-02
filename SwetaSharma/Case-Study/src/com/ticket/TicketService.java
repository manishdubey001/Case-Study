package com.ticket;
import java.util.*;

/**
 * Created by root on 18/1/16.
 */

// "Base" usually means "Base Class". I would call this TicketService or TicketStore or something similar.
public class TicketService {
    // Just store this as a List<Ticket>
    // Also, consider if List is the right structure at all--it means you always have to search (potentially)
    // the whole list to find a ticket.

    //UPDATE
    static Map<Integer, Ticket> ticketList = new HashMap<>();
    /**
     * Create ticket
     * @param ticketId
     * @param subject
     * @param agentName
     * @param setOfTags
     * @return ticket object
     */
    public Ticket createTicket(int ticketId, String subject, String agentName, Set<String> setOfTags){
        if(ticketList.get(ticketId) == null){
            //UPDATE
            Ticket ticketModel = Ticket.newInstance(ticketId, subject, agentName, new HashSet<>(setOfTags));
            ticketList.put(ticketModel.getId(), ticketModel);
            return ticketModel;
        }
        return null;
    }

    /**
     * Get ticket detail by id
     * @param ticketId
     * @return ticket object
     */
    public Ticket detail(int ticketId){
        // no reason for the if statement; the for loop
        // will not execute if the condition fails.

        //Also, the more modern way to iterate would be
        // for(Ticket t : ticketList) { if (t.getId() == ticketId) ...
        // But see comments above about choosing a different
        // collection for tickets.

        //UPDATE
        return ticketList.get(ticketId);
    }

    /**
     * Update ticket by id
     * @param ticketId
     * @param agentName
     * @param setOfTags
     * @return ticket object
     */
    public Ticket updateTicket(int ticketId, String agentName, Set<String> setOfTags){
        Ticket ticket = ticketList.get(ticketId);
        if(ticket != null){
            if(agentName != null){
                ticket.setAgentName(agentName);
            }if(setOfTags.size() > 0){
                ticket.setTags(setOfTags);
            }
            return ticket;
        }
        return null;
    }

    /**
     * Delete / Remove ticket by id
     * @param ticketId
     * @return boolean
     */
    public boolean deleteTicket(int ticketId){
        //UPDATE
        if(ticketList.remove(ticketId) != null){
            return true;
        }else{
            return false;
        }
    }

    /**
     * List all tickets, search by agent name, search by tag and sorted by modified date
     * @return list of tickets
     */
    // Generally return interfaces rather than concrete collection types;
    // in this case return a List<Ticket>
    // Also, I would not use a 'searchType' parameter. I would implement separate methods
    // (you could implement a common method with a comparator or Predicate.

    //UPDATE
    public List<Ticket> defaultListTicket(){
        List<Ticket> ticketValues = new ArrayList<>(ticketList.values());
        Collections.sort(ticketValues);
        return ticketValues;
        /*for (Ticket ticket : ticketValues){
            lhmListTickets.put(ticket.getId(), ticket);
        }*/
        //ticketValues.forEach(ticket -> lhmListTickets.put(ticket.getId(), ticket));
    }

    public List<Ticket> agentListTicket(String agentName){
        /*List<Ticket> agentTicketList = new ArrayList<>();
        List<Ticket> ticketValues = new ArrayList<>(ticketList.values());
        for (Ticket ticket : ticketValues){
            if(ticket.getAgentName().equalsIgnoreCase(agentName)){
                agentTicketList.add(ticket);
            }
        }
        Collections.sort(agentTicketList);
        return agentTicketList;*/
        List<Ticket> ticketValues = new ArrayList<>(ticketList.values());
        return TicketPredicates.filterTickets(ticketValues, TicketPredicates.isAgentPresent(agentName));
    }

    public List<Ticket> tagListTicket(String tags){
        /*List<Ticket> tagTicketList = new ArrayList<>();
        List<Ticket> ticketValues = new ArrayList<>(ticketList.values());
        for (Ticket ticket : ticketValues){
            if(ticket.getTags().contains(tags)){
                tagTicketList.add(ticket);
            }
        }
        Collections.sort(tagTicketList);
        return tagTicketList;*/
        List<Ticket> ticketValues = new ArrayList<>(ticketList.values());
        return TicketPredicates.filterTickets(ticketValues, TicketPredicates.isTagPresent(tags));
    }

    /**
     * Ticket count group by agent name order by agent name
     */
    public Map<String, Integer> ticketCountByAgent(){
        //ignore case pending for you to check
            // note that on the right-hand side you do not need to
            // specify the parameter again; you can just say "new ArrayList<>();"
            ArrayList<String> arrListAgentName = new ArrayList<>();
            /*for(Ticket ticket : ticketList.values()){
                arrListAgentName.add(ticket.getAgentName());
            }*/
            //UPDATE
            ticketList.values().forEach(ticket -> arrListAgentName.add(ticket.getAgentName()));

            TreeSet<String> sortedAgentName = new TreeSet<>(arrListAgentName);
            Map<String, Integer> agentTicketCount = new HashMap<>();
            /*for (String key : sortedAgentName){
                // good use of frequency()
                agentTicketCount.put(key, Collections.frequency(arrListAgentName, key));
            }*/
            //UPDATE
            sortedAgentName.forEach(s -> agentTicketCount.put(s, Collections.frequency(arrListAgentName, s)));
            return agentTicketCount;
    }
}
