package com.yogesh.service;


import com.yogesh.ConsolIO;

import com.yogesh.DateComparator;
import com.yogesh.Helper;
import com.yogesh.model.Ticket;
import com.yogesh.pattern.TicketFactory;

import java.util.*;

/**
 * Created by root on 31/12/15.
 */
public class TicketService {

    public ArrayList<Ticket> arrTicketList = new ArrayList();
    Scanner scanIn;

    public void exitService() {
        scanIn = null;
        arrTicketList = null;
        System.exit(0);
    }

    /**
     * this method for search ticket based on Agent name
     */
    public void searchTicketsUsingAgentnameService(String agentName) {
        boolean ticketFlag = false;

        Helper.ticketListHeader();
        System.out.println();
        for (Ticket ticket : this.arrTicketList) {
            if (ticket.getAgentName().equals(agentName)) {
                ConsolIO.showTicket(ticket);
                ticketFlag = true;
            }
        }

        if (ticketFlag == false) {
            Helper.showMsg("Ticket Not found");
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
                    Helper.showMsg(" Ticket deleted Successfully");
                    break;
                }
            }
        } else {
            Helper.showMsg("Ticket Not Fount");
        }

    }


    /**
     * this method for Ticket count grouped by agent name(order by agent name).
     */
    public void showTicketcountAgentService() {

        Helper.showMsg("Agent Count  =>   Total Count");

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
            Helper.showMsg(agentName + " => " + count);
        }
    }

    /**
     * Search all tickets by specific tag.
     */
    public void searchTicketsUsingtagService(String tag) {


        if (this.arrTicketList.isEmpty()) {
            Helper.showMsg("No record Found");
        } else {
            Helper.ticketListHeader();
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
        Helper.ticketListHeader();
        System.out.println();
        for (Ticket ticket : this.arrTicketList) {

            if (ticket.getId() == id) {
                ConsolIO.showTicket(ticket);
                ticketFlag = true;
                return ticket;
//                break;
            }
        }
        if (ticketFlag == false) {
            Helper.showMsg("Ticket Not found");
        }
        return null;
    }


    /**
     * Select all Tickets
     */
    public void showAllTicketService() {

        // sort is based on modified date field Descending Order

        Collections.sort(this.arrTicketList, new DateComparator());

        Helper.ticketListHeader();
        System.out.println();

        if (this.arrTicketList.isEmpty()) {
            Helper.showMsg("No record Found");
        } else {
            for (Ticket ticket : this.arrTicketList) {
                ConsolIO.showTicket(ticket);
            }
        }
    }


    /**
     * @param id
     */
    public void updateTags(int id) {

        List<String> newlist = ConsolIO.getTags();

        if (!newlist.isEmpty()) {
            for (Ticket ticket : this.arrTicketList) {
                if (ticket.getId() == id) {
                    ticket.setTags(newlist);
                    Helper.showMsg("Ticket Tags has been updated");
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
    public void updateAgentName(int id) {

        String newAgentName = ConsolIO.getAgentNAme();

        for (Ticket ticket : this.arrTicketList) {
            if (ticket.getId() == id) {

                ticket.setAgentName(newAgentName);
                Helper.showMsg(" Agent name has been updated ");
            }
            break;
        }

    }

    /**
     * /**
     * this method for create new ticket
     */
    public boolean createTicketService(int id, String subject, String agentName, List<String> list) {

        if(  0 < id && !subject.equals(null) && !agentName.equals(null)) {
            Ticket ticket = TicketFactory.newInstance(id,subject,agentName,list);

            if (this.isTicketIdExit(id)) {
                System.out.println("ticket Id is already Exist");
            } else {
                this.arrTicketList.add(ticket);
                Helper.showMsg("Ticket has been added successfully");
            }
            return  true;
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


}

