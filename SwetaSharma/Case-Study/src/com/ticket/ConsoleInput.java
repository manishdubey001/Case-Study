package com.ticket;

import java.util.*;

/**
 * Created by root on 3/2/16.
 */
public class ConsoleInput {
    //Declaring scanner variable to take input from user.
    Scanner read = new Scanner(System.in);
    TicketService ticketService = new TicketService();

    public void takeInputFromConsole(){
        do{
            showMenuDetails();
            int choice = read.nextInt();

            switch (choice){
                case 1:
                    ticketService.createTicketProcess();
                    break;
                case 2:
                    ticketService.updateTicketProcess();
                    break;
                case 3:
                    ticketService.deleteTicketProcess();
                    break;
                case 4:
                    ticketService.detailTicketProcess();
                    break;
                case 5:
                    ticketService.getTicketListProcess();
                    break;
                case 6:
                    ticketService.getTicketListByAgent();
                    break;
                case 7:
                    ticketService.getTicketListByTag();
                    break;
                case 8:
                    ticketService.getAgentTicketCount();
                    break;
            }
            System.out.println("Do you want to continue? press 'y' for yes or 'n' for no");
        }while (read.next().equals("y"));
    }

    /**
     * prints the menu details
     */
    public static void showMenuDetails(){
        System.out.println("1. Create Ticket");
        System.out.println("2. Update Ticket");
        System.out.println("3. Delete Ticket by id");
        System.out.println("4. Select single Ticket by Id");
        System.out.println("5. Get Ticket List");
        System.out.println("6. Select ticket assigned to specific agent");
        System.out.println("7. Select all tickets by specific tags");
        System.out.println("8. Ticket count group by agent name");
        System.out.println("Please enter your choice");
    }
}
