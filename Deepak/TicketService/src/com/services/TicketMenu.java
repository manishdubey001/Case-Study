package com.services;

import com.util.UserConsoleInput;

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
                    objTicktOperation.getTickets(objTicktOperation.getAllTicket());
                    break;

                case 2:
                    System.out.println("Enter ticket Id");
                    int id1 = UserConsoleInput.acceptNumber();
                    objTicktOperation.getTickets(objTicktOperation.getTicketById(id1));
                    break;

                case 3:
                    System.out.println("Enter ticket Id");
                    int id2 = UserConsoleInput.acceptNumber();
                    objTicktOperation.updateTicketById(id2);
                    break;

                case 4:
                    System.out.println("Enter ticket Id");
                    int id3 = UserConsoleInput.acceptNumber();
                    if(objTicktOperation.deleteTicketById(id3))
                        System.out.println("Ticket delete successful!");
                    break;

                case 5:
                    objTicktOperation.getTickets(objTicktOperation.searchTicketByAgent());
                    break;

                case 6:
                    objTicktOperation.getTickets(objTicktOperation.searchTicketByTag());
                    break;
                case 7:
                    System.out.println("Show All the Tickets");
                    objTicktOperation.getTickets(objTicktOperation.getAllTicket());
                    break;
                case 8:
                    System.out.println("Agents \t Counts");
                    objTicktOperation.showAgentTicketCount(objTicktOperation.calculateAgentTicketCount());
                    break;
                case 0:
                    loop = false;
                    System.exit(0);
                    break;
                case 9:
                    System.out.println("Enter no of tickets you want");
                    int noOfTickets = UserConsoleInput.acceptNumber();
                    objTicktOperation.getTickets(objTicktOperation.autoLoadTickets(noOfTickets));
                    break;

                case 10:
                    boolean report = true;
                    TicketReports objTicktReports = new TicketReports();

                    while(report){
                        displayReportMenu();
                        int reportOption = UserConsoleInput.acceptNumber();
                        switch (reportOption){
                            case 1:
                                System.out.println("Total Number of Tickets : "+objTicktReports.countNoOfTicketInSystem());
                                break;

                            case 2:
                                System.out.println("Oldest ticket in system : ");
                                objTicktOperation.getTickets(objTicktReports.getOldestTicket());
                                break;

                            case 3:
                                System.out.println("Number of tags in system:");
                                objTicktReports.displayTagTicketCount(objTicktReports.getTicketCountByTag());
                                break;

                            case 0:
                                report = false;
                                objTicktReports = null;
                                break;

                            default:
                               System.out.println("Wrong Input! please enter number between 1 to 2");
                                break;


                        }
                    }

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
        System.out.println("9: Auto load multiple tickets");
        System.out.println("10: Ticket Reports");
        System.out.println("0: QUIT");
        System.out.println("Enter your Choice :");
    }


    public static void displayReportMenu(){
        System.out.println("########################### Report Menu ###########################");
        System.out.println("1: Get number of Tickets in system");
        System.out.println("2: Get oldest Ticket in system");
        System.out.println("3: Get all tags used in system");
//        System.out.println("4: Tickets before particular days");
        System.out.println("0: Exit");
        System.out.println("Please enter above option number :");


    }

}