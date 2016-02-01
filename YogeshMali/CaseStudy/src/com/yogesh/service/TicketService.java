package com.yogesh.service;


import com.yogesh.ConsolIO;

import com.yogesh.DateComparator;
import com.yogesh.ConsolIO;
import com.yogesh.model.Ticket;
import com.yogesh.pattern.TicketFactory;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        ArrayList<Ticket> arrTicketList =  new ArrayList<>(this.hmTicketList.values());
        List<Ticket> list = arrTicketList.stream()
                .filter(t -> t.getAgentName().equals(agentName))
                .collect(Collectors.toList());

        return list;
    }

    /**
     * Search all tickets by specific tag Agent name using Stream
     */
    public List<Ticket> searchTicketsUsingtagService(String tag) {

        ArrayList<Ticket> arrTicketList =  new ArrayList<>(this.hmTicketList.values());

        //Ganesh D: good use of lambda
        List<Ticket> list = arrTicketList.stream()
                .filter(t -> t.getTags().contains(tag))
                .collect(Collectors.toList());

        return list;
    }

    /**
     * this method for remove specific Ticket by id
     */
    public boolean removeTicketService(int id) {
        if (isTicketIdExit(id)) {
           this.hmTicketList.remove(id);
            return true;
        }
        return false;
    }

    /**
     * this method for Ticket count grouped by agent name(order by agent name).
     */
    public TreeMap<String, Integer> showTicketcountAgentService() {

        TreeMap<String, Integer> tmCount = new TreeMap<>();
        ArrayList<Ticket> arrTicketList =  new ArrayList<>(this.hmTicketList.values());

        for (Ticket ticket : arrTicketList) {

            if (tmCount.containsKey(ticket.getAgentName())) {
                int ticketCount = tmCount.get(ticket.getAgentName()) + 1;
                tmCount.put(ticket.getAgentName(), ticketCount);
            } else {
                tmCount.put(ticket.getAgentName(), 1);
            }

        }
        return tmCount;
    }

    /**
     * Select single Ticket by id
     */
    //Ganesh D: Returning an empty collection might be better idea, so as to avoid NullPointerException
    public Ticket showSingleTicketService(int id) {

        if (this.hmTicketList.containsKey(id)) {
           return this.hmTicketList.get(id);
        }
        return null;
    }


    /**
     * Select all Tickets
     */
    public List showAllTicketService() {

        ArrayList<Ticket> arrTicketList =  new ArrayList<>(this.hmTicketList.values());
        // sort is based on modified date field Descending Order
        Collections.sort(arrTicketList, new DateComparator());
        return arrTicketList;
    }


    /**
     * @param id
     */
    public boolean updateTags(int id, Set<String> newlist) {
        //Ganesh D: same thing as said with updateAgentName function
        ArrayList<Ticket> arrTicketList =  new ArrayList<>(this.hmTicketList.values());
        for (Ticket ticket : arrTicketList) {
            if (ticket.getId() == id) {
                ticket.setTags(newlist);
                this.hmTicketList.put(id,ticket);
                return true;
            }
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
        ArrayList<Ticket> arrTicketList =  new ArrayList<>(this.hmTicketList.values());
        if (!newAgentName.equals("")) {
            for (Ticket ticket : arrTicketList) {
                if (ticket.getId() == id) {
                    System.out.println(ticket.getId());
                    ticket.setAgentName(newAgentName);
                    this.hmTicketList.put(id,ticket);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * this method for create new ticket
     */
    public boolean createTicketService(int id, String subject, String agentName, Set<String> list) {

        //Ganesh D: String data should also be checked for null values, else equals method will throw null pointer exception
        if (id > 0 && !subject.equals("") && !agentName.equals("")) {
            Ticket ticket = TicketFactory.newInstance(id, subject, agentName, list);
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
    public boolean isTicketIdExit(int id) {

        // Ganesh D: containsKey already returns a boolean value, so why not return directly,
        // no point of using an extra condition statement,
        // can use something like
        // return this.hmTicketList.containsKey(id))
        if (this.hmTicketList.containsKey(id)) {
            return true;
        }

        return false;
    }

    public void exitService() {
        this.hmTicketList = null;
        System.exit(0);
    }



}

