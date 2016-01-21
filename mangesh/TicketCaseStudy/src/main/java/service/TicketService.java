package service;


import factory.TicketFactory;
import model.Ticket;

import java.util.*;

/**
 * Created by root on 18/1/16.
 */
public class TicketService {

    ArrayList<Ticket> masterTicketData = new ArrayList();
    HashMap<String, Integer> agentTicketsTotalCounts = new HashMap<String, Integer>(); // Storing ticket counts against agents name
    HashMap<String, HashSet> tagsWithTicketIds = new HashMap<String, HashSet>();  // storing ticket ids against tag name

    /**
     * Creating a new ticket
     * Adding tags
     * Updating ticket count against agent
     */
    public int createTicket(int ticketId, String subject, String agentName, List<String> tagsList) {

        if (subject.isEmpty() || agentName.isEmpty()) {
            return 0;
        }
      //  System.out.println("tag list in create ticket service function  :: " + tagsList);
        if (!tagsList.isEmpty()) {
            this.updateTags(tagsList, ticketId);
        }

        Date date = new Date();
        Ticket ticket = TicketFactory.newInstance(ticketId, subject, agentName, tagsList);
        //Ticket ticket = new Ticket();

        ticket.setId(ticketId);
        ticket.setSubject(subject);
        ticket.setAgent_name(agentName);
        ticket.setTags(tagsList);
        ticket.setCreated(date);
        ticket.setModified(date);

        this.masterTicketData.add(ticket);
        this.updateAgentsTicketCount(agentName, 1); // increment ticket count against agent name
        return ticketId;
    }

    /**
     * Get the details of ticket by id
     * @param id
     * @return
     */
    public HashMap getTicketDetailsById(int id) {
        if(!this.isTicketExist(id)){
            return  null;
        }
        HashMap TicketDetails = new HashMap();
        for (Ticket ticket : masterTicketData) {
            if (ticket.getId() == id) {
                TicketDetails.put("id", ticket.getId());
                TicketDetails.put("subject", ticket.getSubject());
                TicketDetails.put("agentName", ticket.getAgent_name());
                TicketDetails.put("tags", ticket.getTags());
                TicketDetails.put("created", ticket.getCreated());
                TicketDetails.put("modified", ticket.getModified());
                break;
            }
        }
        return TicketDetails;
    }

    /**
     * Adding / updating ticket ids into tag set
     *
     * @param tagsList
     * @param ticketId
     */
    public void updateTags(List<String> tagsList, int ticketId) {
        for (String tag : tagsList) {
            if (this.tagsWithTicketIds.containsKey(tag)) {
                HashSet<Integer> idSet = new HashSet<Integer>();
                idSet = tagsWithTicketIds.get(tag);
                idSet.add(ticketId);
                this.tagsWithTicketIds.put(tag, idSet);
            } else {
                HashSet<Integer> idSet = new HashSet<Integer>();
                idSet.add(ticketId);
                this.tagsWithTicketIds.put(tag, idSet);
            }
        }
    }

    /**
     * Updating Ticket count for a agent
     *
     * @param agent
     * @param action
     */
    public void updateAgentsTicketCount(String agent, int action) {
        int agentCount = 0;
        if (agentTicketsTotalCounts.containsKey(agent)) {
            agentCount = agentTicketsTotalCounts.get(agent);
            if (action == 1) // add
                agentCount = agentCount + 1;
            else if (action == 0) // remove
                agentCount = agentCount - 1;
        } else if (action == 1)
            agentCount = 1;

        agentTicketsTotalCounts.put(agent, agentCount);
    }

    /**
     * showing all ticket details
     */
    public List<Ticket> showAllTickets() {
        return masterTicketData;
    }

    /**
     * Displaying ticket details
     * @param ticketList
     */
    public void showTickets(List<Ticket> ticketList){
        if (ticketList.isEmpty()) {
            System.out.println("No Tickets Found!!!");
        } else {
            Collections.sort(ticketList);
            System.out.println("Id | Subject | Agent Name | Tags | Created | Modified");
            for (Ticket ticket : ticketList) {
                System.out.println(ticket.getId() + " | " + ticket.getSubject() + " | " + ticket.getAgent_name() + " | " + ticket.getTags() + " | " +
                        ticket.getCreated() + " | " + ticket.getModified());
            }
        }
    }

