package com.yogesh.service;


import com.yogesh.ConsolIO;

import com.yogesh.DateComparator;
import com.yogesh.ConsolIO;
import com.yogesh.model.Ticket;
import com.yogesh.pattern.TicketFactory;

import java.util.*;

/**
 * Created by root on 31/12/15.
 */
public class TicketService {

    public ArrayList<Ticket> arrTicketList = new ArrayList();

    /**
     * this method for search ticket based on Agent name
     */
    public void searchTicketsUsingAgentnameService(String agentName) {
        boolean ticketFlag = false;

        ConsolIO.ticketListHeader();
        for (Ticket ticket : this.arrTicketList) {
            if (ticket.getAgentName().equals(agentName)) {
                ConsolIO.showTicket(ticket);
                ticketFlag = true;
            }
        }

        if (ticketFlag == false) {
            ConsolIO.showMsg("Ticket Not found");
        }

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
    public void showTicketcountAgentService() {

        ConsolIO.showMsg("Agent Count  =>   Total Count");

        TreeMap<String, Integer> tmCount = new TreeMap<>();

        int ticketCount = 1;
        for (Ticket ticket : this.arrTicketList) {

            if (tmCount.containsKey(ticket.getAgentName())) {
                ticketCount = tmCount.get(ticket.getAgentName());
                ticketCount++;
            }
            tmCount.put(ticket.getAgentName(), ticketCount);
        }

        for (Map.Entry<String, Integer> entry : tmCount.entrySet()) {
            String agentName = entry.getKey();
            Integer count = entry.getValue();
            ConsolIO.showMsg(agentName + " => " + count);
        }
    }

    /**
     * Search all tickets by specific tag.
     */
    public void searchTicketsUsingtagService(String tag) {


        if (this.arrTicketList.isEmpty()) {
            ConsolIO.showMsg("No record Found");
        } else {
            ConsolIO.ticketListHeader();
            System.out.println();
            for (Ticket ticket : this.arrTicketList) {
                if (ticket.getTags().contains(tag)) {
                    ConsolIO.showTicket(ticket);
                    break;
                }
            }
        }
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

        if (id > 0 && !subject.equals("") && !agentName.equals(""))
        {
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

