package service;


import Serialization.TicketOperationSerialization;
import factory.TicketFactory;
import model.Ticket;

import java.io.*;
import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by root on 18/1/16.
 */
public class TicketService {

    ArrayList<Ticket> masterTicketData = new ArrayList();
    HashMap<String, Integer> agentTicketsTotalCounts = new HashMap<String, Integer>(); // Storing ticket counts against agents name
    HashMap<String, HashSet> tagsWithTicketIds = new HashMap<String, HashSet>();  // storing ticket ids against tag name


    // initialize the input stream
    /**
     * Creating a new ticket
     * Adding tags
     * Updating ticket count against agent
     */

    public int createTicket(int ticketId, String subject, String agentName, List<String> tagsList) {

        if (subject.isEmpty() || agentName.isEmpty()) {
            return 0;
        }
       if (!tagsList.isEmpty()) {
            this.updateTags(tagsList, ticketId);
        }

        Date date = new Date();
        Ticket ticket = TicketFactory.newInstance(ticketId, subject, agentName, tagsList);

        ticket.setId(ticketId);
        ticket.setSubject(subject);
        ticket.setAgent_name(agentName);
        ticket.setTags(tagsList);
        ticket.setCreated(date);
        ticket.setModified(date);

        this.masterTicketData.add(ticket);
        this.updateAgentsTicketCount(agentName, 1); // increment ticket count against agent name
        TicketOperationSerialization.serialize(ticket, masterTicketData, true);
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
        ArrayList ticketLsit = TicketOperationSerialization.deserialize();
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
        ArrayList<Ticket> ticketLsit = TicketOperationSerialization.deserialize();
        return ticketLsit;
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

        masterTicketData = TicketOperationSerialization.deserialize();

        Date date = new Date();
        boolean updateFlag = false;
        for (Ticket ticket : masterTicketData) {
            if (ticket.getId() == id) {
                updateFlag = true;
                ticket.setAgent_name(agentName);
                ticket.setModified(date);
                break;
            }
        }
        if(masterTicketData !=null && updateFlag)
        {
            TicketOperationSerialization.serialize(null, masterTicketData, false);
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
        boolean updateFlag = false;
        masterTicketData = TicketOperationSerialization.deserialize();

        for (Ticket ticket : masterTicketData) {
            if (ticket.getId() == id) {
                updateFlag = true;
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
        if(masterTicketData !=null && updateFlag)
        {
            TicketOperationSerialization.serialize(null, masterTicketData, false);
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
        masterTicketData = TicketOperationSerialization.deserialize();
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

        boolean deleteFlag = false;

        masterTicketData = TicketOperationSerialization.deserialize();
        for(Ticket ticket : masterTicketData) {
            if (ticket.getId() == id) {
                masterTicketData.remove(ticket);
                deleteFlag = true;
                this.updateAgentsTicketCount(ticket.getAgent_name(), 0); // decrement ticket count against agent name
                break;
            }
        }
        if(masterTicketData !=null && deleteFlag)
        {
            TicketOperationSerialization.serialize(null, masterTicketData, false);
        }
        return deleteFlag;
    }

    /**
     * getting ticket(s) assigned to specific agent using stream
     * @param agentName
     * @return
     */
    public List<Ticket> getTicketByAgentName(String agentName){
        masterTicketData = TicketOperationSerialization.deserialize();
        List<Ticket> ticketList = masterTicketData.stream().filter(ticket -> ticket.getAgent_name().toLowerCase().equals(agentName.toLowerCase())).collect(Collectors.toList());
        return ticketList;
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
        masterTicketData = TicketOperationSerialization.deserialize();
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

    public void exitSystem(){
        //Ticket.writeFile(masterTicketData);
        System.out.println("End of operation");
    }

    public int numberOfTickets() {
        int count = 0;
        masterTicketData = TicketOperationSerialization.deserialize();
        if(!masterTicketData.isEmpty())
            count = masterTicketData.size();

        return count;
    }

    public void showUsedTagsWithTicketId()
    {
        for(Map.Entry m:tagsWithTicketIds.entrySet()){
            System.out.println(m.getKey()+"     "+m.getValue());
        }
    }

    public void showOldestTicket() {
        masterTicketData = TicketOperationSerialization.deserialize();
        if (masterTicketData.isEmpty()) {
            System.out.println("No Tickets Found!!!");
        } else {
            Ticket ticket= masterTicketData
                    .stream()
                    .sorted((Ticket t1, Ticket t2) -> t1.getCreated().compareTo(t2.getCreated()))
                    .findFirst()
                    .get();

            System.out.println("Id | Subject | Agent Name | Tags | Created | Modified");
            System.out.println(ticket.getId() + " | " + ticket.getSubject() + " | " + ticket.getAgent_name()+ " | " + ticket.getTags() + " | " + ticket.getCreated() + " | " + ticket.getModified());
        }


    }

    public void showTicketsOlderThanCertainDays(int userDays){
        masterTicketData = TicketOperationSerialization.deserialize();
        if(masterTicketData.isEmpty()){
            System.out.println("No records Found!!!");
        }
        else {
            System.out.println("Id | Subject | Agent Name | Tags | Created | Modified");
            for (Ticket ticket : masterTicketData){
                long datDiffInDays = this.getDateDiff(ticket.getCreated());

                if(datDiffInDays>userDays){
                    System.out.println(ticket.getId() + " | " + ticket.getSubject() + " | " + ticket.getAgent_name() + " | " + ticket.getTags() + " | " +
                            ticket.getCreated() + " | " + ticket.getModified());
                }
            }
        }
    }

    public void createDummyTickets(int ticketId) {
        String tags = "mumbai,delhi,pune";
        List<String> tagList = Arrays.asList(tags.split(","));

        Date date = null;
        for(int i=1; i<=10; i++) {
            Ticket ticket = TicketFactory.newInstance();
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -i);

            date = cal.getTime();

            ticketId = ticketId + i;

            ticket.setId(ticketId);
            ticket.setSubject("Dummy++" + ticketId);
            ticket.setAgent_name("agentName++" + ticketId);
            ticket.setTags(tagList);
            ticket.setCreated(date);
            ticket.setModified(date);

            this.masterTicketData.add(ticket);
            this.updateAgentsTicketCount("agentName++"+ticketId, 1); // increment ticket count against agent name
        }
        ticketId++;
    }

    public long getDateDiff(Date d1){
        long dateDiff = 0;
        Date date = new Date();

        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        String currentDate = format.format(date);

        String ticketDate = format.format(d1);
        Date tempD1 = null;
        Date tempD2 = null;

        try{
            tempD1 = format.parse(currentDate);
            tempD2 = format.parse(ticketDate);

            long diff = tempD1.getTime() - tempD2.getTime();
            dateDiff = diff / (24 * 60 * 60 * 1000);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return  dateDiff;
    }

}