    /**
     * Updating agent name
     * @param id
     * @param agentName
     * @return
     */
    public boolean updateTicketAgent(int id, String agentName) {
        if(agentName.isEmpty() || agentName == null)
            return false;

        Date date = new Date();
        for (Ticket ticket : masterTicketData) {
            if (ticket.getId() == id) {
                ticket.setAgent_name(agentName);
                ticket.setModified(date);
                break;
            }
        }

        return true;
    }

    /**
     * Updating ticket tags
     * @param id
     * @param tags
     * @return
     */
    public boolean updateTicketTags(int id, List<String> tags) {

        Date date = new Date();
        for (Ticket ticket : masterTicketData) {
            if (ticket.getId() == id) {
                // update tags
                if (!tags.isEmpty()) {
                    this.updateTags(tags, id);
                }

                // remove tags
                List<String> oldTicketTags = ticket.getTags();
                this.removeTicketTags(oldTicketTags, id);

                ticket.setTags(tags);
                ticket.setModified(date);
                break;
            }
        }
        return true;
    }

    /**
     * Removing ticket id from tags set
     *
     * @param oldTicketTags
     * @param ticketId
     */
    public void removeTicketTags(List<String> oldTicketTags, int ticketId) {
        for (String tag : oldTicketTags) {
            if (oldTicketTags.contains(tag)) {
                HashSet allTags = this.tagsWithTicketIds.get(tag);
                allTags.remove(ticketId);
            }
        }

    }

    /**
     * check given ticket is present or not in the system.
     * @param ticketId
     * @return
     */
    public boolean isTicketExist(int ticketId) {
        //System.out.println("ic check :: "  + masterTicketData.get(ticketId));

        for (Ticket ticket : masterTicketData){
            if(ticket.getId() == ticketId)
                return true;
        }
        return false;
    }

    /**
     * deleting a ticket by id
     * @param id
     * @return
     */
    public boolean deleteTicket (int id) {

        boolean flag = false;

        for(Ticket ticket : masterTicketData) {
            if (ticket.getId() == id) {
                masterTicketData.remove(ticket);
                flag = true;
                this.updateAgentsTicketCount(ticket.getAgent_name(), 0); // decrement ticket count against agent name
                break;
            }
        }
        return flag;
    }

    /**
     * getting tickets assigned to specific agent
     * @param agentName
     * @return
     */
    public ArrayList<Ticket> getTicketByAgentName(String agentName){
        ArrayList<Ticket> tempList = new ArrayList<Ticket>();
        agentName.trim();

        for (Ticket ticket : masterTicketData) {
            if(ticket.getAgent_name().equals(agentName)){
                tempList.add(ticket);
            }
        }
        return tempList;
    }

    /**
     * Updating tickets count to maintain the ticket(assigned to agent) counts of agent
     */
    public void ticketCountsByAgentName() {
        System.out.println("Agent Count  |   Total Count");

        Set setOfKeys = agentTicketsTotalCounts.keySet();
        Iterator it = setOfKeys.iterator();

        while (it.hasNext()){
            String agentName = (String) it.next();
            Integer count = (Integer) agentTicketsTotalCounts.get(agentName);
            System.out.println(agentName + "     |     " + count);
        }
    }

    /**
     * Serching tickets by tag name
     * @param tag
     * @return
     */
    public HashSet searchTicketsByTagName(String tag){
        HashSet ids = new HashSet();
        if(this.tagsWithTicketIds.containsKey(tag)) {
            ids = tagsWithTicketIds.get(tag);
        }
        return ids;
    }

    /**
     * Showing all tickets by tag
     * @param ids
     */
    public void showTicketsByTag(HashSet ids){

        if(ids.isEmpty() ||  masterTicketData.isEmpty()){
            System.out.println("No ticket(s) found in the system");
        }
        else {
            System.out.println("Id | Subject | Agent Name | Tags | Created | Modified");
            for (Ticket ticket : masterTicketData){
                if(ids.contains(ticket.getId())){
                    System.out.println(ticket.getId() + " | " + ticket.getSubject() + " | " + ticket.getAgent_name() + " | " + ticket.getTags() + " | " +
                            ticket.getCreated() + " | " + ticket.getModified());
                }
            }
        }
    }
}



