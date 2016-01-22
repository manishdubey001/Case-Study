package com.ticket;
import java.util.*;

/**
 * Created by root on 18/1/16.
 */
public class TicketBase {
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
    public HashMap<String, Integer> ticketCountByAgent(){
        if(ticketList.size() > 0) {
            ArrayList<String> arrListAgentName = new ArrayList<String>();
            for (int i=0; i< ticketList.size(); i++){
                arrListAgentName.add(ticketList.get(i).getAgentName());
            }
            TreeSet<String> sortedAgentName = new TreeSet<String>(arrListAgentName);
            HashMap<String, Integer> agentTicketCount = new HashMap<String, Integer>();
            for (String key : sortedAgentName){
                agentTicketCount.put(key, Collections.frequency(arrListAgentName, key));
            }
            return agentTicketCount;
        }
        return null;
    }
}
