package com.yogesh;

import com.yogesh.model.Ticket;
import com.yogesh.service.TicketService;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Created by root on 1/2/16.
 */
public class TicketManager {

    public TicketService ticketService = new TicketService();

    public void createTicket() {

        int id = ConsolIO.getTicketId();
        String subject = ConsolIO.getSubject();
        String agentName = ConsolIO.getAgentNAme();
        Set<String> list = ConsolIO.getTags();

        if (ticketService.isTicketIdExit(id)) {
            ConsolIO.showMsg("ticket Id is already Exist");
        } else {
            if (ticketService.createTicketService(id, subject, agentName, list)) {
                ConsolIO.showMsg("Ticket has been added successfully");
            }
        }

    }

    public void updateTicket() {
        int id = ConsolIO.getTicketId();
        if (ticketService.isTicketIdExit(id)) {
            ConsolIO.showMsg("Press a to update Agent name  ");
            String updatedId = ConsolIO.getString();
            if (updatedId.equals("a")) {
                String newAgentName = ConsolIO.getAgentNAme();
                if (ticketService.updateAgentName(id, newAgentName)) {
                    ConsolIO.showMsg(" Agent name has been updated ");
                }
            }
            ConsolIO.showMsg("Press t to update Tags  ");
            String updatedTag = ConsolIO.getString();
            if (updatedTag.equals("t")) {
                Set<String> newlist = ConsolIO.getTags();
                if (ticketService.updateTags(id, newlist)) {
                    ConsolIO.showMsg("Ticket Tags has been updated");
                }
            }

        } else {
            ConsolIO.showMsg("Ticket is not Exists");
        }
    }


    public void showAllTicket() {

        ConsolIO.ticketListHeader();
        List<Ticket> list = ticketService.showAllTicketService();
        if (list.isEmpty()) {
            ConsolIO.showMsg("No record Found");

        } else {
            for (Ticket ticket : list) {
                ConsolIO.showTicket(ticket);
            }
        }
    }

    public void showSingleTicket() {
        ConsolIO.ticketListHeader();
        int id = ConsolIO.getTicketId();
        Ticket ticket = ticketService.showSingleTicketService(id);
        if (ticket == null) {
            ConsolIO.showMsg("Ticket Not found");
        } else {
            ConsolIO.showTicket(ticket);
        }
    }

    public void searchTicketsUsingtag() {
        ConsolIO.showMsg("Enter the tag want to search");
        String tag = ConsolIO.getString();

        ConsolIO.ticketListHeader();
        List<Ticket> list = ticketService.searchTicketsUsingtagService(tag);
        if (list.isEmpty()) {
            ConsolIO.showMsg("Ticket Not found");
        } else {
            for (Ticket ticket : list) {
                ConsolIO.showTicket(ticket);
            }
        }

    }

    public void showTicketcountAgent() {
        ConsolIO.showMsg("Agent Name  =>   Total Count");
        TreeMap<String, Integer> tmCount = ticketService.showTicketcountAgentService();

        for (Map.Entry<String, Integer> entry : tmCount.entrySet()) {
            String agentName = entry.getKey();
            Integer count = entry.getValue();
            ConsolIO.showMsg(agentName + " => " + count);
        }

    }


    public void removeTicket() {
        if (ticketService.removeTicketService(ConsolIO.getTicketId())) {
            ConsolIO.showMsg(" Ticket deleted Successfully");
        }
    }

    public void searchTicketsUsingAgentname() {

        List<Ticket> list = ticketService.searchTicketsUsingAgentnameService(ConsolIO.getAgentNAme());
        ConsolIO.ticketListHeader();
        if (list.isEmpty()) {
            ConsolIO.showMsg("Ticket Not found");
        } else {
            for (Ticket ticket : list) {
                ConsolIO.showTicket(ticket);
            }
        }
    }

    public void exit() {
        ticketService.exitService();
    }

}
