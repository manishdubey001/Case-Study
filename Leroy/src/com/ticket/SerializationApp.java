package com.ticket;

import com.ticket.model.Ticket;
import com.ticket.service.SerializeService;
import com.ticket.service.TicketService;
import com.ticket.util.ConsoleScan;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Created by root on 28/1/16.
 */
public class SerializationApp {
    public static void main(String[] args) {

        int userInput;
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
        List ticketsList = ticketService.prepareMultipleTicketDataForFile(ticket);
        serializeService.writeMultipleTicketsToFile(ticketsList, ticket);
        serializeService.readMultipleTicketsFromFile(ticket);

        // Report Statistics
        try(BufferedReader bf = new BufferedReader(new InputStreamReader(System.in))) {
            consoleScan.getAllSerializeCommands();
            do {
                userInput = Integer.parseInt(bf.readLine());
                switch (userInput) {
                    case 1:
                        ticketService.getNumberOfTicketsInSystem(ticketsList);
                        break;

                    case 2:
                        ticketService.getOldestTicketInSystem(ticketsList);
                        break;

                    case 3:
                        consoleScan.scanOldTickets(ticketsList);
                        break;

                    case 4:
                        ticketService.getCountOfTags(ticketsList);
                        break;

                    default:
                        break;
                }
            }while (userInput <5);
        }catch (NumberFormatException Nf){
            System.out.print(ConsoleScan.ACT_INVALID+ Nf);
        }catch (IOException Io){
            System.out.print(ConsoleScan.ACT_ERROR + Io);
        }


    }
}
