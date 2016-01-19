package com.services;

import com.model.Tag;
import com.model.Ticket;

import java.util.*;

/**
 * Created by root on 13/1/16.
 */
public class TicketMenu {

    public static void main(String[] args) {
        TicketOperations objTicktOperation = new TicketOperations();

        boolean loop = true;

        while(loop) {
            displayTicketMenu();

            int option = UserConsoleInput.acceptNumber();
            System.out.println("your option is :" + option);

            switch (option) {
                case 1:
                    System.out.println("Creating Ticket");
                    objTicktOperation.create();
                    //objTicktOperation.showAllTicket();
                    objTicktOperation.showTickets(objTicktOperation.showAllTicket());
                    System.out.println("Ticket create successful");
                    break;

                case 2:
                    System.out.println("Enter ticket Id");
                    int id1 = UserConsoleInput.acceptNumber();
                    objTicktOperation.showTickets(objTicktOperation.showTicketById(id1));
                    break;

                case 3:
                    System.out.println("Enter ticket Id");
                    int id2 = UserConsoleInput.acceptNumber();
                    objTicktOperation.updateTicketById(id2);
                    break;

                case 4:
                    System.out.println("Enter ticket Id");
                    int id3 = UserConsoleInput.acceptNumber();
                    objTicktOperation.deleteTicketById(id3);
                    System.out.println("Ticket update successful");
                    break;

                case 5:
                    objTicktOperation.showTickets(objTicktOperation.searchTicketByAgent());
                    break;

                case 6:
                    objTicktOperation.showTickets(objTicktOperation.searchTicketByTag());
                    break;
                case 7:
                    System.out.println("Show All the Tickets");
                    objTicktOperation.showTickets(objTicktOperation.showAllTicket());
                    break;
                case 8:
                    System.out.println("Enter Agent Name");
                    Map<String, Integer> ticketContMap = objTicktOperation.calculateAgentTicketCount();
                    objTicktOperation.showAgentTicketCount(objTicktOperation.calculateAgentTicketCount());
                    break;
                case 9:
                    Set<String> tagSet = new HashSet<>();
                    tagSet.add("one");
                    tagSet.add("two");
                    tagSet.add("three");
                    objTicktOperation.createTicket("Subject1", "Agent1", tagSet);
                    break;
                case 0:
                    System.exit(0);
                    break;

                default:
                    System.out.println("Wrong Input! please enter number between 1 to 9");
                    break;

            }
        }
        System.exit(0);

    }



    /*
    * Showing the operation available on Ticket System */
    public static void displayTicketMenu(){
        System.out.println("########################### Ticket Menu ###########################");
        System.out.println("Select the options for particular action below:");
        System.out.println("1: Create Ticket");
        System.out.println("2: Select Ticket by id");
        System.out.println("3: Update Ticket by id");
        System.out.println("4: Delete Ticket");
        System.out.println("5: Search tickets by Agent Name");
        System.out.println("6: Search tickets by Tag name");
        System.out.println("7: Show All Tickets");
        System.out.println("8: Agent by Ticket count");
        System.out.println("0: QUIT");
        System.out.println("Enter your Choice :");
    }

}