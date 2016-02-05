package com.yogesh;

import com.yogesh.exception.TicketNotFountException;
import com.yogesh.model.Ticket;
import com.yogesh.service.TicketService;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by root on 1/2/16.
 */
public class TicketManager {

    public TicketService ticketService = new TicketService();

    public void createTicket() {

        //Ganesh D: I think isTicketIDExit logic should be implemented in createTicketService functions,
        // coz when we expose createTicketService to outside world then the duplicate ticket id validation will fail/skip

        // update  ::  Changed the logic as you suggested
        if (ticketService.createTicketService(ConsoleIO.getTicketId(), ConsoleIO.getSubject(), ConsoleIO.getAgentNAme(), ConsoleIO.getTags())) {
            ConsoleIO.showMsg("Ticket has been added successfully");
        }


    }

    public void updateTicket() {
        int id = ConsoleIO.getTicketId();
        ConsoleIO.showMsg("Press a to update Agent name ");
        String updatedId = ConsoleIO.getString();
        if (updatedId.equals("a")) {
            String newAgentName = ConsoleIO.getAgentNAme();
            if (ticketService.updateAgentName(id, newAgentName)) {
                ConsoleIO.showMsg(" Agent name has been updated ");
            }
        }
        ConsoleIO.showMsg("Press t to update Tags  ");
        String updatedTag = ConsoleIO.getString();
        if (updatedTag.equals("t")) {
            if (ticketService.updateTags(id, ConsoleIO.getTags())) {
                ConsoleIO.showMsg("Ticket Tags has been updated");
            }
        }


    }


    public void showAllTicket() {

        ConsoleIO.ticketListHeader();
        List<Ticket> list = ticketService.showAllTicketService();
        if (list.isEmpty()) {
            ConsoleIO.showMsg("No record Found");
        } else {
            //Ganesh D: Suggestions used of foreach loop is good, but you can check forEach + lambda expression

            // update :: used forEach + lambda
            list.forEach(ticket -> ConsoleIO.showTicket(ticket));
        }
    }

    public void showSingleTicket() {

        int id = ConsoleIO.getTicketId();
        ConsoleIO.ticketListHeader();
        try {
            Ticket ticket = ticketService.showSingleTicketService(id);
            ConsoleIO.showTicket(ticket);
        } catch (TicketNotFountException tnfe) {
        }
    }

    public void searchTicketsUsingtag() {
        ConsoleIO.showMsg("Enter the tag want to search");
        String tag = ConsoleIO.getString();

        ConsoleIO.ticketListHeader();
        List<Ticket> list = ticketService.searchTicketsUsingtagService(tag);
        if (list.isEmpty()) {
            ConsoleIO.showMsg("Ticket Not found");
        } else {
            // update :: used forEach + lambda
            list.forEach(ticket -> ConsoleIO.showTicket(ticket));
        }

    }

    public void showTicketCountAgent() {
        ConsoleIO.showMsg("Agent Name  =>   Total Count");
        TreeMap<String, Integer> tmCount = ticketService.showTicketCountAgentService();

        for (Map.Entry<String, Integer> entry : tmCount.entrySet()) {
            String agentName = entry.getKey();
            Integer count = entry.getValue();
            ConsoleIO.showMsg(agentName + " => " + count);
        }
    }

    public void removeTicket() {
        if (ticketService.removeTicketService(ConsoleIO.getTicketId())) {
            ConsoleIO.showMsg(" Ticket deleted Successfully");
        }
    }

    public void searchTicketsUsingAgentname() {

        List<Ticket> list = ticketService.searchTicketsUsingAgentnameService(ConsoleIO.getAgentNAme());
        ConsoleIO.ticketListHeader();
        if (list.isEmpty()) {
            ConsoleIO.showMsg("Ticket Not found");
        } else {
            // update :: used forEach + lambda
            list.forEach(ticket -> ConsoleIO.showTicket(ticket));
        }
    }

    public void exit() {
        ticketService.exitService();
    }

}
