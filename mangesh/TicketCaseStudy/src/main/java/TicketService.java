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
        Ticket ticket = new Ticket();

        ticket.setId(ticketId);
        ticket.setSubject(subject);
        ticket.setAgent_name(agentName);
        ticket.setTags(tagsList);
        ticket.setCreated(date);
        ticket.setModified(date);

        this.masterTicketData.add(ticket);

        // System.out.println(" ticket Data : " + masterTicketData);

        this.updateAgentsTicketCount(agentName, 1); // increment ticket count against agent name
        return ticketId;
    }

    public HashMap getTicketDetailsById(int id) {
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
        //System.out.println("updatetags func :: " + tagsWithTicketIds);
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
    public void showAllTickets() {
        Collections.sort(this.masterTicketData);

        if (this.masterTicketData.isEmpty()) {
            System.out.println("No Tickets Found!!!");
        } else {
            System.out.println("Id | Subject | Agent Name | Tags | Created | Modified");
            for (Ticket ticket : this.masterTicketData) {
                System.out.println(ticket.getId() + " | " + ticket.getSubject() + " | " + ticket.getAgent_name() + " | " + ticket.getTags() + " | " +
                        ticket.getCreated() + " | " + ticket.getModified());
            }
            //System.out.println("tags :: " + tagsWithTicketIds);
        }
    }

    public boolean updateTicketAgent(int id, String agentName) {
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

    public void showTicketByAgentName(String agentName){
        boolean isTicket = false;
        agentName.trim();
        System.out.println("Id | Subject | Agent Name | Tags | Created | Modified");
        for (Ticket ticket : masterTicketData) {
            if(ticket.getAgent_name().equals(agentName)){
                System.out.println(ticket.getId() + " | " + ticket.getSubject() + " | " + ticket.getAgent_name() + " | " + ticket.getTags() + " | " +
                        ticket.getCreated() + " | " + ticket.getModified());
                isTicket = true;
            }
        }

        if(isTicket == false) {
            System.out.println("No Ticket(s) assigned to Agent " + agentName);
        }
    }

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

    public void showTicketsByTagName(String tag) {
        HashSet ids = new HashSet();
        if(this.tagsWithTicketIds.containsKey(tag)) {
            ids = tagsWithTicketIds.get(tag);
        }

        if(masterTicketData.isEmpty()){
            System.out.println("No ticket(s) found in the system");
        }
        else{
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



