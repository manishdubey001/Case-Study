package com.casestudy;

/**
 * Created by lokesh on 12/1/16.
 * This is Main Class defining main function to start the program, and calls functions from Service as per action from user input.
 */

public class Main {

    final static String []menus = {"1) Create Ticket","2) Update Ticket","3) Delete Ticket","4) Get Ticket","5) Get all Ticket","6) Find Ticket assigned to Agent", "7) Get all Agent with Ticket Counts","8) Search Ticket By Tag","9) Total Ticket Count","10) Oldest Tickets Report", "11) Tickets older than n Days", "12) Count of Tickets with specific Tag", "13) Exit"};

    public static void main(String[] args) throws Exception {
        AddOnService service = new AddOnService();
        //Create Dummy Tickets (only if not available in file) and write them in file with Serialization
        service.createDummyTickets();
        //Read All Tickets from File with de-serialization and store them in local data structure
        service.readAllTicketsFromFile();
        int choice;
        do {
            showMainMenu();
            choice = InputReader.readChoice("Enter your choice:");
            switch (choice) {
                case 1:
                    //Create New Ticket
                    service.createTicket();
                    break;
                case 2:
                    //Update Ticket By ID
                    service.updateTicket();
                    break;
                case 3:
                    //Delete By ID
                    service.deleteTicket();
                    break;
                case 4:
                    //Get Ticket By ID
                    service.getTicket();
                    break;
                case 5:
                    //Get All tickets sorted BY UPDATED TIME
                    service.getTickets();
                    break;
                case 6:
                    //Find Ticket assigned to Agent
                    service.ticketsOfAgent();
                    break;
                case 7:
                    //Get all Agent with Ticket Counts
                    service.allAgentTicketCount();
                    break;
                case 8:
                    //Search Ticket By Tag
                    service.ticketsByTag();
                    break;
                case 9:
                    //Report on Number of Tickets in System
                    service.ticketCountReport();
                    break;
                case 10:
                    //Oldest Ticket(s) in System
                    service.oldestTicket();
                    break;
                case 11:
                    //Ticket Older than entered Days
                    service.olderThanDaysTickets();
                    break;
                case 12:
                    //Number of Tickets with entered tag
                    service.ticketsReportForTag();
                case 13:
                    System.out.println("Good Bye!!");
                    break;
                default:
                    //Exit Case
                    System.out.println("Please enter correct option.");
                    break;
            }
        } while (choice != 13);
    }

    public static void showMainMenu() {
        for (String option: menus) {
            System.out.println(option);
        }
    }
}