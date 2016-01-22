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

    public ArrayList<Ticket> arrTicketList = new ArrayList();

    /**
     * this method for search ticket based on Agent name
     */
    public List<Ticket> searchTicketsUsingAgentnameService(String agentName) {

        List<Ticket> list = this.arrTicketList.stream()
                .filter(t -> t.getAgentName().equals(agentName))
                .collect(Collectors.toList());

        return list;
    }


    /**
     * Search all tickets by specific tag.
     */
    public List<Ticket> searchTicketsUsingtagService(String tag) {


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
            for (Ticket ticket : this.arrTicketList) {
                if (ticket.getId() == id) {
                    this.arrTicketList.remove(ticket);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * this method for Ticket count grouped by agent name(order by agent name).
     */
    public TreeMap<String, Integer> showTicketcountAgentService() {

        TreeMap<String, Integer> tmCount = new TreeMap<>();
        
        for (Ticket ticket : this.arrTicketList) {

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
    public Ticket showSingleTicketService(int id) {

        for (Ticket ticket : this.arrTicketList) {

            if (ticket.getId() == id) {
                return ticket;
            }
        }
        return null;
    }


    /**
     * Select all Tickets
     */
    public List showAllTicketService() {

        // sort is based on modified date field Descending Order
        Collections.sort(this.arrTicketList, new DateComparator());
        return this.arrTicketList;
    }


    /**
     * @param id
     */
    public boolean updateTags(int id, List<String> newlist) {

        for (Ticket ticket : this.arrTicketList) {
            if (ticket.getId() == id) {
                ticket.setTags(newlist);
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

        if (!newAgentName.equals("")) {
            for (Ticket ticket : this.arrTicketList) {
                if (ticket.getId() == id) {
                    System.out.println(ticket.getId());
                    ticket.setAgentName(newAgentName);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * this method for create new ticket
     */
    public boolean createTicketService(int id, String subject, String agentName, List<String> list) {

        if (id > 0 && !subject.equals("") && !agentName.equals("")) {
            Ticket ticket = TicketFactory.newInstance(id, subject, agentName, list);
            this.arrTicketList.add(ticket);
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
    public boolean isTicketIdExit(int id) {

        for (Ticket ticket : this.arrTicketList) {
            if (ticket.getId() == id)
                return true;
        }
        return false;
    }

    public void exitService() {
        arrTicketList = null;
        System.exit(0);
    }

}

