package com.ticket;
import java.util.*;

/**
 * Created by root on 18/1/16.
 */

// "Base" usually means "Base Class". I would call this TicketService or TicketStore or something similar.
public class TicketBase {
    // Just store this as a List<TicketModel>
    // Also, consider if List is the right structure at all--it means you always have to search (potentially)
    // the whole list to find a ticket.
    ArrayList<TicketModel> ticketList = new ArrayList<TicketModel>();
    /**
     * Create ticket
     * @param ticketId
     * @param subject
     * @param agentName
     * @param setOfTags
     * @return ticket object
     */
    public TicketModel createTicket(int ticketId, String subject, String agentName, HashSet<String> setOfTags){
        TicketModel ticketModel = TicketFactory.newInstance(ticketId, subject, agentName, setOfTags);
        ticketList.add(ticketModel);
        return ticketModel;
    }

    /**
     * Get ticket detail by id
     * @param ticketId
     * @return ticket object
     */
    public TicketModel detail(int ticketId){
        // no reason for the if statement; the for loop
        // will not execute if the condition fails.

        //Also, the more modern way to iterate would be
        // for(TicketModel t : ticketList) { if (t.getId() == ticketId) ...
        // But see comments above about choosing a different
        // collection for tickets.
        if(ticketList.size() > 0) {
            for (int i = 0; i < ticketList.size(); i++) {
                if (ticketList.get(i).getId() == ticketId) {
                    return ticketList.get(i);
                }
            }
        }
        return null;
    }

    /**
     * Update ticket by id
     * @param ticketId
     * @param agentName
     * @param setOfTags
     * @return ticket object
     */
    public TicketModel updateTicket(int ticketId, String agentName,  HashSet<String> setOfTags){
        if(ticketList.size() > 0) {
            for (int i = 0; i < ticketList.size(); i++) {
                if (ticketList.get(i).getId() == ticketId) {
                    if(agentName != null){
                        ticketList.get(i).setAgentName(agentName);
                    }
                    if(setOfTags.size() > 0){
                        ticketList.get(i).setTags(setOfTags);
                    }
                    return ticketList.get(i);
                }
            }
        }
        return null;
    }

    /**
     * Delete / Remove ticket by id
     * @param ticketId
     * @return boolean
     */
    public boolean deleteTicket(int ticketId){
        if(ticketList.size() > 0) {
            for (int i = 0; i < ticketList.size(); i++) {
                if (ticketList.get(i).getId() == ticketId) {
                    ticketList.remove(i);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * List all tickets, search by agent name, search by tag and sorted by modified date
     * @return list of tickets
     */
    // Generally return interfaces rather than concrete collection types;
    // in this case return a List<TicketModel>
    // Also, I would not use a 'searchType' parameter. I would implement separate methods
    // (you could implement a common method with a comparator or Predicate.
    public ArrayList<TicketModel> listTicket(int searchType, String ticketSearchType){
        if(ticketList.size() > 0){
            ArrayList<TicketModel> arrayListTickets =  new ArrayList<TicketModel>();
            switch (searchType){
                case 1:
                    for(int i=0; i < ticketList.size(); i++){
                        arrayListTickets.add(ticketList.get(i));
                    }
                    break;
                case 2:
                    for(int i=0; i < ticketList.size(); i++){
                        if(ticketList.get(i).getAgentName().equals(ticketSearchType)){
                            arrayListTickets.add(ticketList.get(i));
                        }
                    }
                    break;
                case 3:
                    for(int i=0; i < ticketList.size(); i++){
                        if(ticketList.get(i).getTags().contains(ticketSearchType)){
                            arrayListTickets.add(ticketList.get(i));
                        }
                    }
                    break;
            }
            Collections.sort(ticketList);
            return arrayListTickets;
        }
        return null;
    }

    /**
     * Ticket count group by agent name order by agent name
     */
    // return Map<String,Integer>
    public HashMap<String, Integer> ticketCountByAgent(){
        if(ticketList.size() > 0) {
            // note that on the right-hand side you do not need to
            // specify the parameter again; you can just say "new ArrayList<>();"
            ArrayList<String> arrListAgentName = new ArrayList<String>();
            for (int i=0; i< ticketList.size(); i++){
                arrListAgentName.add(ticketList.get(i).getAgentName());
            }
            TreeSet<String> sortedAgentName = new TreeSet<String>(arrListAgentName);
            HashMap<String, Integer> agentTicketCount = new HashMap<String, Integer>();
            for (String key : sortedAgentName){
                // good use of frequency()
                agentTicketCount.put(key, Collections.frequency(arrListAgentName, key));
            }
            return agentTicketCount;
        }
        return null;
    }
}
