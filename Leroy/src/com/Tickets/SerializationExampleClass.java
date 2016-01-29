package com.Tickets;


import java.util.List;

/**
 * Created by root on 28/1/16.
 */
public class SerializationExampleClass {
    public static void main(String[] args) {

        TicketServiceComponent ticketServiceComponent= new TicketServiceComponent();
        SerializeService serializeService = new SerializeService();
        Sout sout = new Sout();

        // Single Ticket Serialize / De-Serialize
        System.out.println("Serialize / De-serialize Single Ticket from File");
        Ticket ticket = ticketServiceComponent.prepareTicketDataForFile();
        serializeService.writeSingleTicketToFile(ticket);
        serializeService.readSingleTicketFromFile(ticket);

        // Multiple  Tickets Serialize / De-Serialize
        System.out.println("\nSerialize / De-serialize Multiple Tickets from File");
        List ticketsList = ticketServiceComponent.prepareMultipleTicketDataForFile();
        serializeService.writeMultipleTicketsToFile(ticketsList, ticket);
        serializeService.readMultipleTicketsFromFile(ticket);

        // Report Statistics
        System.out.println("\nReport Statistics");
        ticketServiceComponent.getNumberOfTicketsInSystem(ticketsList);
        ticketServiceComponent.getOldestTicketInSystem(ticketsList);
        ticketServiceComponent.getCountOfTags(ticketsList);
    }
}
