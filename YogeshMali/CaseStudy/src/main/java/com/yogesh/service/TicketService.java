package com.yogesh.service;


import com.yogesh.ConsoleIO;
import com.yogesh.exception.TicketNotFountException;
import com.yogesh.model.Ticket;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by root on 31/12/15.
 */
public class TicketService {

    public HashMap<Integer, Ticket> hmTicketList = new HashMap();

    /**
     * this method for search ticket based on Agent name using Stream
     */
    public List<Ticket> searchTicketsUsingAgentnameService(String agentName) {

        //Ganesh D: good use of streams, just try to check case-insensitive way
        List<Ticket> list = this.hmTicketList.values().stream()
                .filter(t -> t.getAgentName().equalsIgnoreCase(agentName))
                .collect(Collectors.toList());

        return Collections.unmodifiableList(list);
    }

    /**
     * Search all tickets by specific tag  using Stream
     */
    public List<Ticket> searchTicketsUsingtagService(String tag) {

        //Ganesh D: good use of lambda
        List<Ticket> list = this.hmTicketList.values().stream()
                .filter(t -> t.getTags().contains(tag))
                .collect(Collectors.toList());

        return Collections.unmodifiableList(list);
    }

    /**
     * this method for remove specific Ticket by id
     */
    public boolean removeTicketService(int id) {
        if (isTicketIdExist(id)) {
            this.hmTicketList.remove(id);
            return true;
        }
        return false;
    }

    /**
     * this method for Ticket count grouped by agent name(order by agent name).
     */
    public TreeMap<String, Integer> showTicketCountAgentService() {

        TreeMap<String, Integer> tmCount = new TreeMap<>();

        hmTicketList.values().stream().forEach(ticket -> {
            if (tmCount.containsKey(ticket.getAgentName())) {
                tmCount.put(ticket.getAgentName(), tmCount.get(ticket.getAgentName()) + 1);
            } else {
                tmCount.put(ticket.getAgentName(), 1);
            }
        });

        return tmCount;
    }

    /**
     * Select single Ticket by id
     */
    //Ganesh D: Returning an empty collection might be better idea, so as to avoid NullPointerException

    // update :: created custom exception for avoid null pointer exception
    public Ticket showSingleTicketService(int id) throws TicketNotFountException {

        Ticket ticket = hmTicketList.get(id);
        if (ticket != null) {
            return new Ticket(hmTicketList.get(id));
        }

        throw new TicketNotFountException("Ticket not Found");
    }


    /**
     * Select all Tickets
     */
    public List showAllTicketService() {

        return Collections.unmodifiableList(this.hmTicketList.values()
                .stream()
                .sorted((Ticket o1, Ticket o2) -> o2.getModified().compareTo(o1.getModified()))
                .collect(Collectors.toList()));
    }


    /**
     * @param id
     */
    public boolean updateTags(int id, Set<String> newlist) {
        //Ganesh D: same thing as said with updateAgentName function

        //  update ::

        Ticket ticket = this.hmTicketList.get(id);
        if (ticket != null) {
            ticket.setTags(newlist);
            this.hmTicketList.put(id, ticket);
            return true;
        } else {
            ConsoleIO.showMsg("Ticket is not Found");
        }
        return false;
    }


    /**
     * this method for update agent Name
     *
     * @param id
     */

    public boolean updateAgentName(int id, String newAgentName) {

        //Ganesh D: no need of iterating in arraylist for getting Ticket model,
        // instead you can directly get that from HashMap & then do updation process, find below code
        // Ticket ticket = this.hmTicketList.get(id);

        //update :: ganesh I forgot to remove for loop after change ArrayList to HashMap for ticket object

        if (newAgentName != null && !newAgentName.isEmpty()) {
            Ticket ticket = this.hmTicketList.get(id);
            if (ticket != null) {
                new Ticket.Builder().withId(id).build();
                ticket.setAgentName(newAgentName);
                this.hmTicketList.put(id, ticket);
                return true;
            } else {
                ConsoleIO.showMsg("Ticket is not Found");
            }
        }
        return false;
    }

    /**
     * this method for create new ticket
     */
    public boolean createTicketService(int id, String subject, String agentName, Set<String> list) {

        //Ganesh D: String data should also be checked for null values, else equals method will throw null pointer exception

        // update :: added null check for subject and agent name fields and added already Exist method here.

        if (isTicketIdExist(id)) {
            ConsoleIO.showMsg("ticket Id is already Exist");
        } else if (id > 0 && subject != null && !subject.isEmpty() && agentName != null && !agentName.isEmpty()) {
            //   Ticket ticket = TicketFactory.newInstance(id, subject, agentName, list);
            Ticket ticket = new Ticket.Builder().withId(id).withSubject(subject).withAgentName(agentName).withTags(list).withCreated(LocalDateTime.now()).withModified(LocalDateTime.now()).build();
            this.hmTicketList.put(id, ticket);
            return true;
        }
        return false;
    }


    /**
     * this method is for check ticket exist or not
     *
     * @param id
     * @return
     */
    // Ganesh D: typo again

    //  update :: changed the Method name
    public boolean isTicketIdExist(int id) {

        // Ganesh D: containsKey already returns a boolean value, so why not return directly,
        // no point of using an extra condition statement,
        // can use something like
        // return this.hmTicketList.containsKey(id))

        // update :: removed Extra condition statement
        return this.hmTicketList.containsKey(id);
    }

    public void exitService() {
        this.hmTicketList = null;
        System.exit(0);
    }


}

