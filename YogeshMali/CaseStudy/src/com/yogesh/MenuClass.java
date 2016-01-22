package com.yogesh;

import com.yogesh.model.Ticket;
import com.yogesh.service.TicketService;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by root on 15/1/16.
 */
public class MenuClass {


    public TicketService ticketService = new TicketService();

    public MenuClass() {
        displayList();
    }

    /**
     * Select Operation
     *
     * @param menuId
     */
    private void operation(int menuId) {

        switch (menuId) {
            case 1:
                createTicket();
                break;
            case 2:
                updateTicket();
                break;
            case 3:
                showAllTicket();
                break;
            case 4:
                showSingleTicket();
                break;
            case 5:
                searchTicketsUsingtag();
                break;
            case 6:
                showTicketcountAgent();
                break;
            case 7:
                removeTicket();
                break;
            case 8:
                searchTicketsUsingAgentname();
                break;
            case 9:
                exit();
                break;
            default:
                ;
                break;
        }
    }


    private void createTicket() {

        int id = ConsolIO.getTicketId();
        String subject = ConsolIO.getSubject();
        String agentName = ConsolIO.getAgentNAme();
        List<String> list = ConsolIO.getTags();

        if (ticketService.isTicketIdExit(id)) {
            ConsolIO.showMsg("ticket Id is already Exist");
        } else {
            if (ticketService.createTicketService(id, subject, agentName, list)) {
                ConsolIO.showMsg("Ticket has been added successfully");
            }
        }

    }

    private void updateTicket() {
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
                List<String> newlist = ConsolIO.getTags();
                if (ticketService.updateTags(id, newlist)) {
                    ConsolIO.showMsg("Ticket Tags has been updated");
                }
            }

        } else {
            ConsolIO.showMsg("Ticket is not Exists");
        }
    }

    private void showAllTicket() {

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

    private void showSingleTicket() {
        ConsolIO.ticketListHeader();
        int id = ConsolIO.getTicketId();
        Ticket ticket = ticketService.showSingleTicketService(id);
        if (ticket == null) {
            ConsolIO.showMsg("Ticket Not found");
        } else {
            ConsolIO.showTicket(ticket);
        }
    }

    private void searchTicketsUsingtag() {
        ConsolIO.showMsg("Enter the tag want to search");
        String tag = ConsolIO.getString();

        ConsolIO.ticketListHeader();
        List<Ticket> list =  ticketService.searchTicketsUsingtagService(tag);
        if (list.isEmpty()) {
            ConsolIO.showMsg("Ticket Not found");
        } else {
            for (Ticket ticket : list) {
                ConsolIO.showTicket(ticket);
            }
        }

    }

    private void showTicketcountAgent() {
        ConsolIO.showMsg("Agent Count  =>   Total Count");
        TreeMap<String, Integer> tmCount =  ticketService.showTicketcountAgentService();

        for (Map.Entry<String, Integer> entry : tmCount.entrySet()) {
            String agentName = entry.getKey();
            Integer count = entry.getValue();
            ConsolIO.showMsg(agentName + " => " + count);
        }

    }


    private void removeTicket() {
        if (ticketService.removeTicketService(ConsolIO.getTicketId())) {
            ConsolIO.showMsg(" Ticket deleted Successfully");
        }
    }

    private void searchTicketsUsingAgentname() {

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

    private void exit() {
        ticketService.exitService();
    }

    /**
     * this method handle continue process or exit
     */
    public void msg() {

        ConsolIO.showMsg("Please Press 1 : Continue   ");
        int msgId = ConsolIO.getIntvalue();
        if (msgId == 1) {
            displayList();

        } else {
            ConsolIO.showMsg("thank you");
        }
    }

    /**
     * Display Menu List
     */
    public void displayList() {

        ConsolIO.displayList();
        ConsolIO.showMsg("Select Operation ");
        int menuId = ConsolIO.getIntvalue();
        this.operation(menuId);
        msg();
    }

}
