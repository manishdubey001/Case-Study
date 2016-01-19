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
    public void removeTicketService(int id) {

        if (isTicketIdExit(id)) {
            for (Ticket ticket : this.arrTicketList) {
                if (ticket.getId() == id) {
                    List<String> oldTagsList = ticket.getTags();
                    this.arrTicketList.remove(ticket);
                    ConsolIO.showMsg(" Ticket deleted Successfully");
                    break;
                }
            }
        } else {
            ConsolIO.showMsg("Ticket Not Fount");
        }

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
        boolean ticketFlag = false;
        ConsolIO.ticketListHeader();
        for (Ticket ticket : this.arrTicketList) {

            if (ticket.getId() == id) {
                ConsolIO.showTicket(ticket);
                ticketFlag = true;
                return ticket;
//                break;
            }
        }
        if (ticketFlag == false) {
            ConsolIO.showMsg("Ticket Not found");
        }
        return null;
    }


    /**
     * Select all Tickets
     */
    public void showAllTicketService() {

        // sort is based on modified date field Descending Order

        Collections.sort(this.arrTicketList, new DateComparator());

        ConsolIO.ticketListHeader();


        if (this.arrTicketList.isEmpty()) {
            ConsolIO.showMsg("No record Found");
        } else {
            for (Ticket ticket : this.arrTicketList) {
                ConsolIO.showTicket(ticket);
            }
        }
    }


    /**
     * @param id
     */
    public void updateTags(int id, List<String> newlist) {
        if (!newlist.isEmpty()) {
            for (Ticket ticket : this.arrTicketList) {
                if (ticket.getId() == id) {
                    ticket.setTags(newlist);
                    ConsolIO.showMsg("Ticket Tags has been updated");
                    break;
                }
            }

        }
    }


    /**
     * this method for update agent Name
     *
     * @param id
     */

    public boolean updateAgentName(int id, String newAgentName) {

        if (id > 0) {
            for (Ticket ticket : this.arrTicketList) {
                if (ticket.getId() == id) {
                    System.out.println(ticket.getId());
                    ticket.setAgentName(newAgentName);
                    return true;
                }
                break;
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

            if (this.isTicketIdExit(id)) {
                ConsolIO.showMsg("ticket Id is already Exist");
            } else {
                this.arrTicketList.add(ticket);
                ConsolIO.showMsg("Ticket has been added successfully");
            }
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

