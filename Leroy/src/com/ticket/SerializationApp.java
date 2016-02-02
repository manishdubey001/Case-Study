package com.ticket;


import com.ticket.model.Ticket;
import com.ticket.service.SerializeService;
import com.ticket.service.TicketService;
import com.ticket.util.ConsoleScan;

import java.util.List;

/**
 * Created by root on 28/1/16.
 */
public class SerializationApp {
    public static void main(String[] args) {

        TicketService ticketService = new TicketService();
        SerializeService serializeService = new SerializeService();
        ConsoleScan consoleScan = new ConsoleScan();

        // Single Ticket Serialize / De-Serialize
        System.out.println("Serialize / De-serialize Single Ticket from File");
        Ticket ticket = ticketService.prepareTicketDataForFile();
        serializeService.writeSingleTicketToFile(ticket);
        serializeService.readSingleTicketFromFile(ticket);

        // Multiple  Tickets Serialize / De-Serialize
        System.out.println("\nSerialize / De-serialize Multiple Tickets from File");
        List ticketsList = ticketService.prepareMultipleTicketDataForFile();
        serializeService.writeMultipleTicketsToFile(ticketsList, ticket);
        serializeService.readMultipleTicketsFromFile(ticket);

        // Report Statistics
        System.out.println("\nReport Statistics");
        ticketService.getNumberOfTicketsInSystem(ticketsList);
        ticketService.getOldestTicketInSystem(ticketsList);
        ticketService.getCountOfTags(ticketsList);
        consoleScan.scanOldTickets(ticketsList);
    }
}
