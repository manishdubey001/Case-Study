package com.casestudy;

/**
 * Created by lokesh on 12/1/16.
 */

public class Main {

    final static String []menus = {"1) Create Ticket","2) Update Ticket","3) Delete Ticket","4) Get Ticket","5) Get all Tickets","6) Find Tickets assigned to Agent", "7) Get all Agent with Tickets Counts","8) Search Tickets By Tag","9) Exit"};

    public static void main(String[] args) throws Exception {
        Services s = new Services();
        for(int i=1;i<=20;i++)
            s.createDummyTickets();
        int choice;
        do {
            showMainMenu();
            choice = MyReader.readChoice();
            switch (choice) {
                case 1:
                    //Create New Ticket
                    s.createTicket();
                    break;
                case 2:
                    //Update Ticket By ID
                    s.updateTicket();
                    break;
                case 3:
                    //Delete By ID
                    s.deleteTicket();
                    break;
                case 4:
                    //Get Ticket By ID
                    s.tickets();
                    break;
                case 5:
                    //Get All tickets sorted BY UPDATED TIME
                    s.getTickets();
                    break;
                case 6:
                    //Find Tickets assigned to Agent
                    s.ticketsOfAgent();
                    break;
                case 7:
                    //Get all Agent with Tickets Counts
                    s.allAgentTicketCount();
                    break;
                case 8:
                    //Search Tickets By Tag
                    s.ticketsByTag();
                    break;
                case 9:
                    System.out.println("Good Bye!!");
                    break;
                default://Exit Case
                    System.out.println("Please enter correct option.");
                    break;
            }
        } while (choice != 9);
    }

    public static void showMainMenu() {
        for (String option: menus
                ) {
            System.out.println(option);
        }
        System.out.println("Enter your choice:");
    }
}