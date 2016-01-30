package service;


import factory.TicketFactory;
import model.Ticket;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by root on 18/1/16.
 */
public class TicketService {

    // Unchecked assignment, even though empty, it should be checked one or compatible.
    ArrayList<Ticket> masterTicketData = new ArrayList();

    // There can be n number of such statistical data requirements, one should not have separate collection to hold each of those. Rather use some logical computation to produce them on demand.
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

        // Why calling setters when they are already set through factory method? Utter foolishness, isn't it?
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
    // Generally prefer to return an interface (Set<>) rather than the concrete implementation; this allows you to change it's implementation later without changing the accessor.
    // Use generics patterns for Collections
    public HashMap getTicketDetailsById(int id) {
        if(!this.isTicketExist(id)){
            return  null;
        }
        // Another redundant Collection object: created and returned immediately. Can't one return the matched ticket object's copy or immutable one here?
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

    // Intention of adding cases of "Tickets IDs with a given Tag" and "Agents List with their total ticket Count" was to check how one applies knowledge on Collection of all tickets to retrieve the result.
    // In this case-study this is not implemented with intended approach.
    // Update tags for a ticket should be: either remove certain tag or add some new tags to existing one. What you did is just take new list and replace existing one.
    /**
     * Adding / updating ticket ids into tag set
     *
     * @param tagsList
     * @param ticketId
     */
    public void updateTags(List<String> tagsList, int ticketId) {
        for (String tag : tagsList) {
            if (this.tagsWithTicketIds.containsKey(tag)) {
                // Redundant HashSet
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
            // You passed the original masterTicketData list in this function and sorting it here. This will leave your masterTicketData in completely new status (obviously data will remain same, but order is not).
            // Any operation should not alter your primary data holder until and unless operation's intended purpose is to do so.
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
// I strongly recommend against taking "null" as a valid input to your own methods.
// The reality is that you force yourself to nest a bunch of null checks;
// There are ways to overcome this null checks: one ways is @NotNull annotation.
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
        // for/foreach loops on Collections can be replaced by Stream API of Java 8.
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
                // I would not give callers to update "modified" field this way. Rather, I would do it in Model class's other functions which are modifying any facts of ticket like setTags() here.
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
        // for/foreach loops on Collections can be replaced by Stream API of Java 8.
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
        // Use Java 8's feature instead for loop
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
     * getting ticket(s) assigned to specific agent using stream
     * @param agentName
     * @return
     */
    public List<Ticket> getTicketByAgentName(String agentName){
        // redundant ticketList object
        List<Ticket> ticketList = masterTicketData.stream().filter(ticket -> ticket.getAgent_name().toLowerCase().equals(agentName.toLowerCase())).collect(Collectors.toList());
        return ticketList;
    }

    /**
     * Updating tickets count to maintain the ticket(assigned to agent) counts of agent
     */
    public void ticketCountsByAgentName() {
        System.out.println("Agent Count  |   Total Count");

        Set setOfKeys = agentTicketsTotalCounts.keySet();

        // Iterators are not recommended now to iterate through Collection objects, specifically in single threaded environment. Use Java 8's stream apis.
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
    // Return Interface (Set<>) in place of Concrete implementation (HeshSet) is recommended. Use generics.
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
            // Use stream API
            for (Ticket ticket : masterTicketData){
                if(ids.contains(ticket.getId())){
                    System.out.println(ticket.getId() + " | " + ticket.getSubject() + " | " + ticket.getAgent_name() + " | " + ticket.getTags() + " | " +
                            ticket.getCreated() + " | " + ticket.getModified());
                }
            }
        }
    }
}



